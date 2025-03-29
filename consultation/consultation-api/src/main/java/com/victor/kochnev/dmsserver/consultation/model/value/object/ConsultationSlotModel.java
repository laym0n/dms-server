package com.victor.kochnev.dmsserver.consultation.model.value.object;


import com.victor.kochnev.dmsserver.profile.model.SpecializationModel;

import java.time.Duration;

public record ConsultationSlotModel(SpecializationModel specialization, Duration duration) {
}
