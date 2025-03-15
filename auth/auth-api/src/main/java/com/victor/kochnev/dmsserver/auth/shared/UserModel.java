package com.victor.kochnev.dmsserver.auth.shared;

import com.victor.kochnev.dmsserver.common.model.BaseModel;
import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.util.Set;

@Data
@SuperBuilder
public class UserModel extends BaseModel {
    private String login;
    private String password;
    private Set<String> roles;
}
