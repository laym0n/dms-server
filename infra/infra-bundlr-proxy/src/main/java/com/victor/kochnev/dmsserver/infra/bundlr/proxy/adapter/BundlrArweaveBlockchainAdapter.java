package com.victor.kochnev.dmsserver.infra.bundlr.proxy.adapter;

import com.victor.kochnev.dmsserver.consultation.dto.BlockchainRecord;
import com.victor.kochnev.dmsserver.consultation.dto.ConsultationPrescriptionDto;
import com.victor.kochnev.dmsserver.consultation.infra.BlockchainClient;
import com.victor.kochnev.dmsserver.infra.bundlr.proxy.dto.UploadConsultationPrescriptionDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class BundlrArweaveBlockchainAdapter implements BlockchainClient {
    @Qualifier("bundlrProxyRestClient")
    private final RestClient restClient;

    @Override
    public BlockchainRecord persist(UUID consultationId, ConsultationPrescriptionDto prescriptionDto) {
        var uploadDto = prepareUploadDto(consultationId, prescriptionDto);
        return restClient.post()
                .uri("/upload")
                .contentType(MediaType.APPLICATION_JSON)
                .body(uploadDto)
                .retrieve()
                .toEntity(BlockchainRecord.class)
                .getBody();
    }

    private UploadConsultationPrescriptionDto prepareUploadDto(UUID consultationId, ConsultationPrescriptionDto prescriptionDto) {
        var dto = new UploadConsultationPrescriptionDto();
        dto.setConsultationId(consultationId);
        dto.setPrescription(prescriptionDto.getPrescription());
        return dto;
    }
}
