package com.victor.kochnev.dmsserver.auth.api;

import com.victor.kochnev.dmsserver.auth.model.UserModel;

public interface UserModelFacade {
    UserModel signUp(UserModel user);

}
