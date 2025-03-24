package com.FreeBoard.FreeBoard_EventHandler_Spring.model.DTO;

import com.FreeBoard.FreeBoard_EventHandler_Spring.model.interfaces.Events.BoardMessageEvent;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ToProcessMessage {
    @JsonProperty("shapeMessageDTO")
    private ShapeMessageDTO shapeMessageDTO;
    @JsonProperty("userUuid")
    private String userUuid;
}
