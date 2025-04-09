package com.victor.kochnev.dmsserver.infra.data.specification;

import com.victor.kochnev.dmsserver.consultation.infra.ConsultationFiltersDto;
import com.victor.kochnev.dmsserver.infra.data.entity.ConsultationEntity;
import com.victor.kochnev.dmsserver.infra.data.entity.ConsultationEntity_;
import com.victor.kochnev.dmsserver.infra.data.entity.UserEntity_;
import org.springframework.data.jpa.domain.Specification;

import java.time.ZonedDateTime;
import java.util.UUID;

public final class ConsultationSpecification {

    private ConsultationSpecification() {
    }

    public static Specification<ConsultationEntity> byFilters(ConsultationFiltersDto filterDto) {
        var filter = getAll();

        if (filterDto == null) {
            return filter;
        }

        if (filterDto.getDoctorId() != null) {
            filter = filter.and(getByDoctorId(filterDto.getDoctorId()));
        }

        if (filterDto.getStartDateTimeRange() != null) {
            if (filterDto.getStartDateTimeRange().getStartRange() != null) {
                filter = filter.and(byStartDateAfter(filterDto.getStartDateTimeRange().getStartRange()));
            } else if (filterDto.getStartDateTimeRange().getEndRange() != null) {
                filter = filter.and(byStartDateBefore(filterDto.getStartDateTimeRange().getEndRange()));
            }
        }

        return filter;
    }

    private static Specification<ConsultationEntity> byStartDateAfter(ZonedDateTime startDateTime) {
        return (root, query, cb) -> cb.greaterThan(root.get(ConsultationEntity_.startDateTime), startDateTime);
    }

    private static Specification<ConsultationEntity> byStartDateBefore(ZonedDateTime endDateTime) {
        return (root, query, cb) -> cb.lessThan(root.get(ConsultationEntity_.startDateTime), endDateTime);
    }

    private static Specification<ConsultationEntity> getByDoctorId(UUID doctorId) {
        return (root, query, cb) -> cb.equal(root.get(ConsultationEntity_.doctor).get(UserEntity_.id), doctorId);
    }

    public static Specification<ConsultationEntity> getAll() {
        return (root, query, cb) -> {
            if (Long.class.equals(query.getResultType())) {
                root.join(ConsultationEntity_.consultationSlot);
                root.join(ConsultationEntity_.doctor);
                root.join(ConsultationEntity_.patient);
            } else {
                root.fetch(ConsultationEntity_.consultationSlot);
                root.fetch(ConsultationEntity_.doctor);
                root.fetch(ConsultationEntity_.patient);
            }
            return cb.conjunction();
        };
    }
}
