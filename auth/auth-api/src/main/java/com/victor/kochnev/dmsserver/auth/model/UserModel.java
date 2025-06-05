package com.victor.kochnev.dmsserver.auth.model;

import com.victor.kochnev.dmsserver.common.model.BaseModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.Set;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class UserModel extends BaseModel {
    private String login;
    private String email;
    private String password;
    private Set<UserRole> roles;
    private boolean enabled;
}
