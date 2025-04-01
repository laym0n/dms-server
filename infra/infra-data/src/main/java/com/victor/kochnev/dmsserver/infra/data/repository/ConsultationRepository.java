package com.victor.kochnev.dmsserver.infra.data.repository;

import com.victor.kochnev.dmsserver.infra.data.entity.ConsultationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ConsultationRepository extends JpaRepository<ConsultationEntity, UUID> {
    List<ConsultationEntity> findByPatientIdOrDoctorId(UUID patientId, UUID doctorId);

    Optional<ConsultationEntity> findByDoctorIdAndStartDateTime(UUID doctorId, ZonedDateTime startDateTime);
}
