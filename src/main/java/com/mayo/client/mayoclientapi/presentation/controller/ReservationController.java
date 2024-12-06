package com.mayo.client.mayoclientapi.presentation.controller;

import com.mayo.client.mayoclientapi.application.service.ReservationService;
import com.mayo.client.mayoclientapi.common.annotation.Authenticated;
import com.mayo.client.mayoclientapi.presentation.dto.request.CreateReservationRequest;
import com.mayo.client.mayoclientapi.presentation.dto.response.ReadReservationDetailResponse;
import com.mayo.client.mayoclientapi.presentation.dto.response.ReadReservationResponse;
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
@RequestMapping("/reservation")
public class ReservationController {

    private final ReservationService reservationService;

    @Authenticated
    @GetMapping
    public ResponseEntity<List<ReadReservationResponse>> getReservationsByUserId(HttpServletRequest request) {
        return ResponseEntity.ok(reservationService.getReservationsByUserId(request.getAttribute("uid").toString()));
    }

    @Authenticated
    @GetMapping("/{reservationId}")
    public ResponseEntity<ReadReservationDetailResponse> getReservationById(@PathVariable("reservationId") String reservationId) {
        return ResponseEntity.ok(reservationService.getReservationDetailById(reservationId));
    }

    @Authenticated
    @PostMapping
    public ResponseEntity<Void> createReservation(HttpServletRequest req, @RequestBody CreateReservationRequest request) {
        reservationService.createReservation(request, req.getAttribute("uid").toString());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
