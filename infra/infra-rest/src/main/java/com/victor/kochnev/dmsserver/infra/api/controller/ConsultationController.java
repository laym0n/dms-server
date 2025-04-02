package com.victor.kochnev.dmsserver.infra.api.controller;

import com.victor.kochnev.dmsserver.common.dto.ModelsRequestDto;
import com.victor.kochnev.dmsserver.consultation.api.ConsultationFacade;
import com.victor.kochnev.dmsserver.consultation.api.ConsultationSlotsFilterDto;
import com.victor.kochnev.dmsserver.common.dto.ModelsResponseDto;
import com.victor.kochnev.dmsserver.consultation.model.ConsultationModel;
import com.victor.kochnev.dmsserver.consultation.model.ConsultationSlotModel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ConsultationController {
    private final ConsultationFacade consultationFacade;

    @GetMapping("/consultation")
    public ResponseEntity<ModelsResponseDto<ConsultationModel>> getCurrentUserConsultations() {
        var consultationsResponse = consultationFacade.getCurrentUserConsultations();
        return ResponseEntity.ok(consultationsResponse);
    }

    @PostMapping("/consultation")
    public ResponseEntity<ConsultationModel> create(@RequestBody ConsultationModel consultationModel) {
        var createConsultationModel = consultationFacade.create(consultationModel);
        return ResponseEntity.ok(createConsultationModel);
    }

    @PostMapping("/consultation/search")
    public ResponseEntity<ModelsResponseDto<ConsultationSlotModel>> findConsultations(@RequestBody ModelsRequestDto<ConsultationSlotsFilterDto> requestDto) {
        var consultationsResponse = consultationFacade.findConsultationSlots(requestDto);
        return ResponseEntity.ok(consultationsResponse);
    }
}
