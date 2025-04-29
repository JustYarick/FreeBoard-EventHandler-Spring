package com.FreeBoard.FreeBoard_EventHandler_Spring.service;

import com.FreeBoard.FreeBoard_EventHandler_Spring.model.DTO.ShapeMessageDTO;
import com.FreeBoard.FreeBoard_EventHandler_Spring.model.DTO.ToProcessMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class BoardMessageService {

    public BoardMessageService(KafkaTemplate<String, ToProcessMessage> boardMessageKafkaTemplate,
                                  SecurityService securityService,
                                  @Value("${spring.kafka.events-topic}") String topicName) {
        this.boardMessageKafkaTemplate = boardMessageKafkaTemplate;
        this.securityService = securityService;
        this.topicName = topicName;
    }

    private final KafkaTemplate<String, ToProcessMessage> boardMessageKafkaTemplate;
    private final SecurityService securityService;

    private final String topicName;



    public Mono<String> sendEvent(ShapeMessageDTO boardMessage, String event) {
        return securityService.getCurrentUserUUID()
                .flatMap(uuid -> Mono.fromFuture(() ->
                        boardMessageKafkaTemplate.send(topicName, "",
                                ToProcessMessage.builder()
                                        .eventType(event)
                                        .shapeMessageDTO(boardMessage)
                                        .userUuid(uuid)
                                        .build()
                        ))
                        .doOnError(ex -> log.error("Failed to send Kafka message: {}", ex.getMessage(), ex)))
                .map(result -> {
                    if (result != null && result.getRecordMetadata() != null) return "success";
                    else return "failed";
                });
    }
}
