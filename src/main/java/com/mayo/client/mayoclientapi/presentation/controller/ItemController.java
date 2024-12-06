package com.mayo.client.mayoclientapi.presentation.controller;

import com.mayo.client.mayoclientapi.application.service.ItemService;
import com.mayo.client.mayoclientapi.presentation.dto.response.ReadItemResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "아이템 API", description = "아이템 API")
@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("/item")
public class ItemController {

    private final ItemService itemService;

    @Operation(summary = "가게의 모든 아이템을 가져옵니다.", description = "가게의 모든 아이템을 가져옵니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "가게 아이템 조회 성공", content = @Content(schema = @Schema(implementation = List.class))),
            @ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content),
            @ApiResponse(responseCode = "500", description = "서버 오류", content = @Content)
    })
    @GetMapping("/store/{storeId}")
    public ResponseEntity<List<ReadItemResponse>> getItemByStoreId(@PathVariable String storeId) {
        return ResponseEntity.ok(itemService.getItemsByStoreId(storeId));
    }

    @Operation(summary = "아이템 정보를 id로 가져옵니다.", description = "아이템 정보를 id로 가져옵니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "아이템 조회 성공", content = @Content(schema = @Schema(implementation = ReadItemResponse.class))),
            @ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content),
            @ApiResponse(responseCode = "500", description = "서버 오류", content = @Content)
    })
    @GetMapping("/item/{itemId}")
    public ResponseEntity<ReadItemResponse> getItemById(@PathVariable String itemId) {
        return ResponseEntity.ok(itemService.getItemById(itemId));
    }
}
