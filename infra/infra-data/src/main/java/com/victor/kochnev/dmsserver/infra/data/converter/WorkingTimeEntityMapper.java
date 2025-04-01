package com.victor.kochnev.dmsserver.infra.data.converter;

import com.victor.kochnev.dmsserver.consultation.model.WorkingTimeModel;
import com.victor.kochnev.dmsserver.infra.data.entity.WorkingTimeEntity;
import org.mapstruct.*;

@Mapper(uses = UserEntityMapper.class,
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.ERROR,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.SET_TO_NULL)
public interface WorkingTimeEntityMapper {
    @BlankEntityMapping
    @Mapping(target = "user", ignore = true)
    WorkingTimeEntity mapToEntity(WorkingTimeModel workingTimeModel);

    WorkingTimeModel mapToModel(WorkingTimeEntity workingTimeEntity);
}
