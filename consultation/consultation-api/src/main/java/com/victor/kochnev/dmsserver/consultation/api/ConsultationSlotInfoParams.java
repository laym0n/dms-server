package com.victor.kochnev.dmsserver.consultation.api;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ConsultationSlotInfoParams {
    private LocalDate appointmentStartDate;
    private LocalDate appointmentEndDate;
}
