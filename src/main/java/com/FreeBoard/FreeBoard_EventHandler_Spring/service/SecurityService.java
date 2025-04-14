package com.FreeBoard.FreeBoard_EventHandler_Spring.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
@Slf4j
public class SecurityService {

//    public Mono<String> getCurrentUserUUID() {
//        return ReactiveSecurityContextHolder.getContext()
//                .flatMap(context -> {
//                    Authentication authentication = context.getAuthentication();
//                    Object principal = authentication.getPrincipal();
//                    return Mono.just(principal.toString());
//                });
//    }

    public Mono<String> getCurrentUserUUID() {
        return ReactiveSecurityContextHolder.getContext()
                .map(securityContext -> securityContext.getAuthentication())
                .map(Authentication::getPrincipal)
                .cast(Jwt.class)
                .map(jwt -> jwt.getSubject()); // "sub" claim
    }
}