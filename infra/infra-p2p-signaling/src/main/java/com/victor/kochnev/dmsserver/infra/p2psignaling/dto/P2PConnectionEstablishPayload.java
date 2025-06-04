package com.victor.kochnev.dmsserver.infra.p2psignaling.dto;

import com.victor.kochnev.dmsserver.profile.model.ProfileModel;
import lombok.Data;

import java.util.Map;
import java.util.UUID;

@Data
public class P2PConnectionEstablishPayload {
    private String type;
    private String sourceSessionId;
    private ProfileModel sourceProfile;
    private String destinationSessionId;
    private UUID destinationUserId;
    private UUID consultationId;
    private Map<String, Object> offer;
    private Map<String, Object> answer;
    private Map<String, Object> candidate;
}
