package com.FreeBoard.FreeBoard_EventHandler_Spring.config;

import com.FreeBoard.FreeBoard_EventHandler_Spring.utils.CustomReactiveJwtAuthenticationConverterAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.rsocket.EnableRSocketSecurity;
import org.springframework.security.config.annotation.rsocket.RSocketSecurity;
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoder;
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoders;
import org.springframework.security.oauth2.server.resource.authentication.JwtReactiveAuthenticationManager;
import org.springframework.security.rsocket.core.PayloadSocketAcceptorInterceptor;

@Configuration
@EnableRSocketSecurity
@RequiredArgsConstructor
public class RSocketSecurityConfig {

    private final CustomReactiveJwtAuthenticationConverterAdapter customReactiveJwtAuthenticationConverterAdapter;

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

//    @Bean
//    PayloadSocketAcceptorInterceptor rsocketInterceptor(RSocketSecurity rsocket) {
//        rsocket
//                .jwt(jwtSpec -> jwtSpec.authenticationManager(jwtReactiveAuthenticationManager(jwtDecoder())));
//        return rsocket.build();
//    }

    @Bean
    public JwtReactiveAuthenticationManager jwtReactiveAuthenticationManager(ReactiveJwtDecoder reactiveJwtDecoder) {
        JwtReactiveAuthenticationManager jwtReactiveAuthenticationManager = new JwtReactiveAuthenticationManager(reactiveJwtDecoder);
        jwtReactiveAuthenticationManager.setJwtAuthenticationConverter(customReactiveJwtAuthenticationConverterAdapter);
        return jwtReactiveAuthenticationManager;
    }

    @Bean
    public ReactiveJwtDecoder jwtDecoder() {
        return ReactiveJwtDecoders
                .fromIssuerLocation("http://localhost:7999/realms/FreeBoard");
    }
}
