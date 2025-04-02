package com.victor.kochnev.dmsserver.infra.data.specification;

import com.victor.kochnev.dmsserver.consultation.api.ConsultationSlotsFilterDto;
import com.victor.kochnev.dmsserver.infra.data.entity.ConsultationSlotEntity;
import com.victor.kochnev.dmsserver.infra.data.entity.ConsultationSlotEntity_;
import com.victor.kochnev.dmsserver.infra.data.entity.SpecializationEntity_;
import org.springframework.data.jpa.domain.Specification;

import java.time.ZonedDateTime;

public final class ConsultationSlotSpecification {

    private ConsultationSlotSpecification() {
    }

    public static Specification<ConsultationSlotEntity> byFilters(ConsultationSlotsFilterDto filterDto) {
        var filter = getAll();

        if (filterDto == null) {
            return filter;
        }

        if (filterDto.getConsultationStartDateTime() != null) {
            filter = filter.and(getByConsultationStartDateTime(filterDto.getConsultationStartDateTime()));
        }

        if (filterDto.getSpecializationName() != null) {
            filter = filter.and(bySpecializationName(filterDto.getSpecializationName()));
        }

        return filter;
    }

    private static Specification<ConsultationSlotEntity> getByConsultationStartDateTime(ZonedDateTime consultationStartDateTime) {
        return null;
    }

    private static Specification<ConsultationSlotEntity> bySpecializationName(String specializationName) {
        return (root, query, cb) -> cb.equal(root.get(ConsultationSlotEntity_.specialization).get(SpecializationEntity_.name), specializationName);
    }

    public static Specification<ConsultationSlotEntity> getAll() {
        return (root, query, cb) -> {
            if (Long.class.equals(query.getResultType())) {
                root.join(ConsultationSlotEntity_.specialization);
                root.join(ConsultationSlotEntity_.user);
            } else {
                root.fetch(ConsultationSlotEntity_.specialization);
                root.fetch(ConsultationSlotEntity_.user);
            }
            return cb.conjunction();
        };
    }
}
