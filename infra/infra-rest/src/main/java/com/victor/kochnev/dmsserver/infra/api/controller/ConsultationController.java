package com.victor.kochnev.dmsserver.infra.api.controller;

import com.victor.kochnev.dmsserver.common.dto.ModelsResponseDto;
import com.victor.kochnev.dmsserver.consultation.api.ConsultationFacade;
import com.victor.kochnev.dmsserver.consultation.model.ConsultationModel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class ConsultationController {
    private final ConsultationFacade consultationFacade;

    @GetMapping("/consultation")
    public ResponseEntity<ModelsResponseDto<ConsultationModel>> getCurrentUserConsultations() {
        var consultationsResponse = consultationFacade.getCurrentUserConsultations();
        return ResponseEntity.ok(consultationsResponse);
    }

    @GetMapping("/consultation/{consultationId}")
    public ResponseEntity<ConsultationModel> getConsultationInfo(@PathVariable(name = "consultationId") UUID consultationId) {
        var consultationsResponse = consultationFacade.getInfoById(consultationId);
        return ResponseEntity.ok(consultationsResponse);
    }

    @PostMapping("/consultation/{consultationId}/complete")
    public ResponseEntity<ConsultationModel> completeConsultation(@PathVariable(name = "consultationId") UUID consultationId) {
        var consultationsResponse = consultationFacade.complete(consultationId);
        return ResponseEntity.ok(consultationsResponse);
    }

    @PostMapping("/consultation")
    public ResponseEntity<ConsultationModel> create(@RequestBody ConsultationModel consultationModel) {
        var createConsultationModel = consultationFacade.create(consultationModel);
        return ResponseEntity.ok(createConsultationModel);
    }
}
