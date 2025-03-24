package com.FreeBoard.FreeBoard_EventHandler_Spring.service;

import com.FreeBoard.FreeBoard_EventHandler_Spring.model.interfaces.AuthenticationContextService;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Collections;

@Service
@AllArgsConstructor
public class SecurityService implements AuthenticationContextService {

    private final JwtService jwtService;

    public Mono<String> getCurrentUserUUID() {
        return ReactiveSecurityContextHolder.getContext()
                .flatMap(context -> {
                    Authentication authentication = context.getAuthentication();
                    Object principal = authentication.getPrincipal();
                    return Mono.just(principal.toString());
                });
    }


    @Override
    public Mono<AbstractAuthenticationToken> createAbstractAuthenticationContext(String jwt) {
        return Mono.justOrEmpty(jwtService.extractClaim(jwt))
                .filter(claims -> claims.getSubject() != null && !jwtService.isExpired(claims.getExpiration()))
                .map(claims -> {
                    String role = claims.get("role") != null ? claims.get("role").toString() : "ROLE_USER";
                    return new UsernamePasswordAuthenticationToken(
                            claims.getSubject(),
                            null,
                            Collections.singletonList(new SimpleGrantedAuthority(role))
                    );
                });
    }
}
