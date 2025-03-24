package com.FreeBoard.FreeBoard_EventHandler_Spring.model.DTO;




import com.FreeBoard.FreeBoard_EventHandler_Spring.model.entity.BoardEntity;

import java.time.Instant;

public record BoardDTO(
     String id,
     String name,
     Object content,
     Instant createdAt
) {
    public BoardDTO(BoardEntity board) {
        this(
            board.getId(),
            board.getName(),
            board.getContent(),
            board.getCreatedAt());
    }
}
