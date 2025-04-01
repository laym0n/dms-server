package com.victor.kochnev.dmsserver.consultation.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConsultationSlotsFilterDto {
    private String specializationName;
    private ZonedDateTime consultationStartDateTime;
}
