package com.victor.kochnev.dmsserver.profile.infra;

import com.victor.kochnev.dmsserver.profile.model.SpecializationModel;

import java.util.List;

public interface SpecializationModelRepository {
    List<SpecializationModel> findAll();
}
