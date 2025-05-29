package com.victor.kochnev.dmsserver.infra.common.security;

import com.victor.kochnev.dmsserver.auth.api.AuthenticationFacade;
import com.victor.kochnev.dmsserver.auth.model.AuthenticateResponse;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtCookieAuthenticationService {
    private final AuthenticationFacade authenticationFacade;

    public Optional<AuthenticateResponse> tryAuthenticate(HttpServletRequest request) {
        try {
            if (tryAuthenticateViaAccessToken(request))
                return Optional.empty();
            return tryAuthenticateViaRefreshToken(request);
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

    private Optional<AuthenticateResponse> tryAuthenticateViaRefreshToken(HttpServletRequest request) {
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
