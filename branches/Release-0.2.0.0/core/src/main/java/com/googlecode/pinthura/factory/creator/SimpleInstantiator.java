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
package com.googlecode.pinthura.factory.creator;

import com.googlecode.pinthura.factory.MethodParam;
import com.googlecode.pinthura.factory.boundary.ConstructorBoundary;
import com.googlecode.pinthura.factory.locator.deriver.ClassNameDeriver;
import com.googlecode.pinthura.filter.MatchNotFoundException;

public final class SimpleInstantiator implements InstantiationStrategy {

    private static final String FILTER_NAME = "Simple Instantiator";

    private final ClassNameDeriver classNameDeriver;

    public SimpleInstantiator(final ClassNameDeriver classNameDeriver) {
        this.classNameDeriver = classNameDeriver;
    }

    @SuppressWarnings({ "unchecked" })
    public Object filter(final MethodParam methodParam) {
        return findAndInstantiate(methodParam);
    }

    public String getFilterName() {
        return FILTER_NAME;
    }

    @SuppressWarnings({ "unchecked" })
    private Object findAndInstantiate(final MethodParam methodParam)  {
        String implClass = classNameDeriver.derive(methodParam.getReturnType());

        try {
            return findConstructor(methodParam, implClass).newInstance(methodParam.getArguments());
        } catch (Exception e) {
            throw new MatchNotFoundException("Could not load implementation for class: " + implClass, e);
        }
    }

    private ConstructorBoundary<?> findConstructor(final MethodParam methodParam, final String implClass) {
        return methodParam.getReturnType().forName(implClass).getConstructor(methodParam.getParameterTypes());
    }
}
