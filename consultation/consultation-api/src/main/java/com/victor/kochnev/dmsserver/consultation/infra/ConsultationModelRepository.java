package com.victor.kochnev.dmsserver.consultation.infra;

import com.victor.kochnev.dmsserver.common.exception.ResourceNotFoundException;
import com.victor.kochnev.dmsserver.consultation.model.ConsultationModel;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ConsultationModelRepository {
    ConsultationModel create(ConsultationModel consultation);

    List<ConsultationModel> findByPatientIdOrDoctorId(UUID patientId, UUID doctorId);

    Optional<ConsultationModel> findByDoctorIdAndStartDateTime(UUID doctorId, ZonedDateTime startDateTime);

    List<ConsultationModel> findAllByFilters(ConsultationFiltersDto consultationFilters);

    Optional<ConsultationModel> findById(UUID consultationId);

    default ConsultationModel getById(UUID consultationId) {
        return findById(consultationId).orElseThrow(() -> new ResourceNotFoundException(ConsultationModel.class, "id", consultationId.toString()));
    }
}
