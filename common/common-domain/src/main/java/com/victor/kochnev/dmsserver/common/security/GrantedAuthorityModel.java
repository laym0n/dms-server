package com.victor.kochnev.dmsserver.common.security;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GrantedAuthorityModel implements GrantedAuthority {
    private String authority;
}
