package com.victor.kochnev.dmsserver.auth.api.auth;

import com.victor.kochnev.dmsserver.auth.api.AuthenticationFacade;
import com.victor.kochnev.dmsserver.auth.api.auth.jwt.JwtService;
import com.victor.kochnev.dmsserver.auth.model.AuthenticateResponse;
import com.victor.kochnev.dmsserver.auth.model.AuthenticationRequest;
import com.victor.kochnev.dmsserver.common.security.SecurityUserService;
import com.victor.kochnev.dmsserver.common.security.UserSecurityModel;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

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
    public AuthenticateResponse refresh(String refreshToken) {
        var refreshDataFromToken = jwtService.getRefreshDataFromToken(refreshToken);
        var user = (UserSecurityModel) securityUserService.loadUserByUsername(refreshDataFromToken.getUser().getUsername());
        var jwtTokenDto = jwtService.generateJwtToken(refreshDataFromToken.isRememberMeSet(), user);
        return new AuthenticateResponse(jwtTokenDto);
    }

    @Override
    public void authenticate(String token) {
        var accessDataFromToken = jwtService.getAccessDataFromToken(token);
        var authentication = new UsernamePasswordAuthenticationToken(accessDataFromToken.getUser(), null, accessDataFromToken.getUser().getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
