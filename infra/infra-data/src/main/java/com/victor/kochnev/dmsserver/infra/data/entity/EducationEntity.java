package com.victor.kochnev.dmsserver.infra.data.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.Collection;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "doctor_education")
@SuperBuilder
public class EducationEntity extends BaseEntity {

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name = "doctor_education_specialization",
            joinColumns = @JoinColumn(name = "doctor_education_id"),
            inverseJoinColumns = @JoinColumn(name = "specialization_id")
    )
    private Collection<SpecializationEntity> specializations;

    @Column(name = "studyplace", nullable = false)
    private String studyplace;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;
}