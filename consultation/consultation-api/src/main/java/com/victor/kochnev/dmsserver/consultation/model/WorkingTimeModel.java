package com.victor.kochnev.dmsserver.consultation.model;

import com.victor.kochnev.dmsserver.auth.model.UserModel;
import com.victor.kochnev.dmsserver.common.model.BaseModel;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Collection;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class WorkingTimeModel extends BaseModel {
    private Collection<DayOfWeek> days;
    private LocalTime startTime;
    private LocalTime endTime;
    private ZoneId zoneId;
    private UserModel user;

    public enum DayOfWeek {
        MONDAY,
        TUESDAY,
        WEDNESDAY,
        THURSDAY,
        FRIDAY,
        SATURDAY,
        SUNDAY;
    }
}
