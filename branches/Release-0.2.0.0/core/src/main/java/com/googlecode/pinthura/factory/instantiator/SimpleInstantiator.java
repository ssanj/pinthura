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
import com.googlecode.pinthura.factory.boundary.ConstructorBoundary;
import com.googlecode.pinthura.factory.locator.deriver.ClassNameDeriver;
import com.googlecode.pinthura.filter.MatchNotFoundException;

public final class SimpleInstantiator implements InstantiationStrategy {

    private static final String FILTER_NAME = "Simple Instantiator";

    private final ConstructorLocator constructorLocator;
    private final ConstructorInstantiator constructorInstantiator;

    private final ClassNameDeriver classNameDeriver;

    public SimpleInstantiator(final ClassNameDeriver classNameDeriver, final ConstructorLocator locator,
                              final ConstructorInstantiator instantiator) {
        this.classNameDeriver = classNameDeriver;
        this.constructorLocator = locator;
        this.constructorInstantiator = instantiator;
    }

    @SuppressWarnings({ "unchecked" })
    public Object filter(final MethodParam methodParam) {
        String implClass = "[Unknown]";
        try {
            implClass = classNameDeriver.derive(methodParam);
            ConstructorBoundary constructorBoundary = constructorLocator.locate(methodParam, implClass);
            return constructorInstantiator.instantiate(constructorBoundary, methodParam);
        } catch (Exception e) {
            throw new MatchNotFoundException("Could not load implementation for class: " + implClass, e);
        }
    }

    public String getFilterName() {
        return FILTER_NAME;
    }

}
