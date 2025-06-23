package com.victor.kochnev.dmsserver.infra.data.specification;

import com.victor.kochnev.dmsserver.infra.data.entity.ProfileEntity;
import com.victor.kochnev.dmsserver.infra.data.entity.ProfileEntity_;
import com.victor.kochnev.dmsserver.infra.data.entity.UserEntity_;
import com.victor.kochnev.dmsserver.profile.dto.ProfileFiltersDto;
import jakarta.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.UUID;

public final class ProfileSpecification {

    private ProfileSpecification() {
    }

    public static Specification<ProfileEntity> byFilters(ProfileFiltersDto filterDto) {
        var filter = getAll();

        if (filterDto == null) {
            return filter;
        }

        if (!CollectionUtils.isEmpty(filterDto.getUserIds())) {
            filter = filter.and(getByUserIds(filterDto.getUserIds()));
        }

        return filter;
    }

    private static Specification<ProfileEntity> getByUserIds(List<UUID> userIds) {
        return (root, query, cb) -> root.get(ProfileEntity_.user).get(UserEntity_.id).in(userIds);
    }

    public static Specification<ProfileEntity> getAll() {
        return (root, query, cb) -> {
            if (Long.class.equals(query.getResultType())) {
                root.join(ProfileEntity_.specializations, JoinType.LEFT);
                root.join(ProfileEntity_.user);
            } else {
                root.fetch(ProfileEntity_.specializations, JoinType.LEFT);
                root.fetch(ProfileEntity_.user);
            }
            return cb.conjunction();
        };
    }
}
