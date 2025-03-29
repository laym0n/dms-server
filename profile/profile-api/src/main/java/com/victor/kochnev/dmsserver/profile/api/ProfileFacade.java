package com.victor.kochnev.dmsserver.profile.api;

import com.victor.kochnev.dmsserver.profile.model.ProfileModel;

public interface ProfileFacade {
    ProfileModel signUp(ProfileModel profile);

    ProfileModel getAuthenticatedProfile();
}
