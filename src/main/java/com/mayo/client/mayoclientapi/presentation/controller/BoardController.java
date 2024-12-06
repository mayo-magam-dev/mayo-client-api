package com.mayo.client.mayoclientapi.presentation.controller;

import com.mayo.client.mayoclientapi.application.service.BoardService;
import com.mayo.client.mayoclientapi.presentation.dto.response.ReadBoardResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("/board")
public class BoardController {
    private final BoardService boardService;

    @GetMapping("/notice")
    public ResponseEntity<List<ReadBoardResponse>> getNoticeBoard() {
        return ResponseEntity.ok(boardService.getNoticeBoard());
    }

    @GetMapping("/terms")
    public ResponseEntity<List<ReadBoardResponse>> getTermsBoard() {
        return ResponseEntity.ok(boardService.getTermsBoard());
    }

    @GetMapping("/event")
    public ResponseEntity<List<ReadBoardResponse>> getEventBoard() {
        return ResponseEntity.ok(boardService.getEventBoard());
    }

    @GetMapping("/{boardId}")
    public ResponseEntity<ReadBoardResponse> getBoard(@PathVariable String boardId) {
        return ResponseEntity.ok(boardService.getBoard(boardId));
    }

    @GetMapping("/faq-update-info")
    public ResponseEntity<List<ReadBoardResponse>> getFaqUpdateInfoFAQ() {
        return ResponseEntity.ok(boardService.getUpdateInfoFAQ());
    }

    @GetMapping("/faq-delete-account")
    public ResponseEntity<List<ReadBoardResponse>> getFaqDeleteAccount() {
        return ResponseEntity.ok(boardService.getDeleteAccountFAQ());
    }

    @GetMapping("/faq-store-pickup")
    public ResponseEntity<List<ReadBoardResponse>> getFaqStorePickup() {
        return ResponseEntity.ok(boardService.getStorePickUpFAQ());
    }

    @GetMapping("/faq-order-history")
    public ResponseEntity<List<ReadBoardResponse>> getFaqOrderHistory() {
        return ResponseEntity.ok(boardService.getOrderHistoryFAQ());
    }

    @GetMapping("/faq-order-notification")
    public ResponseEntity<List<ReadBoardResponse>> getFaqOrderNotification() {
        return ResponseEntity.ok(boardService.getOrderNotificationFAQ());
    }

    @GetMapping("/faq-main-feature")
    public ResponseEntity<List<ReadBoardResponse>> getFaqMainFeature() {
        return ResponseEntity.ok(boardService.getMainFeatureFAQ());
    }

    @GetMapping("/faq-how-to-use")
    public ResponseEntity<List<ReadBoardResponse>> getFaqHowToUse() {
        return ResponseEntity.ok(boardService.getHowToUseFAQ());
    }

    @GetMapping("/faq-payment")
    public ResponseEntity<List<ReadBoardResponse>> getFaqPayment() {
        return ResponseEntity.ok(boardService.getPaymentFAQ());
    }

    @GetMapping("/faq-coupon")
    public ResponseEntity<List<ReadBoardResponse>> getFaqCoupon() {
        return ResponseEntity.ok(boardService.getCouponFAQ());
    }

    @GetMapping("/faq-event")
    public ResponseEntity<List<ReadBoardResponse>> getFaqEvent() {
        return ResponseEntity.ok(boardService.getEventFAQ());
    }

    @GetMapping("/faq-etc")
    public ResponseEntity<List<ReadBoardResponse>> getFaqEtc() {
        return ResponseEntity.ok(boardService.getEtcFAQ());
    }
}
