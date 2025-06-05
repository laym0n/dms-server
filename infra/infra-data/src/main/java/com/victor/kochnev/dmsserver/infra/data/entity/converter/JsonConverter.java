package com.victor.kochnev.dmsserver.infra.data.entity.converter;

import lombok.SneakyThrows;

public abstract class JsonConverter<X> extends AbstractConverter<X, String> {

    protected abstract Class<X> getMappingClass();

    @SneakyThrows
    @Override
    public String convertToDatabaseColumn(X attribute) {
        if (attribute == null) {
            return null;
        }
        return objectMapper.writeValueAsString(attribute);
    }

    @SneakyThrows
    @Override
    public X convertToEntityAttribute(String dbData) {
        if (dbData == null) {
            return null;
        }
        return objectMapper.readValue(dbData, getMappingClass());
    }
}
