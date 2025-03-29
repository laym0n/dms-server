package com.victor.kochnev.dmsserver.infra.api.security;

import com.victor.kochnev.dmsserver.auth.api.AuthenticationFacade;
import com.victor.kochnev.dmsserver.common.security.UserSecurityModel;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private static final String BEARER_PREFIX = "Bearer ";
    private static final String AUTHORIZATION_HEADER = "Authorization";
    private final AuthenticationFacade authenticationFacade;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (SecurityContextHolder.getContext().getAuthentication() == null) {
            tryAuthenticate(request);
        }
        filterChain.doFilter(request, response);
    }

    private void tryAuthenticate(HttpServletRequest request) {
        try {
            String requestHeader = request.getHeader(AUTHORIZATION_HEADER);
            UserSecurityModel user = null;
            if (requestHeader != null && requestHeader.startsWith(BEARER_PREFIX)) {
                String token = requestHeader.substring(BEARER_PREFIX.length());
                try {
                    authenticationFacade.authenticate(token);
                } catch (Exception e) {
                    log.error(ExceptionUtils.getMessage(e));
                }
            }
        } catch (Exception e) {
            log.error(ExceptionUtils.getMessage(e));
        }
    }
}
