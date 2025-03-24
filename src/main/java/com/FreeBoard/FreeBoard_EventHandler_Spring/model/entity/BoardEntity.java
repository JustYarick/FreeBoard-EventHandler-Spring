package com.FreeBoard.FreeBoard_EventHandler_Spring.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BoardEntity {
    private String id;
    @Builder.Default
    private String name = "New Board";
    private String ownerId;
    private List<String> authorizedUserIds;
    @Builder.Default
    private List<ShapeEntity> content = new ArrayList<>();
    private Instant createdAt;
}