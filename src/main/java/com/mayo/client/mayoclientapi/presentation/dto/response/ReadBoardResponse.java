package com.mayo.client.mayoclientapi.presentation.dto.response;

import com.google.cloud.Timestamp;
import com.mayo.client.mayoclientapi.persistence.domain.Board;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
public record ReadBoardResponse(
        String boardId,
        String title,
        String content,
        Integer category,
        @Schema(nullable = true)
        String image,
        @Schema(nullable = true)
        String writer,
        @Schema(nullable = true)
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
