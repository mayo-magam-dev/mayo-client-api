package com.mayo.client.mayoclientapi.application.service;

import com.mayo.client.mayoclientapi.common.exception.ApplicationException;
import com.mayo.client.mayoclientapi.common.exception.payload.ErrorStatus;
import com.mayo.client.mayoclientapi.persistence.domain.Item;
import com.mayo.client.mayoclientapi.persistence.repository.ItemRepository;
import com.mayo.client.mayoclientapi.presentation.dto.response.ReadItemResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Service
public class ItemService {

    private final ItemRepository itemRepository;

    public List<ReadItemResponse> getItemsByStoreId(String storeId) {
        return itemRepository.findItemsByStoreId(storeId).stream().map(ReadItemResponse::from).toList();
    }

    public ReadItemResponse getItemById(String itemId) {
        Item item = itemRepository.findItemById(itemId)
                .orElseThrow(() -> new ApplicationException(
                        ErrorStatus.toErrorStatus("해당하는 아이템이 없습니다.", 404, LocalDateTime.now())
                ));

        return ReadItemResponse.from(item);
    }
}
