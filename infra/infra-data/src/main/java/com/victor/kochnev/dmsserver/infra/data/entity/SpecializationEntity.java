package com.victor.kochnev.dmsserver.infra.data.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "specialization")
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class SpecializationEntity extends BaseEntity {
    @Column(nullable = false, unique = true, length = 50, name = "name")
    private String name;
}
