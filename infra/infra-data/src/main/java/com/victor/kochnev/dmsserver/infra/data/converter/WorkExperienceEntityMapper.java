package com.victor.kochnev.dmsserver.infra.data.converter;

import com.victor.kochnev.dmsserver.infra.data.entity.WorkExperienceEntity;
import com.victor.kochnev.dmsserver.profile.model.WorkExperienceModel;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(uses = SpecializationEntityMapper.class,
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.ERROR,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.SET_TO_NULL)
public interface WorkExperienceEntityMapper {
    @BlankEntityMapping
    WorkExperienceEntity mapToEntity(WorkExperienceModel workExperienceModel);

    WorkExperienceModel mapToModel(WorkExperienceEntity workExperienceEntity);
}
