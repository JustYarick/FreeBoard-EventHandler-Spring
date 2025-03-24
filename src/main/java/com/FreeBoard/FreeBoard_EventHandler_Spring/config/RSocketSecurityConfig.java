package com.FreeBoard.FreeBoard_EventHandler_Spring.config;

import com.FreeBoard.FreeBoard_EventHandler_Spring.utils.CustomReactiveJwtAuthenticationConverterAdapter;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.rsocket.EnableRSocketSecurity;
import org.springframework.security.config.annotation.rsocket.RSocketSecurity;
import org.springframework.security.oauth2.jwt.NimbusReactiveJwtDecoder;
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtReactiveAuthenticationManager;
import org.springframework.security.rsocket.core.PayloadSocketAcceptorInterceptor;

import javax.crypto.SecretKey;
import java.util.Base64;

@Configuration
@EnableRSocketSecurity
@RequiredArgsConstructor
public class RSocketSecurityConfig {

    @Value("${jwt.secret-key}")
    private String secretKey;

    private final CustomReactiveJwtAuthenticationConverterAdapter customReactiveJwtAuthenticationConverterAdapter;

    @Bean
    PayloadSocketAcceptorInterceptor rsocketInterceptor(RSocketSecurity rsocket) {
        rsocket
                .jwt(jwtSpec -> jwtSpec.authenticationManager(jwtReactiveAuthenticationManager()));
        return rsocket.build();
    }

    @Bean
    public JwtReactiveAuthenticationManager jwtReactiveAuthenticationManager() {
        JwtReactiveAuthenticationManager jwtReactiveAuthenticationManager =
                new JwtReactiveAuthenticationManager(reactiveJwtDecoder());
        jwtReactiveAuthenticationManager.setJwtAuthenticationConverter(customReactiveJwtAuthenticationConverterAdapter);
        return jwtReactiveAuthenticationManager;
    }

    @Bean
    public ReactiveJwtDecoder reactiveJwtDecoder() {
        SecretKey key = Keys.hmacShaKeyFor(Base64.getDecoder().decode(secretKey));
        return NimbusReactiveJwtDecoder.withSecretKey(key).build();
    }
}
