package com.mayo.client.mayoclientapi.presentation.controller;

import com.mayo.client.mayoclientapi.application.service.StoreService;
import com.mayo.client.mayoclientapi.presentation.dto.response.ReadRecentlyStoreResponse;
import com.mayo.client.mayoclientapi.presentation.dto.response.ReadSimpleStoreResponse;
import com.mayo.client.mayoclientapi.presentation.dto.response.ReadStoreResponse;
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
@RequestMapping("/store")
public class StoreController {

    private final StoreService storeService;

    @GetMapping
    public ResponseEntity<List<ReadSimpleStoreResponse>> getAllStore() {
        return ResponseEntity.ok(storeService.getAllStores());
    }

    @GetMapping("/on-sale")
    public ResponseEntity<List<ReadSimpleStoreResponse>> getAllSales() {
        return ResponseEntity.ok(storeService.getOpenStores());
    }

    @GetMapping("/category/{storeCategory}")
    public ResponseEntity<List<ReadSimpleStoreResponse>> getAllCategories(@PathVariable Long storeCategory) {
        return ResponseEntity.ok(storeService.getStoreByCategory(storeCategory));
    }

    @GetMapping("/recently/{userId}")
    public ResponseEntity<ReadRecentlyStoreResponse> getRecentlyStore(@PathVariable String userId) {
        return ResponseEntity.ok(storeService.getRecentlyUserId(userId));
    }

    @GetMapping("/detail/{storeId}")
    public ResponseEntity<ReadStoreResponse> getStoreDetail(@PathVariable String storeId) {
        return ResponseEntity.ok(storeService.getStore(storeId));
    }

    @GetMapping("/random-open-store")
    public ResponseEntity<List<ReadSimpleStoreResponse>> getRandomOpenStore() {
        return ResponseEntity.ok(storeService.getRandomOpenStores());
    }
}
