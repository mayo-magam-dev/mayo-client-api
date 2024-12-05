package com.mayo.client.mayoclientapi.presentation.controller;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import com.mayo.client.mayoclientapi.application.service.StoreService;
import com.mayo.client.mayoclientapi.common.annotation.Authenticated;
import com.mayo.client.mayoclientapi.common.exception.ApplicationException;
import com.mayo.client.mayoclientapi.common.exception.payload.ErrorStatus;
import com.mayo.client.mayoclientapi.common.utils.JwtTokenUtils;
import com.mayo.client.mayoclientapi.presentation.dto.response.ReadRecentlyStoreResponse;
import com.mayo.client.mayoclientapi.presentation.dto.response.ReadSimpleStoreResponse;
import com.mayo.client.mayoclientapi.presentation.dto.response.ReadStoreResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
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

    @Authenticated
    @GetMapping("/recently")
    public ResponseEntity<ReadRecentlyStoreResponse> getRecentlyStore(HttpServletRequest request) {
        return ResponseEntity.ok(storeService.getRecentlyUserId(request.getAttribute("uid").toString()));
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
