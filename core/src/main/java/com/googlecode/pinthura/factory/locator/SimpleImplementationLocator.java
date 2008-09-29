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

import com.googlecode.pinthura.factory.ClassLocator;
import com.googlecode.pinthura.factory.MethodParam;
import com.googlecode.pinthura.factory.boundary.ClassBoundaryImpl;
import com.googlecode.pinthura.factory.locator.deriver.ClassNameDeriver;
import com.googlecode.pinthura.filter.MatchNotFoundException;

public final class SimpleImplementationLocator implements ClassLocator {

    private static final String FILTER_NAME = "Simple Implementation Locator";

    private final ClassNameDeriver classNameDeriver;

    public SimpleImplementationLocator(final ClassNameDeriver classNameDeriver) {
        this.classNameDeriver = classNameDeriver;
    }

    @SuppressWarnings({ "unchecked" })
    public Class<?> filter(final MethodParam methodParam) {
        return getImplementationClassName(methodParam.getReturnType());
    }

    public String getFilterName() {
        return FILTER_NAME;
    }

    @SuppressWarnings({ "unchecked" })
    private <T> Class<T> getImplementationClassName(final Class<T> returnType)  {
        //TODO: Does the boundary make sense here?
        String clazz = classNameDeriver.derive(new ClassBoundaryImpl(returnType));

        try {
            return (Class<T>) Class.forName(clazz);
        } catch (ClassNotFoundException e) {
            throw new MatchNotFoundException("Could not load implementation for class: " + clazz, e);
        }
    }
}
