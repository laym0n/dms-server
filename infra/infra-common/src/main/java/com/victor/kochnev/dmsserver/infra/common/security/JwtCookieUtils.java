package com.victor.kochnev.dmsserver.infra.common.security;

import com.victor.kochnev.dmsserver.auth.model.AuthenticateResponse;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

public final class JwtCookieUtils {
    public static final String JWT_ACCESS_TOKEN_COOKIE_NAME = "accessToken";
    public static final String JWT_REFRESH_TOKEN_COOKIE_NAME = "refreshToken";

    private JwtCookieUtils() {
    }

    public static void addAccessAndRefreshCookies(HttpServletResponse response, AuthenticateResponse authenticationResponse) {
        var cookieAccessToken = new Cookie(JwtCookieUtils.JWT_ACCESS_TOKEN_COOKIE_NAME, authenticationResponse.getJwtToken().getAccessToken());
        cookieAccessToken.setHttpOnly(true);
        cookieAccessToken.setPath("/");
        cookieAccessToken.setMaxAge((int) authenticationResponse.getJwtToken().getAccessTokenLiveDuration().toSeconds());

        var cookieRefreshToken = new Cookie(JwtCookieUtils.JWT_REFRESH_TOKEN_COOKIE_NAME, authenticationResponse.getJwtToken().getRefreshToken());
        cookieRefreshToken.setHttpOnly(true);
        cookieRefreshToken.setPath("/");
        cookieRefreshToken.setMaxAge((int) authenticationResponse.getJwtToken().getRefreshTokenLiveDuration().toSeconds());

        response.addCookie(cookieAccessToken);
        response.addCookie(cookieRefreshToken);
    }
}
