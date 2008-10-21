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

import com.googlecode.pinthura.annotation.AnnotationNotFoundException;
import com.googlecode.pinthura.factory.MethodParam;
import com.googlecode.pinthura.factory.boundary.ConstructorBoundary;
import com.googlecode.pinthura.filter.MatchNotFoundException;

public final class AnnotationInstantiator implements InstantiationStrategy {

    private static final String FILTER_NAME = "Annotation Instantiator";

    private final AnnotatedClassExtractor annotationExtractor;
    private final ConstructorLocator constructorLocator;
    private final ConstructorInstantiator instantiator;

    public AnnotationInstantiator(final AnnotatedClassExtractor annotationExtractor, final ConstructorLocator constructorLocator,
                                  final ConstructorInstantiator instantiator) {
        this.annotationExtractor = annotationExtractor;
        this.constructorLocator = constructorLocator;
        this.instantiator = instantiator;
    }

    @SuppressWarnings({ "unchecked" })
    public Object filter(final MethodParam methodParam) {
        try {
            ConstructorBoundary constructor = constructorLocator.locate(methodParam, annotationExtractor.extract(methodParam));
            return instantiator.instantiate(methodParam, constructor);
        } catch (AnnotationNotFoundException e) {
           throw new MatchNotFoundException();
        }
    }

    public String getFilterName() {
        return FILTER_NAME;
    }
}
