package com.mayo.client.mayoclientapi.application.service;

import com.mayo.client.mayoclientapi.common.annotation.FirestoreTransactional;
import com.mayo.client.mayoclientapi.common.exception.ApplicationException;
import com.mayo.client.mayoclientapi.common.exception.payload.ErrorStatus;
import com.mayo.client.mayoclientapi.persistence.domain.Board;
import com.mayo.client.mayoclientapi.persistence.repository.BoardRepository;
import com.mayo.client.mayoclientapi.presentation.dto.response.ReadBoardResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class BoardService {

    private final BoardRepository boardRepository;

    public List<ReadBoardResponse> getTermsBoard() {
        return boardRepository.getTermsBoard().stream().map(ReadBoardResponse::from).toList();
    }

    public List<ReadBoardResponse> getNoticeBoard() {
        return boardRepository.getNoticeBoard().stream().map(ReadBoardResponse::from).toList();
    }

    public List<ReadBoardResponse> getEventBoard() {
        return boardRepository.getEventBoard().stream().map(ReadBoardResponse::from).toList();
    }

    public List<ReadBoardResponse> getUpdateInfoFAQ() {
        return boardRepository.findUpdateInfo().stream().map(ReadBoardResponse::from).toList();
    }

    public List<ReadBoardResponse> getDeleteAccountFAQ() {
        return boardRepository.findDeleteAccount().stream().map(ReadBoardResponse::from).toList();
    }

    public List<ReadBoardResponse> getStorePickUpFAQ() {
        return boardRepository.findStorePickUp().stream().map(ReadBoardResponse::from).toList();
    }

    public List<ReadBoardResponse> getOrderHistoryFAQ() {
        return boardRepository.findOrderHistory().stream().map(ReadBoardResponse::from).toList();
    }

    public List<ReadBoardResponse> getOrderNotificationFAQ() {
        return boardRepository.findOrderNotification().stream().map(ReadBoardResponse::from).toList();
    }

    public List<ReadBoardResponse> getMainFeatureFAQ() {
        return boardRepository.findMainFeature().stream().map(ReadBoardResponse::from).toList();
    }

    public List<ReadBoardResponse> getHowToUseFAQ() {
        return boardRepository.findHowToUse().stream().map(ReadBoardResponse::from).toList();
    }

    public List<ReadBoardResponse> getPaymentFAQ() {
        return boardRepository.findPayment().stream().map(ReadBoardResponse::from).toList();
    }

    public List<ReadBoardResponse> getCouponFAQ() {
        return boardRepository.findCoupon().stream().map(ReadBoardResponse::from).toList();
    }

    public List<ReadBoardResponse> getEventFAQ() {
        return boardRepository.findEventFAQ().stream().map(ReadBoardResponse::from).toList();
    }

    public List<ReadBoardResponse> getEtcFAQ() {
        return boardRepository.findETC().stream().map(ReadBoardResponse::from).toList();
    }

    public ReadBoardResponse getBoard(String id) {
        Board board = boardRepository.getBoardById(id)
                .orElseThrow(() -> new ApplicationException(
                        ErrorStatus.toErrorStatus("해당하는 게시글이 없습니다.", 404, LocalDateTime.now())
                ));

        return ReadBoardResponse.from(board);
    }
}
