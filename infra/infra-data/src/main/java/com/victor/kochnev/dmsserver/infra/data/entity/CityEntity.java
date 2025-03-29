package com.victor.kochnev.dmsserver.infra.data.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
@Table(name = "city")
public class CityEntity extends BaseEntity {
    @Column(unique = true, nullable = false)
    private String name;
}
