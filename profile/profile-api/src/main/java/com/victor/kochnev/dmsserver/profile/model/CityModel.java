package com.victor.kochnev.dmsserver.profile.model;

import com.victor.kochnev.dmsserver.common.model.BaseModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class CityModel extends BaseModel {
    private String name;
}
