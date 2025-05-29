package com.victor.kochnev.dmsserver.infra.p2psignaling.websocket;

import com.victor.kochnev.dmsserver.infra.common.security.JwtCookieAuthenticationService;
import com.victor.kochnev.dmsserver.profile.api.ProfileFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class AuthHandshakeInterceptor implements HandshakeInterceptor {

    private final JwtCookieAuthenticationService jwtCookieAuthenticationService;
    private final ProfileFacade profileFacade;

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response,
                                   WebSocketHandler wsHandler, Map<String, Object> attributes) {

        if (request instanceof ServletServerHttpRequest servletRequest) {
            jwtCookieAuthenticationService.tryAuthenticate(servletRequest.getServletRequest());
            var currentProfile = profileFacade.getAuthenticatedProfile();
            attributes.put("userId", currentProfile.getUser().getId());
            return true;
        }

        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        return false;
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response,
                               WebSocketHandler wsHandler, Exception exception) {
    }
}

