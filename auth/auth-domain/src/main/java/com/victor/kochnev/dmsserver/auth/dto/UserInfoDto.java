package com.victor.kochnev.dmsserver.auth.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.victor.kochnev.dmsserver.auth.model.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoDto {
    @JsonProperty("id")
    @NotNull
    private UUID id;
    @JsonProperty("login")
    @Email
    @NotNull
    private String login;
    @JsonProperty("roles")
    @NotNull
    private List<UserRole> roles;
}

