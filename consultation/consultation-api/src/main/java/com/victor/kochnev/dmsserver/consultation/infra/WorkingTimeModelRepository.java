package com.victor.kochnev.dmsserver.consultation.infra;

import com.victor.kochnev.dmsserver.consultation.model.WorkingTimeModel;

import java.util.List;
import java.util.UUID;

public interface WorkingTimeModelRepository {

    List<WorkingTimeModel> findAllByUserId(UUID id);
}
