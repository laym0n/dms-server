package com.victor.kochnev.dmsserver.consultation.model;

import com.victor.kochnev.dmsserver.auth.model.UserModel;
import com.victor.kochnev.dmsserver.common.model.BaseModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.DayOfWeek;
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
}
