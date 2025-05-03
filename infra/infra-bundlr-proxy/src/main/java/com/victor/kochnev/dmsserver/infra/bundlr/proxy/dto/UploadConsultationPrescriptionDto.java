package com.victor.kochnev.dmsserver.infra.bundlr.proxy.dto;

import com.victor.kochnev.dmsserver.consultation.dto.ConsultationPrescriptionDto;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class UploadConsultationPrescriptionDto extends ConsultationPrescriptionDto {
    private UUID consultationId;
}
