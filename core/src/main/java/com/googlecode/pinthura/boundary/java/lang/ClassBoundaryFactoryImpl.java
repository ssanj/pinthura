package com.googlecode.pinthura.boundary.java.lang;

public final class ClassBoundaryFactoryImpl implements ClassBoundaryFactory {

    @Override
    public <T> ClassBoundary<T> create(final Class<T> clazz) {
        return new ClassBoundaryImpl<T>(clazz);
    }
}
