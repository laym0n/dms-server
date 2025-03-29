package com.victor.kochnev.dmsserver.infra.p2psignaling.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

import java.util.concurrent.ConcurrentHashMap;

@Controller
@RequiredArgsConstructor
public class SignalingController {
    //    private final SimpMessagingTemplate messagingTemplate;
    private final ConcurrentHashMap<String, String> userSessions = new ConcurrentHashMap<>();

//    @MessageMapping("/register")
//    public void registerUser(SignalMessage message) {
//        userSessions.put(message.getSender(), message.getReceiver());
//    }
//
//    @MessageMapping("/offer")
//    public void handleOffer(SignalMessage message) {
//        String receiverSession = userSessions.get(message.getReceiver());
//        if (receiverSession != null) {
//            messagingTemplate.convertAndSendToUser(receiverSession, "/queue/offer", message);
//        }
//    }
//
//    @MessageMapping("/answer")
//    public void handleAnswer(SignalMessage message) {
//        String receiverSession = userSessions.get(message.getReceiver());
//        if (receiverSession != null) {
//            messagingTemplate.convertAndSendToUser(receiverSession, "/queue/answer", message);
//        }
//    }
//
//    @MessageMapping("/ice-candidate")
//    public void handleIceCandidate(SignalMessage message) {
//        String receiverSession = userSessions.get(message.getReceiver());
//        if (receiverSession != null) {
//            messagingTemplate.convertAndSendToUser(receiverSession, "/queue/ice-candidate", message);
//        }
//    }
}
