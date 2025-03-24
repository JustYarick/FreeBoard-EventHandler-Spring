package com.FreeBoard.FreeBoard_EventHandler_Spring.model.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ShapeEntity {
    @JsonProperty("type")
    private String type;
    @JsonProperty("attrs")
    private ShapeAttributesEntity attrs;
}