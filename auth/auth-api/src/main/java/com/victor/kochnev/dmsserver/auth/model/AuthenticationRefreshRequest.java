package com.victor.kochnev.dmsserver.auth.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationRefreshRequest {
    @JsonProperty("refreshToken")
    @NotNull
    private String refreshToken;
    @JsonProperty("rememberMe")
    private Boolean rememberMe;
}

