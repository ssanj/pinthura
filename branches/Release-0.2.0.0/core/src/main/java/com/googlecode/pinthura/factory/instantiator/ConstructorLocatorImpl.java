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

import com.googlecode.pinthura.factory.MethodParam;
import com.googlecode.pinthura.factory.boundary.ClassBoundary;
import com.googlecode.pinthura.factory.boundary.ConstructorBoundary;

public final class ConstructorLocatorImpl implements ConstructorLocator {

    @SuppressWarnings({ "unchecked" })
    public <T> ConstructorBoundary<T> locate(final MethodParam methodParam, final String className) {
        return (ConstructorBoundary<T>) methodParam.getReturnType().forName(className).getConstructor(methodParam.getParameterTypes());
    }

    public <T> ConstructorBoundary<T> locate(final MethodParam methodParam, final ClassBoundary<T> clazz) {
        return clazz.getConstructor(methodParam.getParameterTypes());
    }
}