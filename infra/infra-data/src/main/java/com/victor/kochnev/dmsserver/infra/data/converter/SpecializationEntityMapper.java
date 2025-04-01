package com.victor.kochnev.dmsserver.infra.data.converter;

import com.victor.kochnev.dmsserver.infra.data.entity.SpecializationEntity;
import com.victor.kochnev.dmsserver.profile.model.SpecializationModel;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.ERROR,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.SET_TO_NULL)
public interface SpecializationEntityMapper {
    @BlankEntityMapping
    SpecializationEntity mapToEntity(SpecializationModel specializationModel);

    SpecializationModel mapToModel(SpecializationEntity specializationEntity);
}
