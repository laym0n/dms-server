package com.victor.kochnev.dmsserver.auth.api;

import com.victor.kochnev.dmsserver.auth.model.AuthenticateResponse;
import com.victor.kochnev.dmsserver.auth.model.AuthenticationRefreshRequest;
import com.victor.kochnev.dmsserver.auth.model.AuthenticationRequest;

public interface AuthenticationFacade {
    AuthenticateResponse authenticate(AuthenticationRequest request);

    AuthenticateResponse refresh(AuthenticationRefreshRequest request);

    void authenticate(String token);
}
