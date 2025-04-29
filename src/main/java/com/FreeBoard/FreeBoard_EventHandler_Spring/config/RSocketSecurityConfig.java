package com.FreeBoard.FreeBoard_EventHandler_Spring.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.rsocket.EnableRSocketSecurity;
import org.springframework.security.config.annotation.rsocket.RSocketSecurity;
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoder;
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoders;
import org.springframework.security.rsocket.core.PayloadSocketAcceptorInterceptor;

@Configuration
@EnableRSocketSecurity
@Slf4j
public class RSocketSecurityConfig {

    @Value("${spring.security.oauth2.resourceserver.jwt.issuer-uri}")
    private String issuerDecoderUrl;

    @Bean
    PayloadSocketAcceptorInterceptor rsocketInterceptor(RSocketSecurity rsocket) {
        rsocket
                .authorizePayload(authorize ->
                        authorize
                                .anyRequest().authenticated()
                                .anyExchange().permitAll()
                )
                .jwt(Customizer.withDefaults());
        return rsocket.build();
    }

    @Bean
    public ReactiveJwtDecoder jwtDecoder() {
        log.info("JWT Decoder initialized with url: {}", issuerDecoderUrl);
        return ReactiveJwtDecoders
                .fromIssuerLocation(issuerDecoderUrl);
    }
}
