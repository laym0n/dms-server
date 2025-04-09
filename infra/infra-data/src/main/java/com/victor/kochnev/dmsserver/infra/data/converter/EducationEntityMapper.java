package com.victor.kochnev.dmsserver.infra.data.converter;

import com.victor.kochnev.dmsserver.infra.data.entity.EducationEntity;
import com.victor.kochnev.dmsserver.profile.model.EducationModel;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(uses = SpecializationEntityMapper.class,
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.ERROR,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.SET_TO_NULL)
public interface EducationEntityMapper {
    @BlankEntityMapping
    EducationEntity mapToEntity(EducationModel educationModel);

    EducationModel mapToModel(EducationEntity educationEntity);
}
