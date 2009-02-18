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

import com.googlecode.pinthura.annotation.SuppressionReason;
import com.googlecode.pinthura.boundary.java.lang.ClassBoundary;
import com.googlecode.pinthura.boundary.java.lang.reflect.MethodBoundary;
import com.googlecode.pinthura.boundary.java.lang.reflect.MethodBoundaryImpl;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

//TODO: this seems like a compound boundary object. move to the boundary package.
//TODO: rename to MethodParamBoundary. 
public final class MethodParamImpl implements MethodParam {

    private final MethodBoundary method;
    private final List<Object> arguments;

    public MethodParamImpl(final Method method, final Object[] arguments) {
         //special case where the argument supplied by a proxy could be null. Have a look at: java.lang.reflect.InvocationHandler
        this.arguments = (arguments == null) ? Arrays.<Object>asList() : Arrays.asList(arguments);
        this.method = new MethodBoundaryImpl(method);
    }

    public ClassBoundary<?> getReturnType() {
        return method.getReturnType();
    }

    public List<Object> getArguments() {
        return arguments;
    }

    public MethodBoundary getMethod() {
        return method;
    }

    public List<ClassBoundary<?>> getParameterTypes() {
        return Arrays.asList(method.getParameterTypes());
    }

    @SuppressWarnings({"RedundantIfStatement"})
    @SuppressionReason(SuppressionReason.Reason.GENERATED_CODE)
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MethodParamImpl that = (MethodParamImpl) o;

        if (arguments != null ? !arguments.equals(that.arguments) : that.arguments != null) {
            return false;
        }
        if (method != null ? !method.equals(that.method) : that.method != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = method != null ? method.hashCode() : 0;
        result = 31 * result + (arguments != null ? arguments.hashCode() : 0);
        return result;
    }
}

