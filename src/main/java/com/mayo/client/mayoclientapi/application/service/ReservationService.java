package com.mayo.client.mayoclientapi.application.service;

import com.google.cloud.firestore.DocumentReference;
import com.mayo.client.mayoclientapi.common.annotation.FirestoreTransactional;
import com.mayo.client.mayoclientapi.common.exception.ApplicationException;
import com.mayo.client.mayoclientapi.common.exception.payload.ErrorStatus;
import com.mayo.client.mayoclientapi.persistance.domain.Cart;
import com.mayo.client.mayoclientapi.persistance.domain.Item;
import com.mayo.client.mayoclientapi.persistance.domain.Reservation;
import com.mayo.client.mayoclientapi.persistance.domain.Store;
import com.mayo.client.mayoclientapi.persistance.repository.*;
import com.mayo.client.mayoclientapi.presentation.dto.request.CreateReservationRequest;
import com.mayo.client.mayoclientapi.presentation.dto.response.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@RestController
@Slf4j
@FirestoreTransactional
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final ItemRepository itemRepository;
    private final CartRepository cartRepository;
    private final StoreRepository storeRepository;
    private final UserRepository userRepository;
    private final FCMService fcmService;

    public List<ReadReservationResponse> getReservationsByUserId(String userId) {

        List<Reservation> reservationList = reservationRepository.getReservationsByUserId(userId);
        List<ReadReservationResponse> responseList = new ArrayList<>();

        for(Reservation reservation : reservationList) {

            Store store = storeRepository.findByDocRef(reservation.getStoreRef())
                    .orElseThrow(() -> new ApplicationException(ErrorStatus.toErrorStatus("해당되는 가게가 없습니다.", 404, LocalDateTime.now())
                    ));

            DocumentReference cartDocRef = cartRepository.findFirstCartsByReservation(reservation)
                    .orElse(null);

            ReadFirstItemResponse firstItemResponse = null;

            if(cartDocRef != null) {
                firstItemResponse = itemRepository.findFirstItemNamesFromCart(cartDocRef);
            } else {
                firstItemResponse = ReadFirstItemResponse.builder()
                        .itemName(" ")
                        .itemQuantity(0)
                        .build();
            }

            responseList.add(ReadReservationResponse.from(reservation, store, firstItemResponse));
        }

        return responseList;
    }

    public ReadReservationDetailResponse getReservationDetailById(String reservationId) {

        Reservation reservation = reservationRepository.findByReservationId(reservationId)
                .orElseThrow(() -> new ApplicationException(
                        ErrorStatus.toErrorStatus("해당하는 예약이 없습니다.", 404, LocalDateTime.now())
                ));

        Store store = storeRepository.findByDocRef(reservation.getStoreRef())
                .orElseThrow(() -> new ApplicationException(
                        ErrorStatus.toErrorStatus("해당하는 가게가 없습니다.", 404, LocalDateTime.now())
                ));

        List<ReadCartResponse> cartList = new ArrayList<>();

        for(DocumentReference cartDocRef : reservation.getCartRef()) {

            Cart cart = cartRepository.findByDocRef(cartDocRef)
                .orElseThrow(() -> new ApplicationException(
                    ErrorStatus.toErrorStatus("해당하는 카트가 없습니다.", 404, LocalDateTime.now())
                ));

            Item item =itemRepository.findItemByDocRef(cart.getItem())
                    .orElseThrow(() -> new ApplicationException(
                            ErrorStatus.toErrorStatus("해당하는 아이템이 없습니다.", 404, LocalDateTime.now())
                    ));

            cartList.add(ReadCartResponse.from(cart, item, store));
        }

        return ReadReservationDetailResponse.from(reservation, store, cartList);
    }

    public void createReservation(CreateReservationRequest request, String uid) {

        DocumentReference userRef = userRepository.findDocByUserId(uid)
                .orElseThrow(() -> new ApplicationException(
                        ErrorStatus.toErrorStatus("해당하는 유저가 없습니다.", 404, LocalDateTime.now())
                ));

        List<DocumentReference> cartRefList = cartRepository.findCartRefByUserRef(userRef);
        List<Cart> cartList = cartRepository.findCartsByUserRef(userRef);
        DocumentReference storeRef = cartList.get(0).getStoreRef();
        Double totalPrice = (double) 0;

        for(Cart cart : cartList) {

            Item item = itemRepository.findItemByDocRef(cart.getItem())
                    .orElseThrow(() -> new ApplicationException(
                            ErrorStatus.toErrorStatus("해당하는 아이템이 없습니다.", 404, LocalDateTime.now())
                    ));

            if(item.getItemQuantity() >= cart.getItemCount()) {
                itemRepository.updateItemQuantityMinus(item.getItemId(), cart.getItemCount());
                totalPrice += item.getSalePrice() * cart.getItemCount();
            } else {
                throw new ApplicationException(
                        ErrorStatus.toErrorStatus("재고가 부족합니다.", 404, LocalDateTime.now())
                );
            }

            cartRepository.updateCartIsActiveFalse(cart.getCartId());
        }

        reservationRepository.save(request.toEntity(cartRefList, storeRef, totalPrice, userRef));

        List<String> tokens = userRepository.findFCMTokenByStoresId(storeRef.getId());

        fcmService.sendNewReservationMessage(tokens);
    }
}
