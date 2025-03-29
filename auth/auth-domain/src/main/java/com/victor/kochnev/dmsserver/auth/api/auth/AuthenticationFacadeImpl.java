package com.victor.kochnev.dmsserver.auth.api.auth;

import com.victor.kochnev.dmsserver.auth.api.AuthenticationFacade;
import com.victor.kochnev.dmsserver.auth.model.AuthenticateResponse;
import com.victor.kochnev.dmsserver.auth.model.AuthenticationRefreshRequest;
import com.victor.kochnev.dmsserver.auth.model.AuthenticationRequest;
import com.victor.kochnev.dmsserver.common.security.SecurityUserService;
import com.victor.kochnev.dmsserver.common.security.UserSecurityModel;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.security.InvalidParameterException;

@Component
@RequiredArgsConstructor
public class AuthenticationFacadeImpl implements AuthenticationFacade {
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final SecurityUserService securityUserService;

    @Override
    public AuthenticateResponse authenticate(AuthenticationRequest request) {
        var authenticationToken = new UsernamePasswordAuthenticationToken(request.getLogin(), request.getPassword());
        Authentication authentication = authenticationManager.authenticate(authenticationToken);
        var userSecurity = (UserSecurityModel) authentication.getPrincipal();
        var jwtTokenDto = jwtService.generateJwtToken(request.getRememberMe(), userSecurity);
        return new AuthenticateResponse(jwtTokenDto);
    }

    @Override
    public AuthenticateResponse refresh(AuthenticationRefreshRequest request) {
        var user = jwtService.getUserFromToken(request.getRefreshToken());
        user = (UserSecurityModel) securityUserService.loadUserByUsername(user.getUsername());
        var jwtTokenDto = jwtService.generateJwtToken(request.getRememberMe(), user);
        return new AuthenticateResponse(jwtTokenDto);
    }

    @Override
    public void authenticate(String token) {
        var user = jwtService.getUserFromToken(token);
        if (user.getAuthorities().isEmpty()) {
            throw new InvalidParameterException("Passed refresh token");
        }
        var authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
