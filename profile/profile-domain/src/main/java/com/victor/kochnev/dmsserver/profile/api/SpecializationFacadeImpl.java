package com.victor.kochnev.dmsserver.profile.api;

import com.victor.kochnev.dmsserver.common.dto.ModelsResponseDto;
import com.victor.kochnev.dmsserver.profile.infra.SpecializationModelRepository;
import com.victor.kochnev.dmsserver.profile.model.SpecializationModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SpecializationFacadeImpl implements SpecializationFacade {
    private final SpecializationModelRepository specializationModelRepository;

    @Override
    public ModelsResponseDto<SpecializationModel> findAll() {
        var specializations = specializationModelRepository.findAll();
        return new ModelsResponseDto<>(specializations);
    }
}
