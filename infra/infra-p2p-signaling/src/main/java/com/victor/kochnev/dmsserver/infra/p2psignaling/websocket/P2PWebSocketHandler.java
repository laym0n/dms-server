package com.victor.kochnev.dmsserver.infra.p2psignaling.websocket;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.victor.kochnev.dmsserver.infra.p2psignaling.dto.P2PConnectionEstablishPayload;
import com.victor.kochnev.dmsserver.profile.api.ProfileFacade;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class P2PWebSocketHandler extends TextWebSocketHandler {
    private final Map<UUID, WebSocketSession> sessionsByUserIds = new HashMap<>();
    private final Map<String, UUID> userIdsBySessionId = new HashMap<>();
    private final ProfileFacade profileFacade;
    private final ObjectMapper objectMapper;

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message)
            throws IOException {
        var payload = objectMapper.readValue(message.getPayload(), P2PConnectionEstablishPayload.class);

        WebSocketSession destinationSession;
        switch (payload.getType()) {
            case "offer_request":
            case "offer_upload":
            case "offer_prescription": {
                var currentUserId = userIdsBySessionId.get(session.getId());
                var authenticatedProfile = profileFacade.getInfoByUserId(currentUserId);
                payload.setSourceProfile(authenticatedProfile);
                destinationSession = getSessionByDestinationUserId(payload);
            }
            break;
            case "answer":
            case "candidate":
                destinationSession = getSessionByDestinationSessionId(payload);
                break;
            default: {
                throw new RuntimeException("Unknown message type: " + payload.getType());
            }
        }
        payload.setSourceSessionId(session.getId());
        payload.setDestinationSessionId(destinationSession.getId());
        destinationSession.sendMessage(new TextMessage(objectMapper.writeValueAsString(payload)));
    }

    @SneakyThrows
    private WebSocketSession getSessionByDestinationSessionId(P2PConnectionEstablishPayload payload) {
        var destinationSessionId = payload.getDestinationSessionId();
        var userId = userIdsBySessionId.get(destinationSessionId);
        return sessionsByUserIds.get(userId);
    }

    @SneakyThrows
    private WebSocketSession getSessionByDestinationUserId(P2PConnectionEstablishPayload payload) {
        var destinationUserId = payload.getDestinationUserId();
        return sessionsByUserIds.get(destinationUserId);
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        var userId = (UUID) session.getAttributes().get("userId");
        sessionsByUserIds.put(userId, session);
        userIdsBySessionId.put(session.getId(), userId);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        var userId = userIdsBySessionId.remove(session.getId());
        if (userId == null) {
            return;
        }
        sessionsByUserIds.remove(userId);
    }
}
