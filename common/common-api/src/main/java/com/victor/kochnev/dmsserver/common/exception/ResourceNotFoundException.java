package com.victor.kochnev.dmsserver.common.exception;

public class ResourceNotFoundException extends ModuleException {
    public ResourceNotFoundException(Class<?> resourceClass, String field, String value) {
        super(resourceClass.getSimpleName() + " not found with " + field + " equals " + value);
    }
}
