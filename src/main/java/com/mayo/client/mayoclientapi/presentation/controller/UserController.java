package com.mayo.client.mayoclientapi.presentation.controller;

import com.mayo.client.mayoclientapi.application.service.UserService;
import com.mayo.client.mayoclientapi.common.annotation.Authenticated;
import com.mayo.client.mayoclientapi.presentation.dto.request.CreateUserRequest;
import com.mayo.client.mayoclientapi.presentation.dto.request.UpdateFavoriteStoreRequest;
import com.mayo.client.mayoclientapi.presentation.dto.request.UpdateNoticeStoreRequest;
import com.mayo.client.mayoclientapi.presentation.dto.response.ReadStoreResponse;
import com.mayo.client.mayoclientapi.presentation.dto.response.ReadUserResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @Authenticated
    @GetMapping
    public ResponseEntity<ReadUserResponse> getUser(HttpServletRequest req) {
        return ResponseEntity.ok(userService.getUser(req.getAttribute("uid").toString()));
    }

    @Authenticated
    @GetMapping("/favorite-stores")
    public ResponseEntity<List<ReadStoreResponse>> favoriteStores(HttpServletRequest req) {
        return ResponseEntity.ok(userService.getUserNoticeStores(req.getAttribute("uid").toString()));
    }

    @Authenticated
    @GetMapping("/notice-stores")
    public ResponseEntity<List<ReadStoreResponse>> noticeStores(HttpServletRequest req) {
        return ResponseEntity.ok(userService.getUserNoticeStores(req.getAttribute("uid").toString()));
    }

    @PostMapping
    public ResponseEntity<Void> createUser(@RequestBody CreateUserRequest request) {
        userService.createUser(request);
        return ResponseEntity.noContent().build();
    }

    @Authenticated
    @PutMapping("/notice-store")
    public ResponseEntity<Void> updateNoticeStore(HttpServletRequest req, @RequestBody UpdateNoticeStoreRequest request) {
        userService.updateNoticeStore(request, req.getAttribute("uid").toString());
        return ResponseEntity.noContent().build();
    }

    @Authenticated
    @PutMapping("/favorite-store")
    public ResponseEntity<Void> updateNoticeStore(HttpServletRequest req, @RequestBody UpdateFavoriteStoreRequest request) {
        userService.updateFavoriteStore(request, req.getAttribute("uid").toString());
        return ResponseEntity.noContent().build();
    }
}
