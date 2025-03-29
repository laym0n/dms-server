package com.victor.kochnev.dmsserver.profile.infra;

import com.victor.kochnev.dmsserver.profile.model.ProfileModel;

import java.util.UUID;

public interface ProfileModelRepository {
    ProfileModel create(ProfileModel profile);

    ProfileModel getByUserId(UUID userId);
}
