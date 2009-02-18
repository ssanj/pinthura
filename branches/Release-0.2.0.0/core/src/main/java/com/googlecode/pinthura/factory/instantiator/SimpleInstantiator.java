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

import com.googlecode.pinthura.boundary.java.lang.reflect.ConstructorBoundary;
import com.googlecode.pinthura.factory.MethodParam;
import com.googlecode.pinthura.factory.locator.deriver.ClassNameDeriver;
import com.googlecode.pinthura.processer.CouldNotProcessInputException;

/**
 * Locates and instantiates a <code>Class</code> by deriving its class name.
 */
public final class SimpleInstantiator implements InstantiationStrategy {

    private static final String FILTER_NAME = "Simple Instantiator";

    private final ConstructorLocator constructorLocator;
    private final ConstructorInstantiator constructorInstantiator;
    private final ClassNameDeriver classNameDeriver;

    /**
     * Constructor.
     * @param classNameDeriver The strategy used to derive class names.
     * @param locator The strategy used to locate constructors.
     * @param instantiator The strategy used to instantiate classes.
     */
    public SimpleInstantiator(final ClassNameDeriver classNameDeriver, final ConstructorLocator locator,
                              final ConstructorInstantiator instantiator) {
        this.classNameDeriver = classNameDeriver;
        constructorLocator = locator;
        constructorInstantiator = instantiator;
    }

    /**
     * Returns an instance of a class given a <code>MethodParam</code> that hints to the class name.
     * @param methodParam The method.
     * @return An instance of a class whose name was derived.
     * @throws CouldNotProcessInputException If the class whose name was derived can't be instantiated.
     */
    @SuppressWarnings("unchecked")
    public Object process(final MethodParam methodParam) {
        String implClass = "[Unknown]";
        try {
            implClass = classNameDeriver.derive(methodParam);
            ConstructorBoundary constructorBoundary = constructorLocator.locate(methodParam, implClass);
            return constructorInstantiator.instantiate(methodParam, constructorBoundary);
        } catch (Exception e) {
            throw new CouldNotProcessInputException("Could not load implementation for class: " + implClass, e);
        }
    }

    /**
     * The processer name displayed when a processer list is displayed.
     * @return The name of this processer.
     */
    public String getProcesserName() {
        return FILTER_NAME;
    }

}
