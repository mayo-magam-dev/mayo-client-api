package com.mayo.client.mayoclientapi.presentation.controller;

import com.mayo.client.mayoclientapi.application.service.ReservationService;
import com.mayo.client.mayoclientapi.common.annotation.Authenticated;
import com.mayo.client.mayoclientapi.presentation.dto.request.CreateReservationRequest;
import com.mayo.client.mayoclientapi.presentation.dto.response.ReadReservationDetailResponse;
import com.mayo.client.mayoclientapi.presentation.dto.response.ReadReservationResponse;
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

@Tag(name = "예약 API", description = "예약 API")
@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/reservation")
public class ReservationController {

    private final ReservationService reservationService;

    @Operation(summary = "유저의 주문 내역을 모두 가져옵니다.", description = "유저의 주문 내역을 모두 가져옵니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "주문 내역 전체 조회 성공", content = @Content(schema = @Schema(implementation = List.class))),
            @ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content),
            @ApiResponse(responseCode = "500", description = "서버 오류", content = @Content)
    })
    @Authenticated
    @GetMapping
    public ResponseEntity<List<ReadReservationResponse>> getReservationsByUserId(HttpServletRequest request) {
        return ResponseEntity.ok(reservationService.getReservationsByUserId(request.getAttribute("uid").toString()));
    }

    @Operation(summary = "주문 내역의 상세정보를 가져옵니다.", description = "주문 내역의 상세정보를 가져옵니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "주문 상세 내역 조회 성공", content = @Content(schema = @Schema(implementation = ReadReservationDetailResponse.class))),
            @ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content),
            @ApiResponse(responseCode = "500", description = "서버 오류", content = @Content)
    })
    @Authenticated
    @GetMapping("/{reservationId}")
    public ResponseEntity<ReadReservationDetailResponse> getReservationById(@PathVariable("reservationId") String reservationId) {
        return ResponseEntity.ok(reservationService.getReservationDetailById(reservationId));
    }

    @Operation(summary = "유저의 장바구니에 있는 정보로 예약을 진행합니다.", description = "유저의 장바구니에 있는 정보로 예약을 진행합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "예약 성공", content = @Content(schema = @Schema(implementation = List.class))),
            @ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content),
            @ApiResponse(responseCode = "500", description = "서버 오류", content = @Content)
    })
    @Authenticated
    @PostMapping
    public ResponseEntity<Void> createReservation(HttpServletRequest req, @RequestBody CreateReservationRequest request) {
        reservationService.createReservation(request, req.getAttribute("uid").toString());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
