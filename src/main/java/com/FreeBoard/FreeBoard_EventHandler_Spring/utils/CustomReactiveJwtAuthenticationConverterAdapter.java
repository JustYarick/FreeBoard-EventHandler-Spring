package com.FreeBoard.FreeBoard_EventHandler_Spring.utils;

import com.FreeBoard.FreeBoard_EventHandler_Spring.model.interfaces.AuthenticationContextService;
import lombok.AllArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@AllArgsConstructor
public class CustomReactiveJwtAuthenticationConverterAdapter implements Converter<Jwt, Mono<AbstractAuthenticationToken>> {

    private final AuthenticationContextService authenticationContextService;

    @Override
    public Mono<AbstractAuthenticationToken> convert(Jwt jwt) {
        return authenticationContextService.createAbstractAuthenticationContext(jwt.getTokenValue())
                .onErrorMap(AuthenticationException.class, e -> e);
    }
}