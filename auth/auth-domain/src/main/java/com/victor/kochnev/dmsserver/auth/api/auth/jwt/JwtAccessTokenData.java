package com.victor.kochnev.dmsserver.auth.api.auth.jwt;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
class JwtAccessTokenData {
    private UUID id;
    private String username;
    private boolean enabled;
    private Collection<String> authorities;
}
