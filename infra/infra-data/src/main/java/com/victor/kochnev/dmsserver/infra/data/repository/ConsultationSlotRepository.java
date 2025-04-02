package com.victor.kochnev.dmsserver.infra.data.repository;

import com.victor.kochnev.dmsserver.infra.data.entity.ConsultationSlotEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.UUID;

public interface ConsultationSlotRepository extends JpaRepository<ConsultationSlotEntity, UUID>, JpaSpecificationExecutor<ConsultationSlotEntity> {
}
