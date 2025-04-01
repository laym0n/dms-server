package com.victor.kochnev.dmsserver.infra.data.converter;

import com.victor.kochnev.dmsserver.consultation.model.ConsultationSlotModel;
import com.victor.kochnev.dmsserver.infra.data.entity.ConsultationSlotEntity;
import org.mapstruct.*;

@Mapper(uses = {UserEntityMapper.class, SpecializationEntityMapper.class},
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.ERROR,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.SET_TO_NULL)
public interface ConsultationSlotEntityMapper {
    @BlankEntityMapping
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "specialization", ignore = true)
    ConsultationSlotEntity mapToEntity(ConsultationSlotModel consultationSlotModel);

    ConsultationSlotModel mapToModel(ConsultationSlotEntity consultationSlotEntity);
}
