package com.victor.kochnev.dmsserver.infra.data.repository;

import com.victor.kochnev.dmsserver.infra.data.entity.SpecializationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SpecializationRepository extends JpaRepository<SpecializationEntity, UUID> {
}
