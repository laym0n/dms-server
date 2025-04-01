package com.victor.kochnev.dmsserver.infra.api.security;

import com.victor.kochnev.dmsserver.auth.api.AuthenticationFacade;
import com.victor.kochnev.dmsserver.auth.model.AuthenticateResponse;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.CollectionUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final AuthenticationFacade authenticationFacade;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        Optional<AuthenticateResponse> optionalAuthenticateResponse = Optional.empty();
        if (SecurityContextHolder.getContext().getAuthentication() == null) {
            optionalAuthenticateResponse = tryAuthenticate(request, response);
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

    private Optional<AuthenticateResponse> tryAuthenticate(HttpServletRequest request, HttpServletResponse response) {
        try {
            if (tryAuthenticateViaAccessToken(request))
                return Optional.empty();
            return tryAuthenticateViaRefreshToken(request, response);
        } catch (Exception e) {
            log.error(ExceptionUtils.getMessage(e));
            return Optional.empty();
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

    private Optional<AuthenticateResponse> tryAuthenticateViaRefreshToken(HttpServletRequest request, HttpServletResponse response) {
        if (request.getCookies() == null) {
            return Optional.empty();
        }
        var optionalRefreshToken = Arrays.stream(request.getCookies())
                .filter(cookie -> JwtCookieUtils.JWT_REFRESH_TOKEN_COOKIE_NAME.equals(cookie.getName()))
                .map(Cookie::getValue)
                .findFirst();
        if (optionalRefreshToken.isEmpty()) {
            return Optional.empty();
        }
        String token = optionalRefreshToken.get();
        try {
            var authResponse = authenticationFacade.refresh(token);
            authenticationFacade.authenticate(authResponse.getJwtToken().getAccessToken());
            return Optional.of(authResponse);
        } catch (Exception e) {
            log.error(ExceptionUtils.getMessage(e));
            return Optional.empty();
        }
    }
}
