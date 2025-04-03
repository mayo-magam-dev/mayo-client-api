package com.mayo.client.mayoclientapi.presentation.controller;

import com.mayo.client.mayoclientapi.application.service.CartService;
import com.mayo.client.mayoclientapi.common.annotation.Authenticated;
import com.mayo.client.mayoclientapi.common.exception.ValidationFailedException;
import com.mayo.client.mayoclientapi.presentation.dto.request.CreateCartRequest;
import com.mayo.client.mayoclientapi.presentation.dto.request.UpdateCartQuantityRequest;
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
import org.springframework.validation.BindingResult;
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
            @ApiResponse(responseCode = "200", description = "장바구니 조회 성공", content = @Content(schema = @Schema(implementation = List.class))),
            @ApiResponse(responseCode = "400", description = "잘못된 요청"),
            @ApiResponse(responseCode = "500", description = "서버 오류")
    })
    @Authenticated
    @GetMapping
    public ResponseEntity<List<ReadCartResponse>> getCartsByUserId(@RequestAttribute("uid") String uid) {
        return ResponseEntity.ok(cartService.getCartsByUserId(uid));
    }

    @Operation(summary = "장바구니를 생성합니다.", description = "장바구니를 생성합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "장바구니 조회 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청"),
            @ApiResponse(responseCode = "500", description = "서버 오류")
    })
    @Authenticated
    @PostMapping
    public ResponseEntity<ReadCartResponse> createCart(@RequestAttribute("uid") String uid, @RequestBody CreateCartRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(cartService.createCart(request, uid));
    }

    @Operation(summary = "장바구니를 삭제합니다.", description = "장바구니를 삭제합니다. (장바구니 창에서 x표시 눌렀을 시)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "장바구니 삭제 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청"),
            @ApiResponse(responseCode = "500", description = "서버 오류")
    })
    @Authenticated
    @DeleteMapping("/{cartId}")
    public ResponseEntity<Void> deleteCart(@RequestAttribute("uid") String uid, @PathVariable String cartId) {
        cartService.deleteById(uid, cartId);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "장바구니를 수량을 수정합니다.", description = "장바구니를 수량을 수정합니다. (수량의 최소값은 1입니다.)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "수정 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청"),
            @ApiResponse(responseCode = "500", description = "서버 오류")
    })
    @Authenticated
    @PutMapping
    public ResponseEntity<Void> updateCartQuantity(@RequestBody UpdateCartQuantityRequest request, BindingResult bindingResult) {

        if(bindingResult.hasErrors()) {
            throw new ValidationFailedException(bindingResult);
        }

        cartService.updateCartQuantity(request);

        return ResponseEntity.noContent().build();
    }
}
