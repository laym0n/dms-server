package com.victor.kochnev.dmsserver.infra.data.converter;

import org.mapstruct.Mapping;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.CLASS)
@Mapping(target = "id", ignore = true)
@Mapping(target = "createDate", ignore = true)
@Mapping(target = "lastChangeDate", ignore = true)
@Mapping(target = "version", ignore = true)
public @interface BlankEntityMapping {
}
