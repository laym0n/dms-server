package com.victor.kochnev.dmsserver.profile.model.value.object;

import com.victor.kochnev.dmsserver.profile.model.SpecializationModel;

import java.time.LocalDate;
import java.util.Collection;

public record EducationModel(String studyplace, Collection<SpecializationModel> specializations, LocalDate startDate,
                             LocalDate endDate) {
}
