package com.victor.kochnev.dmsserver.infra.zoom.client;

import com.victor.kochnev.dmsserver.consultation.infra.MeetingClient;
import com.victor.kochnev.dmsserver.consultation.model.ConsultationModel;
import com.victor.kochnev.dmsserver.consultation.model.MeetingData;
import com.victor.kochnev.dmsserver.infra.zoom.api.auth.DefaultClient;
import com.victor.kochnev.dmsserver.infra.zoom.api.auth.invoker.ApiClient;
import com.victor.kochnev.dmsserver.infra.zoom.api.common.MeetingsClient;
import com.victor.kochnev.dmsserver.infra.zoom.api.common.dto.MeetingCreateRequestDTO;
import com.victor.kochnev.dmsserver.infra.zoom.api.common.dto.MeetingCreateRequestSettingsDTO;
import com.victor.kochnev.dmsserver.infra.zoom.api.common.dto.MeetingCreateRequestSettingsMeetingInviteesInnerDTO;
import com.victor.kochnev.dmsserver.infra.zoom.config.ZoomClientProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ZoomClient implements MeetingClient {
    private final ZoomClientProperties properties;


    @Override
    public MeetingData createMeeting(ConsultationModel consultationModel) {
        var apiClient = new ApiClient();
        apiClient.setUsername(properties.getClientId());
        apiClient.setPassword(properties.getClientSecret());
        apiClient.setBasePath("https://zoom.us");
        var authClient = new DefaultClient(apiClient);
        var authResponse = authClient.oauthTokenPost("account_credentials", properties.getAccountId())
                .block();
        var commonApiClient = new com.victor.kochnev.dmsserver.infra.zoom.api.common.invoker.ApiClient();
        commonApiClient.setApiKey("Bearer " + authResponse.getAccessToken());
        commonApiClient.setBasePath(properties.getBaseUrl());
        var meetingsClient = new MeetingsClient(commonApiClient);
        var meetingCreateRequestDTO = new MeetingCreateRequestDTO();
        meetingCreateRequestDTO.setTopic("Видеоконсультация");
        meetingCreateRequestDTO.setType(MeetingCreateRequestDTO.TypeEnum.NUMBER_2);
        meetingCreateRequestDTO.setStartTime(consultationModel.getStartDateTime().toOffsetDateTime());
        meetingCreateRequestDTO.setDuration((int) consultationModel.getConsultationSlot().getDuration().toMinutes());
        meetingCreateRequestDTO.setTimezone("Europe/Moscow");
        meetingCreateRequestDTO.setPassword(UUID.randomUUID().toString().substring(0, 10));
        meetingCreateRequestDTO.setSettings(
                new MeetingCreateRequestSettingsDTO()
                        .hostVideo(true)
                        .participantVideo(true)
                        .joinBeforeHost(true)
                        .muteUponEntry(true)
                        .watermark(true)
                        .usePmi(false)
                        .approvalType(MeetingCreateRequestSettingsDTO.ApprovalTypeEnum.NUMBER_0)
                        .audio(MeetingCreateRequestSettingsDTO.AudioEnum.BOTH)
                        .autoRecording(MeetingCreateRequestSettingsDTO.AutoRecordingEnum.NONE)
                        .registrantsConfirmationEmail(true)
                        .meetingInvitees(List.of(
                                new MeetingCreateRequestSettingsMeetingInviteesInnerDTO()
                                        .email(consultationModel.getPatient().getEmail())
                        ))
        );
        var createMeetingResponse = meetingsClient.meetingCreate(consultationModel.getDoctor().getEmail(), meetingCreateRequestDTO)
                .block();
        var meetingData = new MeetingData();
        meetingData.setJoinUrl(createMeetingResponse.getJoinUrl());
        meetingData.setStartUrl(createMeetingResponse.getStartUrl());
        return meetingData;
    }
}
