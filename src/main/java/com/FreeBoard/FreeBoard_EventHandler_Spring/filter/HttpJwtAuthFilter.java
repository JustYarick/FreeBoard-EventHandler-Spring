package com.FreeBoard.FreeBoard_EventHandler_Spring.filter;

import com.FreeBoard.FreeBoard_EventHandler_Spring.model.interfaces.AuthenticationContextService;
import com.FreeBoard.FreeBoard_EventHandler_Spring.service.JwtService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@AllArgsConstructor
@Component
public class HttpJwtAuthFilter implements WebFilter {

    private final JwtService jwtService;
    private final AuthenticationContextService authenticationContextService;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {

        ServerHttpRequest request = exchange.getRequest();

        // Проверяем наличие заголовка Authorization
        if (!request.getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
            HttpHeaders headers = request.getHeaders();
            return chain.filter(exchange);
        }

        String authHeader = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return chain.filter(exchange);
        }

        String token = authHeader.substring(7);
        return authenticationContextService.createAbstractAuthenticationContext(token)
                .flatMap(authentication -> chain.filter(exchange)
                        .contextWrite(ReactiveSecurityContextHolder.withAuthentication((Authentication) authentication)));
    }
}