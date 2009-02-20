/*
 * Copyright 2008 Sanjiv Sahayam
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.googlecode.pinthura.factory.locator;

import com.googlecode.pinthura.factory.MethodParam;
import com.googlecode.pinthura.factory.MethodParamImpl;
import com.googlecode.pinthura.util.Arrayz;
import com.googlecode.pinthura.util.ArrayzImpl;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public final class MethodParamBuilder {

    private final List<Object> argumentsList;
    private final List<Class<?>> typeList;
    private final Arrayz arrayz;
    private Class<?> targetClazz;
    private String methodName;

    public MethodParamBuilder() {
        argumentsList = new ArrayList<Object>();
        typeList = new ArrayList<Class<?>>();
        arrayz = new ArrayzImpl();
    }

    public MethodParamBuilder findMethod() {
        return this;
    }

    public MethodParamBuilder onClass(final Class<?> targetClazz) {
        this.targetClazz = targetClazz;
        return this;
    }

    public MethodParamBuilder havingMethodNameOf(final String methodName) {
        this.methodName = methodName;
        return this;
    }

    public MethodParamBuilder withParameter(final Object parameter) {
        typeList.add(parameter.getClass());
        argumentsList.add(parameter);
        return this;
    }

    public MethodParam build() {
        try {
            Method method = targetClazz.getMethod(methodName, arrayz.fromCollection(typeList, Class.class));
            return new MethodParamImpl(method,  arrayz.fromCollection(argumentsList, Object.class));
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }
}
