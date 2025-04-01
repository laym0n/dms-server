package com.victor.kochnev.dmsserver.infra.data.repository;

import com.victor.kochnev.dmsserver.infra.data.entity.WorkingTimeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface WorkingTimeRepository extends JpaRepository<WorkingTimeEntity, UUID> {
    List<WorkingTimeEntity> findAllByUserId(UUID userId);
}
