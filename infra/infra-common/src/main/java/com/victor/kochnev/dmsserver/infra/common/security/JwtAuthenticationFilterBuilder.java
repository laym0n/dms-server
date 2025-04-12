package com.victor.kochnev.dmsserver.infra.common.security;

import com.victor.kochnev.dmsserver.auth.api.AuthenticationFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilterBuilder {
    private final AuthenticationFacade authenticationFacade;

    public JwtAuthenticationFilter build() {
        return new JwtAuthenticationFilter(authenticationFacade);
    }
}
