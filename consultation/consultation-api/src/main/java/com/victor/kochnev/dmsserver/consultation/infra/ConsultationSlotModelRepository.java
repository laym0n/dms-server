package com.victor.kochnev.dmsserver.consultation.infra;

import com.victor.kochnev.dmsserver.common.exception.ResourceNotFoundException;
import com.victor.kochnev.dmsserver.consultation.api.ConsultationSlotsFilterDto;
import com.victor.kochnev.dmsserver.consultation.model.ConsultationSlotModel;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ConsultationSlotModelRepository {
    Optional<ConsultationSlotModel> findById(UUID id);

    default ConsultationSlotModel getById(UUID id) {
        return findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ConsultationSlotModel.class, "id", id.toString()));
    }

    List<ConsultationSlotModel> findAllByFilters(ConsultationSlotsFilterDto filterDto);
}
