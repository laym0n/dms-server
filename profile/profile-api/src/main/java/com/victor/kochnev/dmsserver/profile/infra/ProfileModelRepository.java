package com.victor.kochnev.dmsserver.profile.infra;

import com.victor.kochnev.dmsserver.common.exception.ResourceNotFoundException;
import com.victor.kochnev.dmsserver.profile.dto.ProfileFiltersDto;
import com.victor.kochnev.dmsserver.profile.model.ProfileModel;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProfileModelRepository {
    ProfileModel create(ProfileModel profile);

    ProfileModel getByUserId(UUID userId);

    List<? extends ProfileModel> findAllByFilters(ProfileFiltersDto filters);

    default ProfileModel getById(UUID id) {
        return findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ProfileModel.class, "id", id.toString()));
    }

    Optional<ProfileModel> findById(UUID id);
}
