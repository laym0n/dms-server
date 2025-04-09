package com.victor.kochnev.dmsserver.infra.data.repository;

import com.victor.kochnev.dmsserver.infra.data.entity.ProfileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProfileRepository extends JpaRepository<ProfileEntity, UUID>, JpaSpecificationExecutor<ProfileEntity> {
    Optional<ProfileEntity> findByUserId(UUID userId);
}
