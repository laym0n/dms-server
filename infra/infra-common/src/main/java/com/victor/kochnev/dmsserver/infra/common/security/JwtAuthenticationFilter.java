package com.victor.kochnev.dmsserver.infra.common.security;

import com.victor.kochnev.dmsserver.auth.model.AuthenticateResponse;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.CollectionUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtCookieAuthenticationService jwtCookieAuthenticationService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        Optional<AuthenticateResponse> optionalAuthenticateResponse = Optional.empty();
        if (SecurityContextHolder.getContext().getAuthentication() == null) {
            optionalAuthenticateResponse = jwtCookieAuthenticationService.tryAuthenticate(request);
        }
        filterChain.doFilter(request, response);
        optionalAuthenticateResponse
                .ifPresent(authenticateResponse -> trySetCookies(response, authenticateResponse));
    }

    private void trySetCookies(HttpServletResponse response, AuthenticateResponse authenticateResponse) {
        var setCookieHeaders = response.getHeaders("Set-Cookie");
        if (CollectionUtils.isEmpty(setCookieHeaders)) {
            return;
        }
        var containsAuthSetCookie = setCookieHeaders.stream()
                .anyMatch(authHeader ->
                        authHeader.contains(JwtCookieUtils.JWT_ACCESS_TOKEN_COOKIE_NAME)
                                || authHeader.contains(JwtCookieUtils.JWT_REFRESH_TOKEN_COOKIE_NAME));
        if (containsAuthSetCookie) {
            return;
        }
        try {
            JwtCookieUtils.addAccessAndRefreshCookies(response, authenticateResponse);
        } catch (Exception e) {
            log.error(ExceptionUtils.getMessage(e));
        }
    }


}
