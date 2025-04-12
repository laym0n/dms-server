package com.victor.kochnev.dmsserver.infra.api.controller;

import com.victor.kochnev.dmsserver.auth.api.AuthenticationFacade;
import com.victor.kochnev.dmsserver.auth.model.AuthenticateResponse;
import com.victor.kochnev.dmsserver.auth.model.AuthenticationRequest;
import com.victor.kochnev.dmsserver.auth.model.JwtTokenDto;
import com.victor.kochnev.dmsserver.infra.common.security.JwtCookieUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;

@RestController
@CrossOrigin
@Validated
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Authentication")
public class AuthenticationController {
    private static final String AUTHENTICATION_ENDPOINT = "POST /authentication";
    private final AuthenticationFacade authenticationFacade;

    @PostMapping("/authentication")
    @Operation(operationId = "authenticate")
    public ResponseEntity<Void> authenticate(@Valid @RequestBody @NotNull AuthenticationRequest requestBody, HttpServletResponse response) {
        log.info("Request: {}", AUTHENTICATION_ENDPOINT);
        log.debug("Request: {} {}", AUTHENTICATION_ENDPOINT, requestBody);

        var authenticationResponse = authenticationFacade.authenticate(requestBody);

        log.info("Request: {} proccesed", AUTHENTICATION_ENDPOINT);

        JwtCookieUtils.addAccessAndRefreshCookies(response, authenticationResponse);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/authentication/logout")
    public ResponseEntity<Void> logout(HttpServletResponse response) {
        var authenticateResponse = new AuthenticateResponse();
        authenticateResponse.setJwtToken(new JwtTokenDto());
        authenticateResponse.getJwtToken().setAccessToken("");
        authenticateResponse.getJwtToken().setRefreshToken("");
        authenticateResponse.getJwtToken().setAccessTokenLiveDuration(Duration.ZERO);
        authenticateResponse.getJwtToken().setRefreshTokenLiveDuration(Duration.ZERO);

        JwtCookieUtils.addAccessAndRefreshCookies(response, authenticateResponse);

        return ResponseEntity.ok().build();
    }
}
