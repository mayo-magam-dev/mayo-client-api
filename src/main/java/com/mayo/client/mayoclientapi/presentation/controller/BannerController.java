package com.mayo.client.mayoclientapi.presentation.controller;

import com.mayo.client.mayoclientapi.application.service.BannerService;
import com.mayo.client.mayoclientapi.presentation.dto.response.ReadBannerResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class BannerController {

    private final BannerService bannerService;

    @Operation(summary = "모든 배너를 가져옵니다.", description = "모든 배너를 가져옵니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "모든 배너 조회 성공", content = @Content(schema = @Schema(implementation = List.class))),
            @ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content),
            @ApiResponse(responseCode = "500", description = "서버 오류", content = @Content)
    })
    @GetMapping("/banners")
    public ResponseEntity<List<ReadBannerResponse>> getBanners() {
        return ResponseEntity.ok(bannerService.getBanners());
    }
}
