package com.FreeBoard.FreeBoard_EventHandler_Spring.controller.RSocket;

import com.FreeBoard.FreeBoard_EventHandler_Spring.model.DTO.ShapeMessageDTO;
import com.FreeBoard.FreeBoard_EventHandler_Spring.model.DTO.ToProcessMessage;
import com.FreeBoard.FreeBoard_EventHandler_Spring.service.SecurityService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Mono;

@Controller
@RequiredArgsConstructor
@MessageMapping("board-events")
@Slf4j
public class BoardMessageController {

    private final KafkaTemplate<String, ToProcessMessage> boardMessageKafkaTemplate;
    private final SecurityService securityService;

    private static final String TOPIC_NAME = "boardEventTopic";

    @MessageMapping("addShape")
    public Mono<Void> addShape(ShapeMessageDTO boardMessage) {
        return securityService.getCurrentUserUUID()
                .flatMap(uuid -> Mono.fromFuture(() ->
                        boardMessageKafkaTemplate.send(TOPIC_NAME, 0, "", new ToProcessMessage(boardMessage, uuid))).then());
    }

    @MessageMapping("deleteShape")
    public Mono<Void> deleteShape(ShapeMessageDTO boardMessage) {
        return securityService.getCurrentUserUUID()
                .flatMap(uuid -> Mono.fromFuture(() ->
                        boardMessageKafkaTemplate.send(TOPIC_NAME, 1, "", new ToProcessMessage(boardMessage, uuid))).then());
    }

    @MessageMapping("transformShape")
    public Mono<Void> transformShape(ShapeMessageDTO boardMessage) {
        return securityService.getCurrentUserUUID()
                .flatMap(uuid -> Mono.fromFuture(() ->
                        boardMessageKafkaTemplate.send(TOPIC_NAME, 2, "", new ToProcessMessage(boardMessage, uuid))).then());
    }
}
