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
package com.googlecode.pinthura.factory;

import com.googlecode.pinthura.boundary.java.lang.ClassBoundary;
import com.googlecode.pinthura.boundary.java.lang.reflect.MethodBoundary;
import com.googlecode.pinthura.boundary.java.lang.reflect.MethodBoundaryImpl;

import java.lang.reflect.Method;
import java.util.Arrays;

public final class MethodParamImpl implements MethodParam {

    private final MethodBoundary method;
    private final Object[] arguments;

    //TODO: Why aren't we using a MethodBoundary here?
    public MethodParamImpl(final Method method, final Object[] arguments) {
        this.arguments = arguments;
        this.method = new MethodBoundaryImpl(method);
    }

    public ClassBoundary<?> getReturnType() {
        return method.getReturnType();
    }

    //TODO: we could replace this with a ClassInstance (1)
    public Object[] getArguments() {
        return arguments;
    }

    public MethodBoundary getMethod() {
        return method;
    }

    //TODO: we could replace this with a ClassInstance (2)
    public ClassBoundary<?>[] getParameterTypes() {
        return method.getParameterTypes();
    }

    @Override
    public boolean equals(final Object object) {
        return object != null && object instanceof MethodParamImpl
                && method.equals(((MethodParamImpl) object).getMethod())
                && Arrays.equals(arguments, ((MethodParamImpl) object).getArguments());
    }

    @Override
    public int hashCode() {
        int result;
        result = (method != null ? method.hashCode() : 0);
        //CHECKSTYLE_OFF
        result = 31 * result + (arguments != null ? Arrays.hashCode(arguments) : 0);
        //CHECKSTYLE_ON
        return result;
    }
}

