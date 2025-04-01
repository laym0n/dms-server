package com.victor.kochnev.dmsserver.infra.data.converter;

import com.victor.kochnev.dmsserver.consultation.model.ConsultationModel;
import com.victor.kochnev.dmsserver.infra.data.entity.ConsultationEntity;
import com.victor.kochnev.dmsserver.infra.data.entity.ConsultationSlotEntity;
import org.mapstruct.*;

@Mapper(uses = {UserEntityMapper.class, ConsultationSlotEntity.class},
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.ERROR,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.SET_TO_NULL)
public interface ConsultationEntityMapper {
    @BlankEntityMapping
    @Mapping(target = "patient", ignore = true)
    @Mapping(target = "doctor", ignore = true)
    @Mapping(target = "consultationSlot", ignore = true)
    ConsultationEntity mapToEntity(ConsultationModel consultationModel);

    ConsultationModel mapToModel(ConsultationEntity profile);
}
