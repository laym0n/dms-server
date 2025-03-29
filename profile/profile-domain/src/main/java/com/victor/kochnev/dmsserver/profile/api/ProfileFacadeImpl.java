package com.victor.kochnev.dmsserver.profile.api;

import com.victor.kochnev.dmsserver.auth.api.UserModelFacade;
import com.victor.kochnev.dmsserver.common.security.SecurityUserService;
import com.victor.kochnev.dmsserver.profile.infra.ProfileModelRepository;
import com.victor.kochnev.dmsserver.profile.model.ProfileModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Component
public class ProfileFacadeImpl implements ProfileFacade {
    private final UserModelFacade userModelFacade;
    private final ProfileModelRepository profileModelRepository;
    private final SecurityUserService securityUserService;

    @Override
    @Transactional
    public ProfileModel signUp(ProfileModel profile) {
        var signedUpUser = userModelFacade.signUp(profile.getUser());
        profile.setUser(signedUpUser);
        return profileModelRepository.create(profile);
    }

    @Override
    public ProfileModel getAuthenticatedProfile() {
        var currentUserId = securityUserService.getCurrentUser().getId();
        return profileModelRepository.getByUserId(currentUserId);
    }
}
