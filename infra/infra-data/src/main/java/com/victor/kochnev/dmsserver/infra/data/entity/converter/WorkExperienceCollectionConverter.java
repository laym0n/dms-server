package com.victor.kochnev.dmsserver.infra.data.entity.converter;

import com.victor.kochnev.dmsserver.profile.model.WorkExperienceModel;

public class WorkExperienceCollectionConverter extends CollectionConverter<WorkExperienceModel> {
    @Override
    protected Class<WorkExperienceModel> getInnerClass() {
        return WorkExperienceModel.class;
    }
}
