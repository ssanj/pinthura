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
package com.googlecode.pinthura.factory.instantiator;

import com.googlecode.pinthura.boundary.java.lang.ClassBoundary;
import com.googlecode.pinthura.boundary.java.lang.reflect.ConstructorBoundary;
import com.googlecode.pinthura.factory.MethodParam;
import com.googlecode.pinthura.util.Arrayz;

public final class ConstructorLocatorImpl implements ConstructorLocator {

    private final Arrayz arrayz;

    public ConstructorLocatorImpl(final Arrayz arrayz) {
        this.arrayz = arrayz;
    }

    @SuppressWarnings("unchecked")
    public <T> ConstructorBoundary<T> locate(final MethodParam methodParam, final String className) {
        return (ConstructorBoundary<T>) methodParam.getReturnType().forName(className).getConstructor(
                arrayz.fromCollection(methodParam.getParameterTypes(), ClassBoundary.class));
    }

    public <T> ConstructorBoundary<T> locate(final MethodParam methodParam, final ClassBoundary<T> clazz) {
        return clazz.getConstructor(arrayz.fromCollection(methodParam.getParameterTypes(), ClassBoundary.class));
    }
}
