package com.victor.kochnev.dmsserver.profile.model;

import com.victor.kochnev.dmsserver.auth.model.UserModel;
import com.victor.kochnev.dmsserver.common.model.BaseModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@Schema(oneOf = {DoctorProfileModel.class})
public class ProfileModel extends BaseModel {
    private UserModel user;
    private String city;
    private String name;
}
