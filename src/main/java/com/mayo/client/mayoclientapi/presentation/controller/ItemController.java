package com.mayo.client.mayoclientapi.presentation.controller;

import com.mayo.client.mayoclientapi.application.service.ItemService;
import com.mayo.client.mayoclientapi.presentation.dto.response.ReadItemResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("/item")
public class ItemController {

    private final ItemService itemService;

    @GetMapping("/{storeId}")
    public ResponseEntity<List<ReadItemResponse>> getItemByStoreId(@PathVariable String storeId) {
        return ResponseEntity.ok(itemService.getItemsByStoreId(storeId));
    }

    @GetMapping("/{itemId}")
    public ResponseEntity<ReadItemResponse> getItemById(@PathVariable String itemId) {
        return ResponseEntity.ok(itemService.getItemById(itemId));
    }
}
