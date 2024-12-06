package com.mayo.client.mayoclientapi.application.service;

import com.google.cloud.firestore.DocumentReference;
import com.mayo.client.mayoclientapi.common.exception.ApplicationException;
import com.mayo.client.mayoclientapi.common.exception.payload.ErrorStatus;
import com.mayo.client.mayoclientapi.persistance.domain.Cart;
import com.mayo.client.mayoclientapi.persistance.domain.Item;
import com.mayo.client.mayoclientapi.persistance.domain.Store;
import com.mayo.client.mayoclientapi.persistance.repository.CartRepository;
import com.mayo.client.mayoclientapi.persistance.repository.ItemRepository;
import com.mayo.client.mayoclientapi.persistance.repository.StoreRepository;
import com.mayo.client.mayoclientapi.persistance.repository.UserRepository;
import com.mayo.client.mayoclientapi.presentation.dto.request.CreateCartRequest;
import com.mayo.client.mayoclientapi.presentation.dto.response.ReadCartResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CartService {

    private final CartRepository cartRepository;
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;
    private final StoreRepository storeRepository;

    public void createCart(CreateCartRequest request, String userId) {

        List<ReadCartResponse> cartResponseList = getCartsByUserId(userId);

        if(!cartResponseList.isEmpty()) {
            if(!cartResponseList.get(0).storeId().equals(request.storeId())) {
                throw new ApplicationException(
                        ErrorStatus.toErrorStatus("장바구니와 다른 가게 입니다.", 400, LocalDateTime.now())
                );
            }

            if(cartResponseList.get(0).itemQuantity() > request.itemCount()) {
                throw new ApplicationException(
                        ErrorStatus.toErrorStatus("현재 주문 가능한 수량을 초과하였습니다.", 400, LocalDateTime.now())
                );
            }
        }

        Item item = itemRepository.findItemById(request.itemId())
                .orElseThrow(() -> new ApplicationException(
                        ErrorStatus.toErrorStatus("해당하는 아이템이 없습니다.", 404, LocalDateTime.now())
                ));

        Double subtotal = item.getSalePrice() * request.itemCount();

        DocumentReference userRef = userRepository.findDocByUserId(userId)
                .orElseThrow(() -> new ApplicationException(
                        ErrorStatus.toErrorStatus("해당하는 유저가 없습니다.", 404, LocalDateTime.now())
                ));

        DocumentReference storeRef = item.getStoreRef();

        DocumentReference itemRef = itemRepository.findDocRefById(request.itemId())
                .orElseThrow(() -> new ApplicationException(
                        ErrorStatus.toErrorStatus("해당하는 아이템이 없습니다.", 404, LocalDateTime.now())
                ));

        cartRepository.save(request.toEntity(itemRef, storeRef, userRef, subtotal));
    }

    public List<ReadCartResponse> getCartsByUserId(String userId) {

        DocumentReference userRef = userRepository.findDocByUserId(userId)
                        .orElseThrow(() -> new ApplicationException(
                                ErrorStatus.toErrorStatus("해당하는 유저가 없습니다.", 404, LocalDateTime.now())
                        ));

        List<Cart> cartList = cartRepository.findCartsByUserRef(userRef);
        List<ReadCartResponse> responseList = new ArrayList<>();

        for(Cart cart : cartList) {

            Item item = itemRepository.findItemByDocRef(cart.getItem())
                    .orElseThrow(() -> new ApplicationException(
                            ErrorStatus.toErrorStatus("해당하는 아이템이 없습니다.", 404, LocalDateTime.now())
                    ));

            DocumentReference storeRef = cart.getStoreRef();
            Store store = storeRepository.findByDocRef(storeRef)
                    .orElseThrow(() -> new ApplicationException(
                            ErrorStatus.toErrorStatus("해당하는 가게가 없습니다.", 404, LocalDateTime.now())
                            ));

            responseList.add(ReadCartResponse.from(cart, item, store));
        }

        return responseList;
    }
}
