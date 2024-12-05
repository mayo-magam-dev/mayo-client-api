package com.mayo.client.mayoclientapi.presentation.controller;

import com.mayo.client.mayoclientapi.application.service.ReservationService;
import com.mayo.client.mayoclientapi.presentation.dto.response.ReadReservationDetailResponse;
import com.mayo.client.mayoclientapi.presentation.dto.response.ReadReservationResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/reservation")
public class ReservationController {

    private final ReservationService reservationService;

    @GetMapping
    public ResponseEntity<List<ReadReservationResponse>> getReservationsByUserId(HttpServletRequest request) {
        return ResponseEntity.ok(reservationService.getReservationsByUserId(request.getAttribute("uid").toString()));
    }

    @GetMapping("/{reservationId}")
    public ResponseEntity<ReadReservationDetailResponse> getReservationById(@PathVariable("reservationId") String reservationId) {
        return ResponseEntity.ok(reservationService.getReservationDetailById(reservationId));
    }
}
