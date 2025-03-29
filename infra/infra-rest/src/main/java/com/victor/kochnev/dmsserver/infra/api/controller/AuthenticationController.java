package com.victor.kochnev.dmsserver.infra.api.controller;

import com.victor.kochnev.dmsserver.auth.api.AuthenticationFacade;
import com.victor.kochnev.dmsserver.auth.model.AuthenticateResponse;
import com.victor.kochnev.dmsserver.auth.model.AuthenticationRefreshRequest;
import com.victor.kochnev.dmsserver.auth.model.AuthenticationRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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

@RestController
@CrossOrigin
@Validated
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Authentication")
public class AuthenticationController {
    private static final String AUTHENTICATION_ENDPOINT = "POST /authentication";
    private static final String AUTHENTICATION_REFRESH_ENDPOINT = "POST /authentication/refresh";
    private final AuthenticationFacade authenticationFacade;

    @PostMapping("/authentication")
    @Operation(operationId = "authenticate")
    public ResponseEntity<AuthenticateResponse> authenticate(@Valid @RequestBody @NotNull AuthenticationRequest requestBody) {
        log.info("Request: {}", AUTHENTICATION_ENDPOINT);
        log.debug("Request: {} {}", AUTHENTICATION_ENDPOINT, requestBody);

        var authenticationResponse = authenticationFacade.authenticate(requestBody);

        log.info("Request: {} proccesed", AUTHENTICATION_ENDPOINT);
        return ResponseEntity.ok(authenticationResponse);
    }

    @PostMapping("/authentication/refresh")
    @Operation(operationId = "refreshAuthentication")
    public ResponseEntity<AuthenticateResponse> refreshAuthentication(@Valid @RequestBody @NotNull AuthenticationRefreshRequest request) {
        log.info("Request: {}", AUTHENTICATION_REFRESH_ENDPOINT);
        log.debug("Request: {} {}", AUTHENTICATION_REFRESH_ENDPOINT, request);

        var authenticationResponse = authenticationFacade.refresh(request);

        log.info("Request: {} proccesed", AUTHENTICATION_REFRESH_ENDPOINT);
        return ResponseEntity.ok(authenticationResponse);
    }

}
