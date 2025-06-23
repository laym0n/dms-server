package com.victor.kochnev.dmsserver.infra.data.adapter;

import com.victor.kochnev.dmsserver.common.exception.ResourceNotFoundException;
import com.victor.kochnev.dmsserver.infra.data.converter.ProfileMapper;
import com.victor.kochnev.dmsserver.infra.data.entity.UserEntity;
import com.victor.kochnev.dmsserver.infra.data.repository.ProfileRepository;
import com.victor.kochnev.dmsserver.infra.data.repository.UserRepository;
import com.victor.kochnev.dmsserver.infra.data.specification.ProfileSpecification;
import com.victor.kochnev.dmsserver.profile.dto.ProfileFiltersDto;
import com.victor.kochnev.dmsserver.profile.infra.ProfileModelRepository;
import com.victor.kochnev.dmsserver.profile.model.ProfileModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ProfileModelModelRepositoryImpl implements ProfileModelRepository {
    private final UserRepository userRepository;
    private final ProfileRepository profileRepository;
    private final ProfileMapper profileMapper;

    @Override
    @Transactional
    public ProfileModel create(ProfileModel profile) {
        var userId = profile.getUser().getId();
        var userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException(UserEntity.class, "id", String.valueOf(userId)));
        var profileEntity = profileMapper.mapToEntity(profile);
        profileEntity.setUser(userEntity);
        var savedProfile = profileRepository.save(profileEntity);
        return profileMapper.mapToModel(savedProfile);
    }

    @Override
    @Transactional
    public ProfileModel getByUserId(UUID userId) {
        return profileRepository.findByUserId(userId)
                .map(profileMapper::mapToModel)
                .orElseThrow(() -> new ResourceNotFoundException(ProfileModel.class, "userId", String.valueOf(userId)));
    }

    @Override
    @Transactional
    public List<? extends ProfileModel> findAllByFilters(ProfileFiltersDto filters) {
        var spec = ProfileSpecification.byFilters(filters);
        return profileRepository.findAll(spec)
                .stream()
                .map(profileMapper::mapToModel)
                .toList();
    }

    @Override
    @Transactional
    public Optional<ProfileModel> findById(UUID id) {
        return profileRepository.findById(id)
                .map(profileMapper::mapToModel);
    }
}
