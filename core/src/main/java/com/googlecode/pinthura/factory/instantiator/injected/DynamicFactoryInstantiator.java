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
package com.googlecode.pinthura.factory.instantiator.injected;

import com.googlecode.pinthura.boundary.java.lang.ClassBoundary;
import com.googlecode.pinthura.factory.MethodParam;
import com.googlecode.pinthura.factory.instantiator.AnnotatedClassExtractor;
import com.googlecode.pinthura.factory.instantiator.InstantiationStrategy;
import com.googlecode.pinthura.filter.MatchNotFoundException;

/**
 * Creates an instance of an object via dynamic injection of factory classes.
 */
public final class DynamicFactoryInstantiator implements InstantiationStrategy {

    private static final String FILTER_NAME = "Dynamic Parameter Instantiator";

    private final AnnotatedClassExtractor classExtractor;
    private final InjectedFactoryResolver factoryResolver;

    /**
     * Constructor.
     * @param classExtractor Strategy for extracting annotations from a class.
     * @param factoryResolver Strategy for sorting injected factory classes.
     */
    public DynamicFactoryInstantiator(final AnnotatedClassExtractor classExtractor, final InjectedFactoryResolver factoryResolver) {
        this.classExtractor = classExtractor;
        this.factoryResolver = factoryResolver;
    }

    /**
     * Creates an instance of a <code>Class</code> given an annotated <code>MethodParam</code>.
     * @param methodParam The annotated method.
     * @return An instead created from the information on the annotated <code>MethodParam</code>.
     * @throws MatchNotFoundException If the instance could not be created.
     */
    public Object process(final MethodParam methodParam) throws MatchNotFoundException {
        try {
            ClassBoundary<?> implClass = classExtractor.extract(methodParam);
            ConstructorParam constructorParam = factoryResolver.resolve(methodParam);

            return implClass.getConstructor(constructorParam.getConstructorTypes()).
                    newInstance(constructorParam.getConstructorArguments());
        } catch (Exception e) {
            throw new MatchNotFoundException(e);
        }
    }

    /**
     * The filter name displayed when a filter list is displayed.
     * @return The filter name.
     */
    public String getProcesserName() {
        return FILTER_NAME;
    }
}
