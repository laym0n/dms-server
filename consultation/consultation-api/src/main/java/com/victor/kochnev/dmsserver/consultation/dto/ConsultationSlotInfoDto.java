package com.victor.kochnev.dmsserver.consultation.dto;

import com.victor.kochnev.dmsserver.consultation.model.ConsultationSlotModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.ZonedDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ConsultationSlotInfoDto {
    private ConsultationSlotModel consultationSlotModel;
    private List<ZonedDateTime> appointmentDateTimes;

}
