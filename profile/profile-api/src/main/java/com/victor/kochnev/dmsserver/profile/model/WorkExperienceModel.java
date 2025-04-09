package com.victor.kochnev.dmsserver.profile.model;

import com.victor.kochnev.dmsserver.common.model.BaseModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class WorkExperienceModel extends BaseModel {
    private String workplace;
    private SpecializationModel specialization;
    private LocalDate startDate;
    private LocalDate endDate;
}
