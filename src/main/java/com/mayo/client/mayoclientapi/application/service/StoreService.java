package com.mayo.client.mayoclientapi.application.service;

import com.mayo.client.mayoclientapi.common.annotation.FirestoreTransactional;
import com.mayo.client.mayoclientapi.common.exception.ApplicationException;
import com.mayo.client.mayoclientapi.common.exception.payload.ErrorStatus;
import com.mayo.client.mayoclientapi.persistence.domain.Store;
import com.mayo.client.mayoclientapi.persistence.repository.ItemRepository;
import com.mayo.client.mayoclientapi.persistence.repository.ReservationRepository;
import com.mayo.client.mayoclientapi.persistence.repository.StoreRepository;
import com.mayo.client.mayoclientapi.presentation.dto.response.ReadRecentlyStoreResponse;
import com.mayo.client.mayoclientapi.presentation.dto.response.ReadSimpleStoreResponse;
import com.mayo.client.mayoclientapi.presentation.dto.response.ReadStoreResponse;
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
public class StoreService {

    private final StoreRepository storeRepository;
    private final ItemRepository itemRepository;
    private final ReservationRepository reservationRepository;

    public List<ReadSimpleStoreResponse> getAllStores() {

        List<ReadSimpleStoreResponse> list = new ArrayList<>();
        List<Store> stores = storeRepository.findAll();

        for(Store store : stores) {
            Double maxSalePercent = itemRepository.findMaxPercentItemByStoreId(store.getId());
            list.add(ReadSimpleStoreResponse.from(store, maxSalePercent));
        }

        return list;
    }

    public List<ReadSimpleStoreResponse> getOpenStores() {

        List<ReadSimpleStoreResponse> list = new ArrayList<>();
        List<Store> stores = storeRepository.findOpenStores();

        for(Store store : stores) {
            Double maxSalePercent = itemRepository.findMaxPercentItemByStoreId(store.getId());
            list.add(ReadSimpleStoreResponse.from(store, maxSalePercent));
        }

        return list;
    }

    public List<ReadSimpleStoreResponse> getStoreByCategory(Long storeCategory) {

        List<ReadSimpleStoreResponse> list = new ArrayList<>();
        List<Store> stores = storeRepository.findByCategory(storeCategory);

        for(Store store : stores) {
            Double maxSalePercent = itemRepository.findMaxPercentItemByStoreId(store.getId());
            list.add(ReadSimpleStoreResponse.from(store, maxSalePercent));
        }

        return list;
    }

    public ReadRecentlyStoreResponse getRecentlyUserId(String userId){

        return reservationRepository.findRecentlyByUserId(userId)
                .map(reservationEntity -> storeRepository.findByDocRef(reservationEntity.getStoreRef()))
                .orElseThrow(() -> new ApplicationException(
                        ErrorStatus.toErrorStatus("해당하는 가게가 없습니다.", 404, LocalDateTime.now())
                ))
                .map(ReadRecentlyStoreResponse::from)
                .orElse(null);
    }

    public ReadStoreResponse getStore(String storeId) {

        Store store = storeRepository.findByStoreId(storeId)
                .orElseThrow(() -> new ApplicationException(ErrorStatus.toErrorStatus("해당하는 가게가 없습니다.", 404, LocalDateTime.now())
                ));

        return ReadStoreResponse.from(store);
    }

    public List<ReadSimpleStoreResponse> getRandomOpenStores() {

        List<ReadSimpleStoreResponse> list = new ArrayList<>();
        List<Store> stores = storeRepository.findRandomOpenStores();

        for(Store store : stores) {
            Double maxSalePercent = itemRepository.findMaxPercentItemByStoreId(store.getId());
            list.add(ReadSimpleStoreResponse.from(store, maxSalePercent));
        }

        return list;
    }
}
