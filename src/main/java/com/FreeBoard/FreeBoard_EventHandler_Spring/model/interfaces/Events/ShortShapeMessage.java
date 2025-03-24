package com.FreeBoard.FreeBoard_EventHandler_Spring.model.interfaces.Events;

import java.util.List;

public interface ShortShapeMessage extends BoardMessageEvent {
    List<Integer> getShapeIds();
}
