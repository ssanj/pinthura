package com.googlecode.pinthura.factory.locator;

import com.googlecode.pinthura.annotation.SuppressionReason;
import com.googlecode.pinthura.boundary.java.lang.reflect.MethodBoundary;
import com.googlecode.pinthura.boundary.java.lang.reflect.MethodBoundaryImpl;
import com.googlecode.pinthura.factory.MethodParam;
import com.googlecode.pinthura.factory.MethodParamImpl;
import com.googlecode.pinthura.util.Arrayz;
import com.googlecode.pinthura.util.ArrayzImpl;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public final class MethodParamIncubator<I, R> {

    private final List<Object> argsList;
    private final List<Class<?>> paramTypesList;
    private final Arrayz arrayz;
    private Method method;
    private Class<I> factoryInterface;
    private String methodName;
    private MethodParam methodParam;
    private Class<R> returnType;
    private boolean nullParameter;

    public MethodParamIncubator() {
        paramTypesList = new ArrayList<Class<?>>();
        argsList = new ArrayList<Object>();
        arrayz = new ArrayzImpl();
    }

    public MethodParamIncubator havingMethod(final Method method) {
        this.method = method;
        return this;
    }

    public MethodParamIncubator supplyMethodName(final String methodName) {
        this.methodName = methodName;
        return this;
    }

    public MethodParamIncubator performGetMethod() {
        try {
            method = factoryInterface.getMethod(methodName, arrayz.fromCollection(paramTypesList, Class.class));
            methodParam = (!nullParameter) ? new MethodParamImpl(method, argsList.toArray()) : new MethodParamImpl(method, null);
        } catch (NoSuchMethodException e) {
            throw new IllegalArgumentException(e);
        }

        return this;
    }

    public MethodParamIncubator supplyTargetClass(final Class<I> factoryInterface) {
        this.factoryInterface = factoryInterface;
        return this;
    }

    public MethodParamIncubator supplyMethodArgument(final Object element) {
        paramTypesList.add(element.getClass());
        argsList.add(element);
        return this;
    }

    public MethodParamIncubator supplyReturnType(final Class<R> returnType) {
        this.returnType = returnType;
        return this;
    }

    public MethodParamIncubator observe() {
        return this;
    }

    public void done() {
        //do nothing.
    }

    public MethodParamIncubator methodName() {
        assertThat(methodParam.getMethod().getName(), equalTo(methodName));
        return this;
    }

    public MethodParamIncubator isReturned() {
        return this;
    }

    public MethodParamIncubator areReturned() {
        return this;
    }

    @SuppressWarnings("unchecked")
    @SuppressionReason(SuppressionReason.Reason.CANT_INFER_GENERICS)
    public MethodParamIncubator parameterTypes() {
        for (int index = 0; index  < paramTypesList.size(); index++) {
            assertThat(methodParam.getParameterTypes().get(index).getClazz(), equalTo((Class) paramTypesList.get(index)));
        }
        return this;
    }

    public MethodParamIncubator parameterValues() {
        for (int index = 0; index  < argsList.size(); index++) {
            assertThat(methodParam.getArguments().get(index), equalTo(argsList.get(index)));
        }        
        return this;
    }

    public MethodParamIncubator methodBoundary() {
        try {
            Method expectedMethod = factoryInterface.getMethod(methodName, arrayz.fromCollection(paramTypesList, Class.class));
            assertThat(methodParam.getMethod(), equalTo((MethodBoundary) new MethodBoundaryImpl(expectedMethod)));
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }

        return this;
    }

    public MethodParamIncubator returnType() {
        assertThat(methodParam.getReturnType().getClazz(), equalTo((Class) returnType));
        return this;
    }

    public MethodParamIncubator supplyNoParameters() {
        nullParameter = true;
        return this;
    }

    public MethodParamIncubator areEmpty() {      
        assertThat(methodParam.getArguments().isEmpty(), equalTo(true));
        assertThat(methodParam.getParameterTypes().isEmpty(), equalTo(true));
        return this;
    }
}
