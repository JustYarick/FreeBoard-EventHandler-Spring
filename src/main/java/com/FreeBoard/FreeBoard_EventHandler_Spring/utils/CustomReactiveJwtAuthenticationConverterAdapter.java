package com.FreeBoard.FreeBoard_EventHandler_Spring.utils;

import io.rsocket.exceptions.RejectedSetupException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.util.Collections;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class CustomReactiveJwtAuthenticationConverterAdapter implements Converter<Jwt, Mono<AbstractAuthenticationToken>> {

    @Override
    public Mono<AbstractAuthenticationToken> convert(Jwt jwt) {
        if (jwt.getSubject() == null) {
            return Mono.error(new BadCredentialsException("JWT subject is missing"));
        }

        if (isExpired(jwt)) {
            return Mono.error(new BadCredentialsException("JWT token expired"));
        }
        String role = Optional.ofNullable(jwt.getClaim("role"))
                .map(Object::toString)
                .orElse("ROLE_USER");

        return Mono.just(new UsernamePasswordAuthenticationToken(
                jwt,
                null,
                Collections.singletonList(new SimpleGrantedAuthority(role))
        ));
    }

    private boolean isExpired(Jwt jwt) {
        Instant expiration = jwt.getExpiresAt();
        if (Instant.now().isAfter(expiration)) {
            System.out.println("Expired JWT");
            throw new RuntimeException("JWT expired");
        }
        return expiration != null && expiration.isBefore(Instant.now());
    }
}