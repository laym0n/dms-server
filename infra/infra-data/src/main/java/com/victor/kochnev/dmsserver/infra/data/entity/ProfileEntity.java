package com.victor.kochnev.dmsserver.infra.data.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.Collection;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "profile")
@SuperBuilder
public class ProfileEntity extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @Column(name = "city")
    private String city;

    @Column(name = "name", nullable = false)
    private String name;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "profile_id", nullable = false)
    private Collection<WorkExperienceEntity> workExperiences;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "profile_id", nullable = false)
    private Collection<EducationEntity> educations;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name = "doctor_profile_specialization",
            joinColumns = @JoinColumn(name = "doctor_profile_id"),
            inverseJoinColumns = @JoinColumn(name = "specialization_id")
    )
    private Collection<SpecializationEntity> specializations;

    @Column(name = "profile_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private ProfileType profileType;

    public enum ProfileType {
        PATIENT,
        DOCTOR,
        MODERATOR;
    }
}