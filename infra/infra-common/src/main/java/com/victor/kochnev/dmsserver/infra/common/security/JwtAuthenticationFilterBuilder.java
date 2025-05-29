package com.victor.kochnev.dmsserver.infra.common.security;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilterBuilder {
    private final JwtCookieAuthenticationService jwtCookieAuthenticationService;

    public JwtAuthenticationFilter build() {
        return new JwtAuthenticationFilter(jwtCookieAuthenticationService);
    }
}
