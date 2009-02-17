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
import com.googlecode.pinthura.processer.CouldNotProcessInputException;

/**
 * Instantiates a class from annotations provided on a <code>MethodParam</code>.
 */
public final class AnnotationInstantiator implements InstantiationStrategy {

    private static final String FILTER_NAME = "Annotation Instantiator";

    private final AnnotatedClassExtractor annotationExtractor;
    private final ConstructorLocator constructorLocator;
    private final ConstructorInstantiator instantiator;

    /**
     * Constructor.
     * @param annotationExtractor Strategy for extracting annotations.
     * @param constructorLocator Strategy for locating the correct constructor.
     * @param instantiator Strategy for instantiating located constructor.
     */
    public AnnotationInstantiator(final AnnotatedClassExtractor annotationExtractor, final ConstructorLocator constructorLocator,
                                  final ConstructorInstantiator instantiator) {
        this.annotationExtractor = annotationExtractor;
        this.constructorLocator = constructorLocator;
        this.instantiator = instantiator;
    }

    /**
     * Creates an instance of an object hinted to by annotations on the supplied <code>MethodParam</code>.
     * @param methodParam The annotated method.
     * @return An instance of the object hinted to by annotations on the supplied <code>MethodParam</code>.
     * @throws CouldNotProcessInputException If the instance could not be created.
     */
    public Object process(final MethodParam methodParam) throws CouldNotProcessInputException {
        try {
            ConstructorBoundary<?> constructor = constructorLocator.locate(methodParam, annotationExtractor.extract(methodParam));
            return instantiator.instantiate(methodParam, constructor);
        } catch (Exception e) {
           throw new CouldNotProcessInputException(e);
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
