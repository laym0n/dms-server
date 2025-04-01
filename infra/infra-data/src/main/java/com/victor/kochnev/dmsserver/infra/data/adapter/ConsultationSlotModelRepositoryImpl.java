package com.victor.kochnev.dmsserver.infra.data.adapter;

import com.victor.kochnev.dmsserver.consultation.infra.ConsultationSlotModelRepository;
import com.victor.kochnev.dmsserver.consultation.model.ConsultationSlotModel;
import com.victor.kochnev.dmsserver.infra.data.converter.ConsultationSlotEntityMapper;
import com.victor.kochnev.dmsserver.infra.data.repository.ConsultationSlotRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class ConsultationSlotModelRepositoryImpl implements ConsultationSlotModelRepository {
    private final ConsultationSlotRepository consultationSlotRepository;
    private final ConsultationSlotEntityMapper consultationSlotEntityMapper;

    @Override
    @Transactional
    public Optional<ConsultationSlotModel> findById(UUID id) {
        return consultationSlotRepository.findById(id)
                .map(consultationSlotEntityMapper::mapToModel);
    }
}
