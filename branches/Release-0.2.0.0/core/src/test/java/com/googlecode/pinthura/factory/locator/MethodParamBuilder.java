package com.googlecode.pinthura.factory.locator;

import com.googlecode.pinthura.factory.MethodParam;
import com.googlecode.pinthura.factory.MethodParamImpl;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public final class MethodParamBuilder<A, I> {

    private final List<A> args = new ArrayList<A>();

    private Method method;
    private Class<I> factoryInterface;

    public MethodParamBuilder havingMethod(final Method method) {
        this.method = method;
        return this;
    }

    public MethodParamBuilder havingMethod(final String methodName, final Class<?>... paramTypes) {
        try {
            method = factoryInterface.getMethod(methodName, paramTypes);
        } catch (NoSuchMethodException e) {
            throw new IllegalArgumentException(e);
        }

        return this;
    }

    public MethodParamBuilder forInterface(final Class<I> factoryInterface) {
        this.factoryInterface = factoryInterface;
        return this;
    }

    public MethodParamBuilder withArgument(final A element) {
        args.add(element);
        return this;
    }

    public MethodParam build() {
        return new MethodParamImpl(method, args.toArray());
    }
}
