package com.mayo.client.mayoclientapi.presentation.dto.response;

import com.google.cloud.Timestamp;
import com.mayo.client.mayoclientapi.persistance.domain.Board;
import lombok.Builder;

@Builder
public record ReadBoardResponse(
        String boardId,
        String title,
        String content,
        Integer category,
        String image,
        String writer,
        Timestamp writeTime
) {
    public static ReadBoardResponse from(Board board) {
        return ReadBoardResponse.builder()
                .boardId(board.getBoardId())
                .title(board.getTitle())
                .content(board.getContent())
                .category(board.getCategory())
                .image(board.getImage())
                .writer(board.getWriter())
                .writeTime(board.getWriteTime())
                .build();
    }
}
