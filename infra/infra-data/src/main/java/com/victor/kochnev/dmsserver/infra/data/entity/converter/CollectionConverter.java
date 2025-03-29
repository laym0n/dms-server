package com.victor.kochnev.dmsserver.infra.data.entity.converter;

import lombok.SneakyThrows;

import java.util.Collection;

public abstract class CollectionConverter<X> extends JsonConverter<Collection<X>> {

    @Override
    protected final Class<Collection<X>> getMappingClass() {
        return (Class<Collection<X>>) (Object) Collection.class;
    }

    protected abstract Class<X> getInnerClass();

    @SneakyThrows
    @Override
    public Collection<X> convertToEntityAttribute(String dbData) {
        if (dbData == null) {
            return null;
        }
        Class<X> innerClass = getInnerClass();
        return objectMapper.readValue(dbData, objectMapper.getTypeFactory().constructCollectionType(Collection.class, innerClass));
    }
}
