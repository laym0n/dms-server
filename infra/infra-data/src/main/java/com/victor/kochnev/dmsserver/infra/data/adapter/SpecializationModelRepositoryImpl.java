package com.victor.kochnev.dmsserver.infra.data.adapter;

import com.victor.kochnev.dmsserver.infra.data.converter.SpecializationEntityMapper;
import com.victor.kochnev.dmsserver.infra.data.repository.SpecializationRepository;
import com.victor.kochnev.dmsserver.profile.infra.SpecializationModelRepository;
import com.victor.kochnev.dmsserver.profile.model.SpecializationModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@RequiredArgsConstructor
public class SpecializationModelRepositoryImpl implements SpecializationModelRepository {
    private final SpecializationRepository specializationRepository;
    private final SpecializationEntityMapper specializationEntityMapper;

    @Override
    @Transactional
    public List<SpecializationModel> findAll() {
        return specializationRepository.findAll()
                .stream()
                .map(specializationEntityMapper::mapToModel)
                .toList();
    }
}
