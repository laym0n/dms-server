package com.victor.kochnev.dmsserver.consultation.model;

import com.victor.kochnev.dmsserver.common.model.BaseModel;
import com.victor.kochnev.dmsserver.consultation.model.value.object.ConsultationSlotModel;
import com.victor.kochnev.dmsserver.consultation.model.value.object.WorkingTimeModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Collection;

@Getter
@Setter
@NoArgsConstructor
public class DoctorScheduleModel extends BaseModel {
    private Collection<ConsultationSlotModel> receptions;
    private Collection<WorkingTimeModel> workingTimes;
}
