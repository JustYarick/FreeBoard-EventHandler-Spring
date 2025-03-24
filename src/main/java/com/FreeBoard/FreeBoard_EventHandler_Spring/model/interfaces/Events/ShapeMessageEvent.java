package com.FreeBoard.FreeBoard_EventHandler_Spring.model.interfaces.Events;


import com.FreeBoard.FreeBoard_EventHandler_Spring.model.entity.ShapeEntity;

public interface ShapeMessageEvent extends BoardMessageEvent  {
    ShapeEntity getShape();
}
