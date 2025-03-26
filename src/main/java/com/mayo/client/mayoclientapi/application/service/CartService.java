package com.mayo.client.mayoclientapi.application.service;

import com.google.cloud.firestore.DocumentReference;
import com.mayo.client.mayoclientapi.common.annotation.FirestoreTransactional;
import com.mayo.client.mayoclientapi.common.exception.ApplicationException;
import com.mayo.client.mayoclientapi.common.exception.payload.ErrorStatus;
import com.mayo.client.mayoclientapi.persistence.domain.Cart;
import com.mayo.client.mayoclientapi.persistence.domain.Item;
import com.mayo.client.mayoclientapi.persistence.domain.Store;
import com.mayo.client.mayoclientapi.persistence.repository.CartRepository;
import com.mayo.client.mayoclientapi.persistence.repository.ItemRepository;
import com.mayo.client.mayoclientapi.persistence.repository.StoreRepository;
import com.mayo.client.mayoclientapi.persistence.repository.UserRepository;
import com.mayo.client.mayoclientapi.presentation.dto.request.CreateCartRequest;
import com.mayo.client.mayoclientapi.presentation.dto.request.UpdateCartQuantityRequest;
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
@FirestoreTransactional
public class CartService {

    private final CartRepository cartRepository;
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;
    private final StoreRepository storeRepository;

    @FirestoreTransactional
    public ReadCartResponse createCart(CreateCartRequest request, String userId) {

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

        Store store = storeRepository.findByDocRef(storeRef)
                .orElseThrow(() -> new ApplicationException(
                        ErrorStatus.toErrorStatus("해당하는 가게가 없습니다.", 404, LocalDateTime.now())
                ));

        DocumentReference itemRef = itemRepository.findDocRefById(request.itemId())
                .orElseThrow(() -> new ApplicationException(
                        ErrorStatus.toErrorStatus("해당하는 아이템이 없습니다.", 404, LocalDateTime.now())
                ));

        Cart newCart = cartRepository.save(request.toEntity(itemRef, storeRef, userRef, subtotal));

        return ReadCartResponse.from(newCart, item, store);
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

    public void deleteById(String userId, String cartId) {

        Cart cart = cartRepository.findCartById(cartId)
                .orElseThrow(() -> new ApplicationException(
                        ErrorStatus.toErrorStatus("해당하는 카트가 없습니다.", 404, LocalDateTime.now())
                ));

        if(!cart.getUserRef().getId().equals(userId)) {
            throw new ApplicationException(
                    ErrorStatus.toErrorStatus("권한이 없는 사용자입니다.", 401, LocalDateTime.now())
            );
        }

        cartRepository.deleteById(cartId);
    }

    public void updateCartQuantity(UpdateCartQuantityRequest request) {
        cartRepository.updateCartQuantity(request);
    }
}
