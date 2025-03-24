package com.FreeBoard.FreeBoard_EventHandler_Spring.model.interfaces;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import reactor.core.publisher.Mono;

public interface AuthenticationContextService {
    Mono<AbstractAuthenticationToken> createAbstractAuthenticationContext(String jwt);
}
