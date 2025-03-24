package com.FreeBoard.FreeBoard_EventHandler_Spring.config;

import com.FreeBoard.FreeBoard_EventHandler_Spring.utils.BearerTokenMetadataDecoder;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.codec.ByteBufferDecoder;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.messaging.rsocket.DefaultMetadataExtractor;
import org.springframework.messaging.rsocket.MetadataExtractor;
import org.springframework.messaging.rsocket.RSocketStrategies;
import org.springframework.messaging.rsocket.annotation.support.RSocketMessageHandler;
import org.springframework.security.rsocket.metadata.BearerTokenMetadata;
import org.springframework.util.MimeType;
import org.springframework.util.MimeTypeUtils;

@Configuration
@Slf4j
public class RSocketConfig {

    @Bean
    public BearerTokenMetadataDecoder bearerTokenMetadataDecoder() {
        return new BearerTokenMetadataDecoder();
    }


    @Bean
    public RSocketStrategies rsocketStrategies(BearerTokenMetadataDecoder bearerTokenMetadataDecoder) {
        return RSocketStrategies.builder()
                .decoder(bearerTokenMetadataDecoder)
                .decoder(new ByteBufferDecoder())
                .decoder(new Jackson2JsonDecoder(new ObjectMapper(), new MimeType("application", "octet-stream")))
                .build();
    }

    @Bean
    public MetadataExtractor metadataExtractor(RSocketStrategies strategies) {
        MetadataExtractor extractor = strategies.metadataExtractor();

        if (extractor instanceof DefaultMetadataExtractor defaultExtractor) {

            defaultExtractor.metadataToExtract(
                    MimeTypeUtils.parseMimeType("message/x.rsocket.authentication.bearer.v0"),
                    BearerTokenMetadata.class,
                    (metadata, map) -> {
                        map.put("JWT", metadata.getToken());
                    }
            );
        }
        return extractor;
    }


    @Bean
    public RSocketMessageHandler rsocketMessageHandler(RSocketStrategies strategies) {
        RSocketMessageHandler handler = new RSocketMessageHandler();
        handler.setRSocketStrategies(strategies);
        return handler;
    }
}