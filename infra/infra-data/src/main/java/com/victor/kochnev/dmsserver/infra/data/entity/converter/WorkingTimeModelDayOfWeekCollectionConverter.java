package com.victor.kochnev.dmsserver.infra.data.entity.converter;

import com.victor.kochnev.dmsserver.consultation.model.WorkingTimeModel;

public class WorkingTimeModelDayOfWeekCollectionConverter extends CollectionConverter<WorkingTimeModel.DayOfWeek> {
    @Override
    protected Class<WorkingTimeModel.DayOfWeek> getInnerClass() {
        return WorkingTimeModel.DayOfWeek.class;
    }
}
