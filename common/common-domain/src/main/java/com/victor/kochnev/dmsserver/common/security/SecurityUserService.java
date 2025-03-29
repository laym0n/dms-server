package com.victor.kochnev.dmsserver.common.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface SecurityUserService extends UserDetailsService {
    UserSecurityModel getCurrentUser();

    UserSecurityModel getUserFromAuthentication(Authentication authentication);
}
