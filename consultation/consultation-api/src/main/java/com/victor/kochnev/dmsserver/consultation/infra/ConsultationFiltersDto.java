package com.victor.kochnev.dmsserver.consultation.infra;

import com.victor.kochnev.dmsserver.common.dto.RangeDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.ZonedDateTime;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ConsultationFiltersDto {
    private UUID doctorId;
    private RangeDto<ZonedDateTime> startDateTimeRange;
}
