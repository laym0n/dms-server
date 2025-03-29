package com.victor.kochnev.dmsserver.infra.data.entity.converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.persistence.AttributeConverter;
import lombok.SneakyThrows;

public abstract class JsonConverter<X> implements AttributeConverter<X, String> {
    protected ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

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
