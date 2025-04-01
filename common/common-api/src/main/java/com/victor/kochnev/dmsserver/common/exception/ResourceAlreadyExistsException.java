package com.victor.kochnev.dmsserver.common.exception;

public class ResourceAlreadyExistsException extends ModuleException {
    public ResourceAlreadyExistsException(Class<?> resourceClass) {
        super(resourceClass.getSimpleName() + " already exists");
    }
}
