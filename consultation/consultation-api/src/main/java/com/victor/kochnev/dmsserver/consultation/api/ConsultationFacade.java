package com.victor.kochnev.dmsserver.consultation.api;

import com.victor.kochnev.dmsserver.common.dto.ModelsRequestDto;
import com.victor.kochnev.dmsserver.common.dto.ModelsResponseDto;
import com.victor.kochnev.dmsserver.consultation.dto.BlockchainRecord;
import com.victor.kochnev.dmsserver.consultation.dto.ConsultationPrescriptionDto;
import com.victor.kochnev.dmsserver.consultation.dto.ConsultationSlotInfoDto;
import com.victor.kochnev.dmsserver.consultation.model.ConsultationModel;
import com.victor.kochnev.dmsserver.consultation.model.ConsultationSlotModel;

import java.util.UUID;

public interface ConsultationFacade {
    ConsultationModel create(ConsultationModel consultation);

    BlockchainRecord updatePrescription(UUID consultationId, ConsultationPrescriptionDto prescriptionDto);

    ModelsResponseDto<ConsultationSlotModel> findConsultationSlots(ModelsRequestDto<ConsultationSlotsFilterDto> requestDto);

    ModelsResponseDto<ConsultationModel> getCurrentUserConsultations();

    ConsultationSlotInfoDto getConsultationSlotInfo(UUID id, ConsultationSlotInfoParams params);
}
