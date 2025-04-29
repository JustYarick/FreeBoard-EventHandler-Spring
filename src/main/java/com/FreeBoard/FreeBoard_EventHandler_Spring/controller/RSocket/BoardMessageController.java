package com.FreeBoard.FreeBoard_EventHandler_Spring.controller.RSocket;

import com.FreeBoard.FreeBoard_EventHandler_Spring.model.DTO.ShapeMessageDTO;
import com.FreeBoard.FreeBoard_EventHandler_Spring.service.BoardMessageService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Mono;

@Controller
@MessageMapping("board-events")
@Slf4j
@AllArgsConstructor
public class BoardMessageController {

    private final BoardMessageService boardMessageService;

    @MessageMapping("addShape")
    public Mono<String> addShape(ShapeMessageDTO boardMessage) {
        return boardMessageService.sendEvent(boardMessage, "ADD_SHAPE");
    }


    @MessageMapping("deleteShape")
    public Mono<String> deleteShape(ShapeMessageDTO boardMessage) {
        return boardMessageService.sendEvent(boardMessage, "DELETE_SHAPE");
    }

    @MessageMapping("transformShape")
    public Mono<String> transformShape(ShapeMessageDTO boardMessage) {
        return boardMessageService.sendEvent(boardMessage, "TRANSFORM_SHAPE");
    }
}