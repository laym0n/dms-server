package com.victor.kochnev.dmsserver.infra.data.entity;

import com.victor.kochnev.dmsserver.infra.data.entity.converter.EducationCollectionConverter;
import com.victor.kochnev.dmsserver.infra.data.entity.converter.WorkExperienceCollectionConverter;
import com.victor.kochnev.dmsserver.infra.data.entity.identifier.SpecializationEntity;
import com.victor.kochnev.dmsserver.profile.model.value.object.EducationModel;
import com.victor.kochnev.dmsserver.profile.model.value.object.WorkExperienceModel;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Collection;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class DoctorProfileEntity extends ProfileEntity {

    @Column(name = "work_experiences")
    @Convert(converter = WorkExperienceCollectionConverter.class)
    private Collection<WorkExperienceModel> workExperiences;
    @Column(name = "educations")
    @Convert(converter = EducationCollectionConverter.class)
    private Collection<EducationModel> educations;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "doctor_profile_id", nullable = false)
    private Collection<SpecializationEntity> specializations;
}