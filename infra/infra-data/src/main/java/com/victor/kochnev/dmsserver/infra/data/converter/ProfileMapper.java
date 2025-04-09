package com.victor.kochnev.dmsserver.infra.data.converter;

import com.victor.kochnev.dmsserver.infra.data.entity.ProfileEntity;
import com.victor.kochnev.dmsserver.profile.model.DoctorProfileModel;
import com.victor.kochnev.dmsserver.profile.model.ProfileModel;
import org.mapstruct.*;

@Mapper(uses = {UserEntityMapper.class, SpecializationEntityMapper.class, WorkExperienceEntityMapper.class, EducationEntityMapper.class},
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.ERROR,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.SET_TO_NULL)
public abstract class ProfileMapper {

    public ProfileEntity mapToEntity(ProfileModel profileModel) {
        if (profileModel instanceof DoctorProfileModel doctorProfileModel) {
            return mapToEntityInternal(doctorProfileModel);
        } else {
            return mapToEntityInternal(profileModel);
        }
    }

    @BlankEntityMapping
    @Mapping(target = "city", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "specializations", ignore = true)
    @Mapping(target = "workExperiences", ignore = true)
    @Mapping(target = "educations", ignore = true)
    @Mapping(target = "profileType", constant = "PATIENT")
    abstract ProfileEntity mapToEntityInternal(ProfileModel profileModel);

    @BlankEntityMapping
    @Mapping(target = "city", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "specializations", ignore = true)
    @Mapping(target = "profileType", constant = "DOCTOR")
    abstract ProfileEntity mapToEntityInternal(DoctorProfileModel doctorProfileModel);

    public ProfileModel mapToModel(ProfileEntity profile) {
        if (ProfileEntity.ProfileType.PATIENT.equals(profile.getProfileType())) {
            return mapToProfileModelInternal(profile);
        } else {
            return mapToDoctorProfileModelInternal(profile);
        }
    }

    abstract ProfileModel mapToProfileModelInternal(ProfileEntity profile);

    abstract DoctorProfileModel mapToDoctorProfileModelInternal(ProfileEntity profile);
}
