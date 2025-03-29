package com.victor.kochnev.dmsserver.common.security;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Getter
public class UserSecurityModel extends User {
    private UUID id;

    @Default
    public UserSecurityModel(UUID id, String login, String password, boolean enabled, Collection<? extends GrantedAuthority> authorities) {
        super(login, password, enabled, true, true, true, authorities);
        this.id = id;
    }

    public UserSecurityModel(UUID id, String username) {
        super(username, "", List.of());
        this.id = id;
    }
}
