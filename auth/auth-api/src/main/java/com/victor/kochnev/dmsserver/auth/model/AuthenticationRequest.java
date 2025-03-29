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
public class AuthenticationRequest {
    @JsonProperty("login")
    @NotNull
    private String login;
    @JsonProperty("password")
    @NotNull
    private String password;
    @JsonProperty("rememberMe")
    private Boolean rememberMe;
}

