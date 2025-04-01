package com.victor.kochnev.dmsserver.infra.data.adapter;

import com.victor.kochnev.dmsserver.consultation.infra.WorkingTimeModelRepository;
import com.victor.kochnev.dmsserver.consultation.model.WorkingTimeModel;
import com.victor.kochnev.dmsserver.infra.data.converter.WorkingTimeEntityMapper;
import com.victor.kochnev.dmsserver.infra.data.repository.WorkingTimeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class WorkingTimeModelRepositoryImpl implements WorkingTimeModelRepository {
    private final WorkingTimeRepository workingTimeRepository;
    private final WorkingTimeEntityMapper workingTimeEntityMapper;

    @Override
    @Transactional
    public List<WorkingTimeModel> findAllByUserId(UUID userId) {
        return workingTimeRepository.findAllByUserId(userId)
                .stream()
                .map(workingTimeEntityMapper::mapToModel)
                .toList();
    }
}
