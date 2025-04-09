package com.victor.kochnev.dmsserver.profile.model;

import com.victor.kochnev.dmsserver.common.model.BaseModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.Collection;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class EducationModel extends BaseModel {
    private String studyplace;
    private Collection<SpecializationModel> specializations;
    private LocalDate startDate;
    private LocalDate endDate;
}
