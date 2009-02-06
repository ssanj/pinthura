package com.googlecode.pinthura.boundary.java.lang;

public interface ClassBoundaryFactory {

    <T> ClassBoundary<T> create(Class<T> clazz);
}
