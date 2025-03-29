package com.victor.kochnev.dmsserver.infra.data.entity.converter;

import com.victor.kochnev.dmsserver.profile.model.value.object.EducationModel;

public class EducationCollectionConverter extends CollectionConverter<EducationModel> {
    @Override
    protected Class<EducationModel> getInnerClass() {
        return EducationModel.class;
    }
}
