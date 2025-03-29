package com.victor.kochnev.dmsserver.auth.converter;

import com.victor.kochnev.dmsserver.auth.model.UserModel;
import com.victor.kochnev.dmsserver.auth.model.UserRole;
import com.victor.kochnev.dmsserver.common.security.GrantedAuthorityModel;
import com.victor.kochnev.dmsserver.common.security.UserSecurityModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.ERROR,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface UserSecurityMapper {

    @Mapping(target = "authorities", source = "roles")
    UserSecurityModel mapToUserSecurityModel(UserModel user);

    @Mapping(target = "authority", expression = "java(\"ROLE_\" + userRole.name())")
    GrantedAuthorityModel mapToGrantedAuthority(UserRole userRole);
}
