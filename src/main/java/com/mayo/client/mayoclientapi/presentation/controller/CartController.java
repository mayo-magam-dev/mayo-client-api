package com.mayo.client.mayoclientapi.presentation.controller;

import com.mayo.client.mayoclientapi.application.service.CartService;
import com.mayo.client.mayoclientapi.common.annotation.Authenticated;
import com.mayo.client.mayoclientapi.presentation.dto.request.CreateCartRequest;
import com.mayo.client.mayoclientapi.presentation.dto.response.ReadCartResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "장바구니 API", description = "장바구니 API")
@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/carts")
public class CartController {

    private final CartService cartService;

    @Operation(summary = "유저의 모든 장바구니를 가져옵니다.", description = "유저의 모든 장바구니를 가져옵니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "예약 조회 성공", content = @Content(schema = @Schema(implementation = List.class))),
            @ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content),
            @ApiResponse(responseCode = "500", description = "서버 오류", content = @Content)
    })
    @Authenticated
    @GetMapping
    public ResponseEntity<List<ReadCartResponse>> getCartsByUserId(HttpServletRequest req) {
        String userId = req.getParameter("uid");
        return ResponseEntity.ok(cartService.getCartsByUserId(userId));
    }

    @Operation(summary = "장바구니를 생성합니다.", description = "장바구니를 생성합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "예약 조회 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content),
            @ApiResponse(responseCode = "500", description = "서버 오류", content = @Content)
    })
    @Authenticated
    @PostMapping
    public ResponseEntity<Void> createCart(HttpServletRequest req, @RequestBody CreateCartRequest request) {
        String userId = req.getParameter("uid");
        cartService.createCart(request, userId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
