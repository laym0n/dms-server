package com.victor.kochnev.dmsserver.profile.model.value.object;

import com.victor.kochnev.dmsserver.profile.model.SpecializationModel;

import java.time.LocalDate;

public record WorkExperienceModel(
        String workplace,
        SpecializationModel specialization,
        LocalDate startDate,
        LocalDate endDate
) {
}
