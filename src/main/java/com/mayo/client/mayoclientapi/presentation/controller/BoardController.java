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
}
