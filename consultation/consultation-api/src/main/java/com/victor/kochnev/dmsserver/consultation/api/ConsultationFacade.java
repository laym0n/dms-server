package com.victor.kochnev.dmsserver.consultation.api;

import com.victor.kochnev.dmsserver.consultation.model.ConsultationModel;
import com.victor.kochnev.dmsserver.profile.model.DoctorProfileModel;

import java.util.List;

public interface ConsultationFacade {
    ConsultationModel create(ConsultationModel consultation);
    List<DoctorProfileModel> findConsultationSlots(ConsultationSlotsFilterDto filterDto);
    List<ConsultationModel> getCurrentUserConsultations();
}
