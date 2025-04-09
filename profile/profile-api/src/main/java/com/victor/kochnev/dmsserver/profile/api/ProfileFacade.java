package com.victor.kochnev.dmsserver.profile.api;

import com.victor.kochnev.dmsserver.common.dto.ModelsRequestDto;
import com.victor.kochnev.dmsserver.common.dto.ModelsResponseDto;
import com.victor.kochnev.dmsserver.profile.dto.ProfileFiltersDto;
import com.victor.kochnev.dmsserver.profile.model.ProfileModel;

import java.util.UUID;

public interface ProfileFacade {
    ProfileModel signUp(ProfileModel profile);

    ProfileModel getAuthenticatedProfile();

    ModelsResponseDto<? extends ProfileModel> search(ModelsRequestDto<ProfileFiltersDto> requestDto);

    ProfileModel getInfoByUserId(UUID userId);
}
