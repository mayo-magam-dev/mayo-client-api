package com.mayo.client.mayoclientapi.presentation.controller;

import com.mayo.client.mayoclientapi.application.service.BoardService;
import com.mayo.client.mayoclientapi.presentation.dto.response.ReadBoardResponse;
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

@Tag(name = "게시판 API", description = "게시판 API")
@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("/board")
public class BoardController {

    private final BoardService boardService;

    @Operation(summary = "공지사항 게시글을 모두 가져옵니다.", description = "공지사항 게시글을 모두 가져옵니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "공지사항 조회 성공", content = @Content(schema = @Schema(implementation = List.class))),
            @ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content),
            @ApiResponse(responseCode = "500", description = "서버 오류", content = @Content)
    })
    @GetMapping("/notice")
    public ResponseEntity<List<ReadBoardResponse>> getNoticeBoard() {
        return ResponseEntity.ok(boardService.getNoticeBoard());
    }

    @Operation(summary = "약관 및 정책 게시판의 모든 글을 가져옵니다.", description = "약관 및 정책 게시판의 모든 글을 가져옵니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "약관 및 정책 조회 성공", content = @Content(schema = @Schema(implementation = List.class))),
            @ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content),
            @ApiResponse(responseCode = "500", description = "서버 오류", content = @Content)
    })
    @GetMapping("/terms")
    public ResponseEntity<List<ReadBoardResponse>> getTermsBoard() {
        return ResponseEntity.ok(boardService.getTermsBoard());
    }

    @Operation(summary = "이벤트 게시판의 모든 글을 가져옵니다.", description = "이벤트 게시판의 모든 글을 가져옵니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "이벤트 조회 성공", content = @Content(schema = @Schema(implementation = List.class))),
            @ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content),
            @ApiResponse(responseCode = "500", description = "서버 오류", content = @Content)
    })
    @GetMapping("/event")
    public ResponseEntity<List<ReadBoardResponse>> getEventBoard() {
        return ResponseEntity.ok(boardService.getEventBoard());
    }

    @Operation(summary = "boardId를 받아 해당 게시글을 가져옵니다.", description = "board PK값으로 게시판의 모든 글을 가져옵니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "게시글 조회 성공", content = @Content(schema = @Schema(implementation = List.class))),
            @ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content),
            @ApiResponse(responseCode = "500", description = "서버 오류", content = @Content)
    })
    @GetMapping("/{boardId}")
    public ResponseEntity<ReadBoardResponse> getBoard(@PathVariable String boardId) {
        return ResponseEntity.ok(boardService.getBoard(boardId));
    }

    @Operation(summary = "FAQ 정보 수정의 모든 글을 가져옵니다.", description = "FAQ 정보 수정의 모든 글을 가져옵니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "정보 수정 조회 성공", content = @Content(schema = @Schema(implementation = List.class))),
            @ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content),
            @ApiResponse(responseCode = "500", description = "서버 오류", content = @Content)
    })
    @GetMapping("/faq-update-info")
    public ResponseEntity<List<ReadBoardResponse>> getFaqUpdateInfoFAQ() {
        return ResponseEntity.ok(boardService.getUpdateInfoFAQ());
    }

    @Operation(summary = "FAQ 계정 탈퇴의 모든 글을 가져옵니다.", description = "FAQ 계정 탈퇴의 모든 글을 가져옵니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "계정 탈퇴 조회 성공", content = @Content(schema = @Schema(implementation = List.class))),
            @ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content),
            @ApiResponse(responseCode = "500", description = "서버 오류", content = @Content)
    })
    @GetMapping("/faq-delete-account")
    public ResponseEntity<List<ReadBoardResponse>> getFaqDeleteAccount() {
        return ResponseEntity.ok(boardService.getDeleteAccountFAQ());
    }

    @Operation(summary = "FAQ 가게 픽업의 모든 글을 가져옵니다.", description = "FAQ 가게 픽업의 모든 글을 가져옵니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "가게 픽업 조회 성공", content = @Content(schema = @Schema(implementation = List.class))),
            @ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content),
            @ApiResponse(responseCode = "500", description = "서버 오류", content = @Content)
    })
    @GetMapping("/faq-store-pickup")
    public ResponseEntity<List<ReadBoardResponse>> getFaqStorePickup() {
        return ResponseEntity.ok(boardService.getStorePickUpFAQ());
    }

    @Operation(summary = "FAQ 주문 내역의 모든 글을 가져옵니다.", description = "FAQ 주문 내역의 모든 글을 가져옵니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "주문 내역 조회 성공", content = @Content(schema = @Schema(implementation = List.class))),
            @ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content),
            @ApiResponse(responseCode = "500", description = "서버 오류", content = @Content)
    })
    @GetMapping("/faq-order-history")
    public ResponseEntity<List<ReadBoardResponse>> getFaqOrderHistory() {
        return ResponseEntity.ok(boardService.getOrderHistoryFAQ());
    }

    @Operation(summary = "FAQ 주문 알림의 모든 글을 가져옵니다.", description = "FAQ 주문 알림의 모든 글을 가져옵니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "주문 알림 조회 성공", content = @Content(schema = @Schema(implementation = List.class))),
            @ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content),
            @ApiResponse(responseCode = "500", description = "서버 오류", content = @Content)
    })
    @GetMapping("/faq-order-notification")
    public ResponseEntity<List<ReadBoardResponse>> getFaqOrderNotification() {
        return ResponseEntity.ok(boardService.getOrderNotificationFAQ());
    }

    @Operation(summary = "FAQ 앱 주요기능의 모든 글을 가져옵니다.", description = "FAQ 앱 주요기능의 모든 글을 가져옵니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "앱 주요기능 조회 성공", content = @Content(schema = @Schema(implementation = List.class))),
            @ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content),
            @ApiResponse(responseCode = "500", description = "서버 오류", content = @Content)
    })
    @GetMapping("/faq-main-feature")
    public ResponseEntity<List<ReadBoardResponse>> getFaqMainFeature() {
        return ResponseEntity.ok(boardService.getMainFeatureFAQ());
    }

    @Operation(summary = "FAQ 앱 사용방법의 모든 글을 가져옵니다.", description = "FAQ 앱 사용방법의 모든 글을 가져옵니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "앱 사용방법 조회 성공", content = @Content(schema = @Schema(implementation = List.class))),
            @ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content),
            @ApiResponse(responseCode = "500", description = "서버 오류", content = @Content)
    })
    @GetMapping("/faq-how-to-use")
    public ResponseEntity<List<ReadBoardResponse>> getFaqHowToUse() {
        return ResponseEntity.ok(boardService.getHowToUseFAQ());
    }

    @Operation(summary = "FAQ 결제의 모든 글을 가져옵니다.", description = "FAQ 결제의 모든 글을 가져옵니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "결제 조회 성공", content = @Content(schema = @Schema(implementation = List.class))),
            @ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content),
            @ApiResponse(responseCode = "500", description = "서버 오류", content = @Content)
    })
    @GetMapping("/faq-payment")
    public ResponseEntity<List<ReadBoardResponse>> getFaqPayment() {
        return ResponseEntity.ok(boardService.getPaymentFAQ());
    }

    @Operation(summary = "FAQ 쿠폰의 모든 글을 가져옵니다.", description = "FAQ 쿠폰의 모든 글을 가져옵니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "쿠폰 조회 성공", content = @Content(schema = @Schema(implementation = List.class))),
            @ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content),
            @ApiResponse(responseCode = "500", description = "서버 오류", content = @Content)
    })
    @GetMapping("/faq-coupon")
    public ResponseEntity<List<ReadBoardResponse>> getFaqCoupon() {
        return ResponseEntity.ok(boardService.getCouponFAQ());
    }

    @Operation(summary = "FAQ 이벤트의 모든 글을 가져옵니다.", description = "FAQ 이벤트의 모든 글을 가져옵니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "이벤트 조회 성공", content = @Content(schema = @Schema(implementation = List.class))),
            @ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content),
            @ApiResponse(responseCode = "500", description = "서버 오류", content = @Content)
    })
    @GetMapping("/faq-event")
    public ResponseEntity<List<ReadBoardResponse>> getFaqEvent() {
        return ResponseEntity.ok(boardService.getEventFAQ());
    }

    @Operation(summary = "FAQ 기타의 모든 글을 가져옵니다.", description = "FAQ 기타의 모든 글을 가져옵니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "기타 조회 성공", content = @Content(schema = @Schema(implementation = List.class))),
            @ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content),
            @ApiResponse(responseCode = "500", description = "서버 오류", content = @Content)
    })
    @GetMapping("/faq-etc")
    public ResponseEntity<List<ReadBoardResponse>> getFaqEtc() {
        return ResponseEntity.ok(boardService.getEtcFAQ());
    }
}
