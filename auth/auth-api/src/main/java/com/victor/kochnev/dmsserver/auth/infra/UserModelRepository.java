package com.victor.kochnev.dmsserver.auth.infra;

import com.victor.kochnev.dmsserver.auth.model.UserModel;
import com.victor.kochnev.dmsserver.common.exception.ResourceNotFoundException;

import java.util.Optional;

public interface UserModelRepository {
    UserModel create(UserModel user);

    default UserModel getByLogin(String login) {
        return findByLogin(login)
                .orElseThrow(() -> new ResourceNotFoundException(UserModel.class, "login", login));
    }

    Optional<UserModel> findByLogin(String login);
}
