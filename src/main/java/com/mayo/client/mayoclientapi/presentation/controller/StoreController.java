package com.mayo.client.mayoclientapi.presentation.controller;

import com.mayo.client.mayoclientapi.application.service.StoreService;
import com.mayo.client.mayoclientapi.common.annotation.Authenticated;
import com.mayo.client.mayoclientapi.presentation.dto.response.ReadRecentlyStoreResponse;
import com.mayo.client.mayoclientapi.presentation.dto.response.ReadSimpleStoreResponse;
import com.mayo.client.mayoclientapi.presentation.dto.response.ReadStoreResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "가게 API", description = "가게 API")
@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("/store")
public class StoreController {

    private final StoreService storeService;

    @Operation(summary = "가게 전체 조회", description = "전체 가게를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "전체 가게 조회", content = @Content(schema = @Schema(implementation = List.class))),
            @ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content),
            @ApiResponse(responseCode = "500", description = "서버 오류", content = @Content)
    })
    @GetMapping
    public ResponseEntity<List<ReadSimpleStoreResponse>> getAllStore() {
        return ResponseEntity.ok(storeService.getAllStores());
    }

    @Operation(summary = "할인중인 가게 조회", description = "할인 중인 가게를 모두 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "할인 중 가게 조회 성공", content = @Content(schema = @Schema(implementation = List.class))),
            @ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content),
            @ApiResponse(responseCode = "500", description = "서버 오류", content = @Content)
    })
    @GetMapping("/on-sale")
    public ResponseEntity<List<ReadSimpleStoreResponse>> getAllSales() {
        return ResponseEntity.ok(storeService.getOpenStores());
    }

    @Operation(summary = "카테고리 별로 가게 조회", description = "카테고리로 가게를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "카테고리로 가게 조회 성공", content = @Content(schema = @Schema(implementation = List.class))),
            @ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content),
            @ApiResponse(responseCode = "500", description = "서버 오류", content = @Content)
    })
    @GetMapping("/category/{storeCategory}")
    public ResponseEntity<List<ReadSimpleStoreResponse>> getAllCategories(@PathVariable Long storeCategory) {
        return ResponseEntity.ok(storeService.getStoreByCategory(storeCategory));
    }

    @Operation(summary = "최근 주문한 가게 조회", description = "최근 주문한 가게를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "최근 주문 가게 조회 성공", content = @Content(schema = @Schema(implementation = ReadRecentlyStoreResponse.class))),
            @ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content),
            @ApiResponse(responseCode = "500", description = "서버 오류", content = @Content)
    })
    @Authenticated
    @GetMapping("/recently")
    public ResponseEntity<ReadRecentlyStoreResponse> getRecentlyStore(HttpServletRequest request) {
        return ResponseEntity.ok(storeService.getRecentlyUserId(request.getAttribute("uid").toString()));
    }

    @Operation(summary = "가게 상세정보 조회", description = "가게 상세정보를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "가게 상세 정보 성공", content = @Content(schema = @Schema(implementation = List.class))),
            @ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content),
            @ApiResponse(responseCode = "500", description = "서버 오류", content = @Content)
    })
    @GetMapping("/detail/{storeId}")
    public ResponseEntity<ReadStoreResponse> getStoreDetail(@PathVariable String storeId) {
        return ResponseEntity.ok(storeService.getStore(storeId));
    }

    @Operation(summary = "3개의 할인중인 랜덤 가게를 조회합니다.", description = "3개의 할인중인 랜덤 가게를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "할인중 랜덤 조회 성공", content = @Content(schema = @Schema(implementation = List.class))),
            @ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content),
            @ApiResponse(responseCode = "500", description = "서버 오류", content = @Content)
    })
    @GetMapping("/random-open-store")
    public ResponseEntity<List<ReadSimpleStoreResponse>> getRandomOpenStore() {
        return ResponseEntity.ok(storeService.getRandomOpenStores());
    }

}
