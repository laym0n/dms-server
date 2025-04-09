package com.victor.kochnev.dmsserver.infra.data.entity.converter;

import java.time.DayOfWeek;

public class WorkingTimeModelDayOfWeekCollectionConverter extends CollectionConverter<DayOfWeek> {
    @Override
    protected Class<DayOfWeek> getInnerClass() {
        return DayOfWeek.class;
    }
}
