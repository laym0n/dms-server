package com.victor.kochnev.dmsserver.profile.model;

import com.victor.kochnev.dmsserver.profile.model.value.object.EducationModel;
import com.victor.kochnev.dmsserver.profile.model.value.object.WorkExperienceModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.Collection;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class DoctorProfileModel extends ProfileModel {
    private Collection<WorkExperienceModel> workExperiences;
    private Collection<EducationModel> educations;
    private Collection<SpecializationModel> specializations;
}
