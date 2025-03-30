package com.victor.kochnev.dmsserver.infra.api.security;

import com.victor.kochnev.dmsserver.auth.api.AuthenticationFacade;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;

@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final AuthenticationFacade authenticationFacade;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (SecurityContextHolder.getContext().getAuthentication() == null) {
            tryAuthenticate(request, response);
        }
        filterChain.doFilter(request, response);
    }

    private void tryAuthenticate(HttpServletRequest request, HttpServletResponse response) {
        try {
            if (tryAuthenticateViaAccessToken(request))
                return;
            tryAuthenticateViaRefreshToken(request, response);
        } catch (Exception e) {
            log.error(ExceptionUtils.getMessage(e));
        }
    }

    private boolean tryAuthenticateViaAccessToken(HttpServletRequest request) {
        if (request.getCookies() == null) {
            return false;
        }
        var optionalAccessToken = Arrays.stream(request.getCookies())
                .filter(cookie -> JwtCookieUtils.JWT_ACCESS_TOKEN_COOKIE_NAME.equals(cookie.getName()))
                .map(Cookie::getValue)
                .findFirst();
        if (optionalAccessToken.isEmpty()) {
            return false;
        }
        String token = optionalAccessToken.get();
        try {
            authenticationFacade.authenticate(token);
        } catch (Exception e) {
            log.error(ExceptionUtils.getMessage(e));
            return false;
        }
        return true;
    }

    private void tryAuthenticateViaRefreshToken(HttpServletRequest request, HttpServletResponse response) {
        if (request.getCookies() == null) {
            return;
        }
        var optionalRefreshToken = Arrays.stream(request.getCookies())
                .filter(cookie -> JwtCookieUtils.JWT_REFRESH_TOKEN_COOKIE_NAME.equals(cookie.getName()))
                .map(Cookie::getValue)
                .findFirst();
        if (optionalRefreshToken.isEmpty()) {
            return;
        }
        String token = optionalRefreshToken.get();
        try {
            var authResponse = authenticationFacade.refresh(token);
            JwtCookieUtils.addAccessAndRefreshCookies(response, authResponse);
            authenticationFacade.authenticate(authResponse.getJwtToken().getAccessToken());
        } catch (Exception e) {
            log.error(ExceptionUtils.getMessage(e));
        }
    }
}
