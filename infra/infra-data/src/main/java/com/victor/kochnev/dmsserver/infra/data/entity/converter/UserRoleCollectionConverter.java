package com.victor.kochnev.dmsserver.infra.data.entity.converter;

import com.victor.kochnev.dmsserver.auth.model.UserRole;

public class UserRoleCollectionConverter extends CollectionConverter<UserRole> {
    @Override
    protected Class<UserRole> getInnerClass() {
        return UserRole.class;
    }
}
