package com.victor.kochnev.dmsserver.infra.data.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "work_experience")
@SuperBuilder
public class WorkExperienceEntity extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "specialization_id")
    private SpecializationEntity specialization;

    @Column(name = "workplace", nullable = false)
    private String workplace;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;
}