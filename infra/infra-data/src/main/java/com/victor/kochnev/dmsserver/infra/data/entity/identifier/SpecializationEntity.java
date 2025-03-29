package com.victor.kochnev.dmsserver.infra.data.entity.identifier;

import com.victor.kochnev.dmsserver.infra.data.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "specialization")
public class SpecializationEntity extends BaseEntity {
    @Column(nullable = false, unique = true, length = 50, name = "name")
    private String name;
}
