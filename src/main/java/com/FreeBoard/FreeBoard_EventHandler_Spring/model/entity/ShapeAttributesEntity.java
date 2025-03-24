package com.FreeBoard.FreeBoard_EventHandler_Spring.model.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ShapeAttributesEntity {
    @JsonProperty("PUBLIC_ID")
    private int publicId;
    @JsonProperty("points")
    private double[] points;
    @JsonProperty("stroke")
    private String stroke;
    @JsonProperty("strokeWidth")
    private int strokeWidth;
    @JsonProperty("x")
    private int x;
    @JsonProperty("y")
    private int y;
    @JsonProperty("scaleX")
    private double scaleX;
    @JsonProperty("scaleY")
    private double scaleY;
    @JsonProperty("rotation")
    private double rotation;
    @JsonProperty("lineCap")
    private String lineCap;
    @JsonProperty("lineJoin")
    private String lineJoin;
}