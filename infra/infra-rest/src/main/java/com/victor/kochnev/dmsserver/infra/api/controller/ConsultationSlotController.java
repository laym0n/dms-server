package com.victor.kochnev.dmsserver.infra.api.controller;

import com.victor.kochnev.dmsserver.common.dto.ModelsRequestDto;
import com.victor.kochnev.dmsserver.common.dto.ModelsResponseDto;
import com.victor.kochnev.dmsserver.consultation.api.ConsultationFacade;
import com.victor.kochnev.dmsserver.consultation.api.ConsultationSlotInfoParams;
import com.victor.kochnev.dmsserver.consultation.api.ConsultationSlotsFilterDto;
import com.victor.kochnev.dmsserver.consultation.dto.ConsultationSlotInfoDto;
import com.victor.kochnev.dmsserver.consultation.model.ConsultationSlotModel;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class ConsultationSlotController {
    private final ConsultationFacade consultationFacade;

    @GetMapping("/consultationslot/{id}")
    @Operation(operationId = "getConsultationSlotInfo", summary = "Получить информацию о слоте консультации")
    public ResponseEntity<ConsultationSlotInfoDto> get(@PathVariable UUID id, @RequestParam(name = "appointmentStartDateTime") LocalDate appointmentStartDateTime, @RequestParam(name = "appointmentEndDateTime") LocalDate appointmentEndDateTime) {
        var params = new ConsultationSlotInfoParams();
        params.setAppointmentStartDate(appointmentStartDateTime);
        params.setAppointmentEndDate(appointmentEndDateTime);
        var consultationSlotInfo = consultationFacade.getConsultationSlotInfo(id, params);
        return ResponseEntity.ok(consultationSlotInfo);
    }

    @PostMapping("/consultationslot/search")
    public ResponseEntity<ModelsResponseDto<ConsultationSlotModel>> findConsultations(@RequestBody ModelsRequestDto<ConsultationSlotsFilterDto> requestDto) {
        var consultationsResponse = consultationFacade.findConsultationSlots(requestDto);
        return ResponseEntity.ok(consultationsResponse);
    }
}
