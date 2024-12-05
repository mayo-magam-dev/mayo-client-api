package com.mayo.client.mayoclientapi.presentation.controller;

import com.mayo.client.mayoclientapi.application.service.CartService;
import com.mayo.client.mayoclientapi.common.annotation.Authenticated;
import com.mayo.client.mayoclientapi.persistance.domain.Cart;
import com.mayo.client.mayoclientapi.persistance.repository.CartRepository;
import com.mayo.client.mayoclientapi.presentation.dto.request.CreateCartRequest;
import com.mayo.client.mayoclientapi.presentation.dto.response.ReadCartResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/carts")
public class CartController {

    private final CartService cartService;

    @Authenticated
    @GetMapping
    public ResponseEntity<List<ReadCartResponse>> getCartsByUserId(HttpServletRequest req) {
        String userId = req.getParameter("uid");
        return ResponseEntity.ok(cartService.getCartsByUserId(userId));
    }

    @Authenticated
    @PostMapping
    public ResponseEntity<Void> createCart(HttpServletRequest req, @RequestBody CreateCartRequest request) {
        String userId = req.getParameter("uid");
        cartService.createCart(request, userId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
