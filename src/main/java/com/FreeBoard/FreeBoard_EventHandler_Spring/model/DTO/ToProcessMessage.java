package com.FreeBoard.FreeBoard_EventHandler_Spring.model.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ToProcessMessage {
    @JsonProperty("eventType")
    private String eventType;
    @JsonProperty("shapeMessageDTO")
    private ShapeMessageDTO shapeMessageDTO;
    @JsonProperty("userUuid")
    private String userUuid;
}
