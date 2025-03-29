package com.victor.kochnev.dmsserver.infra.data.converter;

import com.victor.kochnev.dmsserver.infra.data.entity.ProfileEntity;
import com.victor.kochnev.dmsserver.profile.model.ProfileModel;
import org.mapstruct.*;

@Mapper(uses = UserEntityMapper.class,
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.ERROR,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.SET_TO_NULL)
public interface ProfileMapper {
    @BlankEntityMapping
    @Mapping(target = "city", ignore = true)
    @Mapping(target = "user", ignore = true)
    ProfileEntity mapToEntity(ProfileModel profileModel);

    ProfileModel mapToModel(ProfileEntity profile);
}
