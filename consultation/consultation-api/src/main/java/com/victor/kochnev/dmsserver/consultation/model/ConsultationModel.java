package com.victor.kochnev.dmsserver.consultation.model;

import com.victor.kochnev.dmsserver.auth.model.UserModel;
import com.victor.kochnev.dmsserver.common.model.BaseModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.ZonedDateTime;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class ConsultationModel extends BaseModel {
    private UserModel patient;
    private UserModel doctor;
    private ZonedDateTime startDateTime;
    private ConsultationSlotModel consultationSlot;
}
