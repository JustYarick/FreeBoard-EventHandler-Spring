package com.FreeBoard.FreeBoard_EventHandler_Spring.model.DTO;

import com.FreeBoard.FreeBoard_EventHandler_Spring.model.entity.ShapeEntity;
import com.FreeBoard.FreeBoard_EventHandler_Spring.model.interfaces.Events.BoardMessageEvent;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShapeMessageDTO implements BoardMessageEvent {
    @JsonProperty("boardId")
    private String boardId;
    @JsonProperty("shapes")
    private List<ShapeEntity> shapes;
}