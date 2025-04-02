package com.victor.kochnev.dmsserver.profile.api;

import com.victor.kochnev.dmsserver.common.dto.ModelsResponseDto;
import com.victor.kochnev.dmsserver.profile.model.SpecializationModel;

public interface SpecializationFacade {
    ModelsResponseDto<SpecializationModel> findAll();
}
