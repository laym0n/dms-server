package com.victor.kochnev.dmsserver.consultation.api;

import com.victor.kochnev.dmsserver.consultation.model.DoctorScheduleModel;

import java.util.UUID;

public interface DoctorScheduleFacade {
    DoctorScheduleModel getDoctorSchedule(UUID userId);
}
