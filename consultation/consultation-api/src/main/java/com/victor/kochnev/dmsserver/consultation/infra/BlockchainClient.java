package com.victor.kochnev.dmsserver.consultation.infra;

import com.victor.kochnev.dmsserver.consultation.dto.BlockchainRecord;
import com.victor.kochnev.dmsserver.consultation.dto.ConsultationPrescriptionDto;

import java.util.UUID;

public interface BlockchainClient {
    BlockchainRecord persist(UUID consultationId, ConsultationPrescriptionDto prescriptionDto);
}
