package com.victor.kochnev.dmsserver.consultation.api;

import com.victor.kochnev.dmsserver.common.dto.ModelsRequestDto;
import com.victor.kochnev.dmsserver.common.dto.ModelsResponseDto;
import com.victor.kochnev.dmsserver.consultation.model.ConsultationModel;
import com.victor.kochnev.dmsserver.consultation.model.ConsultationSlotModel;

public interface ConsultationFacade {
    ConsultationModel create(ConsultationModel consultation);

    ModelsResponseDto<ConsultationSlotModel> findConsultationSlots(ModelsRequestDto<ConsultationSlotsFilterDto> requestDto);

    ModelsResponseDto<ConsultationModel> getCurrentUserConsultations();
}
