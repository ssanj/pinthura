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

import com.googlecode.pinthura.annotation.AnnotationFinder;
import com.googlecode.pinthura.boundary.java.lang.ClassBoundaryFactory;
import com.googlecode.pinthura.factory.Factory;
import com.googlecode.pinthura.factory.InjectedFactory;
import com.googlecode.pinthura.factory.MethodParam;

public final class AnnotatedFactoryExtractorImpl implements AnnotatedFactoryExtractor {

    private final AnnotationFinder annotationFinder;
    private final ClassBoundaryFactory classBoundaryFactory;

    public AnnotatedFactoryExtractorImpl(final AnnotationFinder annotationFinder, final ClassBoundaryFactory classBoundaryFactory) {
        this.annotationFinder = annotationFinder;
        this.classBoundaryFactory = classBoundaryFactory;
    }

    public InjectedFactory extract(final MethodParam methodParam) {
        return annotationFinder.find(methodParam.getMethod(), classBoundaryFactory.create(InjectedFactory.class));
    }

    public Factory[] extractFactories(final MethodParam methodParam) {
        return extract(methodParam).value();
    }
}
