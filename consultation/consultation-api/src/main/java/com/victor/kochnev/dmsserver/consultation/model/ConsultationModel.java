package com.victor.kochnev.dmsserver.consultation.model;

import com.victor.kochnev.dmsserver.auth.model.UserModel;
import com.victor.kochnev.dmsserver.common.model.BaseModel;
import com.victor.kochnev.dmsserver.consultation.model.value.object.ConsultationSlotModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.ZonedDateTime;

@Getter
@Setter
@NoArgsConstructor
public class ConsultationModel extends BaseModel {
    private UserModel patient;
    private UserModel doctor;
    private ZonedDateTime startDateTime;
    private ConsultationSlotModel consultationSlot;
    private String consultationResult;
}
