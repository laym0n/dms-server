package com.victor.kochnev.dmsserver.consultation.model;


import com.victor.kochnev.dmsserver.auth.model.UserModel;
import com.victor.kochnev.dmsserver.common.model.BaseModel;
import com.victor.kochnev.dmsserver.profile.model.SpecializationModel;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.Duration;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@SuperBuilder
public class ConsultationSlotModel extends BaseModel {
    private SpecializationModel specialization;
    private Duration duration;
    private UserModel user;

}
