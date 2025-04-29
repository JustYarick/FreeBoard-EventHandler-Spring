package com.FreeBoard.FreeBoard_EventHandler_Spring.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
@Slf4j
public class SecurityService {

    public Mono<String> getCurrentUserUUID() {
        return ReactiveSecurityContextHolder.getContext()
                .map(SecurityContext::getAuthentication)
                .switchIfEmpty(Mono.error(new IllegalStateException("No SecurityContext found")))
                .map(Authentication::getPrincipal)
                .switchIfEmpty(Mono.error(new IllegalStateException("No Authentication  found")))
                .cast(Jwt.class)
                .map(jwt -> jwt.getSubject()); // "sub" claim
    }
}