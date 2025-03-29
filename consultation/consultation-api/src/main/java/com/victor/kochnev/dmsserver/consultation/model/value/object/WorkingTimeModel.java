package com.victor.kochnev.dmsserver.consultation.model.value.object;

import java.time.LocalTime;
import java.util.Collection;

public record WorkingTimeModel(Collection<DayOfWeek> days, LocalTime startTime, LocalTime endTime) {
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
