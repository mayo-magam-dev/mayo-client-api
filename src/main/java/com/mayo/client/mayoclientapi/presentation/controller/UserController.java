package com.mayo.client.mayoclientapi.presentation.controller;

import com.mayo.client.mayoclientapi.application.service.UserService;
import com.mayo.client.mayoclientapi.common.annotation.Authenticated;
import com.mayo.client.mayoclientapi.presentation.dto.request.*;
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
        return ResponseEntity.ok(userService.getUserFavoriteStores(req.getAttribute("uid").toString()));
    }

    @Authenticated
    @GetMapping("/notice-stores")
    public ResponseEntity<List<ReadStoreResponse>> noticeStores(HttpServletRequest req) {
        return ResponseEntity.ok(userService.getUserNoticeStores(req.getAttribute("uid").toString()));
    }

    @Authenticated
    @PostMapping
    public ResponseEntity<Void> createUser(HttpServletRequest req, @RequestBody CreateUserRequest request) {
        userService.createUser(request, req.getAttribute("uid").toString());
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

    @Authenticated
    @DeleteMapping
    public ResponseEntity<Void> deleteUser(HttpServletRequest req) {
        userService.deleteUser(req.getAttribute("uid").toString());
        return ResponseEntity.noContent().build();
    }

    @Authenticated
    @PutMapping("/nickname")
    public ResponseEntity<Void> updateNickName(HttpServletRequest req, @RequestBody UpdateNickNameRequest request) {
        userService.updateNickName(req.getAttribute("uid").toString(), request.nickName());
        return ResponseEntity.noContent().build();
    }

    @Authenticated
    @PutMapping("/email")
    public ResponseEntity<Void> updateEmail(HttpServletRequest req, @RequestBody UpdateEmailRequest request) {
        userService.updateEmail(req.getAttribute("uid").toString(), request.email());
        return ResponseEntity.noContent().build();
    }
}
