package com.victor.kochnev.dmsserver.consultation.infra;

import com.victor.kochnev.dmsserver.consultation.model.ConsultationModel;
import com.victor.kochnev.dmsserver.consultation.model.MeetingData;

public interface MeetingClient {
    MeetingData createMeeting(ConsultationModel consultationModel);
}
