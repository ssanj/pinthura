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

import com.googlecode.pinthura.factory.boundary.ClassBoundary;
import com.googlecode.pinthura.factory.boundary.MethodBoundary;
import com.googlecode.pinthura.factory.boundary.MethodBoundaryImpl;

import java.lang.reflect.Method;
import java.util.Arrays;

public final class MethodParamImpl implements MethodParam {

    private final MethodBoundary method;
    private final Object[] arguments;

    public MethodParamImpl(final Method method, final Object[] arguments) {
        this.arguments = arguments;
        this.method = new MethodBoundaryImpl(method);
    }

    public ClassBoundary<?> getReturnType() {
        return method.getReturnType();
    }

    public Object[] getArguments() {
        return arguments;
    }

    public MethodBoundary getMethod() {
        return method;
    }

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

