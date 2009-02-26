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
package com.googlecode.pinthura.factory.instantiator.builder;

import com.googlecode.pinthura.annotation.AnnotationFinder;
import com.googlecode.pinthura.annotation.AnnotationFinderImpl;
import com.googlecode.pinthura.boundary.java.lang.ClassBoundaryFactoryImpl;
import com.googlecode.pinthura.factory.instantiator.AnnotatedClassExtractor;
import com.googlecode.pinthura.factory.instantiator.AnnotatedClassExtractorImpl;
import com.googlecode.pinthura.factory.instantiator.AnnotationInstantiator;
import com.googlecode.pinthura.factory.instantiator.ConstructorInstantiator;
import com.googlecode.pinthura.factory.instantiator.ConstructorInstantiatorImpl;
import com.googlecode.pinthura.factory.instantiator.ConstructorLocator;
import com.googlecode.pinthura.factory.instantiator.ConstructorLocatorImpl;
import com.googlecode.pinthura.util.Arrayz;
import com.googlecode.pinthura.util.ArrayzImpl;

public final class AnnotationInstantiatorBuilder {

    private Arrayz arrayz;
    private ConstructorLocator constructorLocator;
    private ConstructorInstantiator constructorInstantiator;
    private AnnotationFinder annotationFinder;
    private AnnotatedClassExtractor annotatedClassExtractor;

    public AnnotationInstantiatorBuilder() {
        arrayz = new ArrayzImpl();
        constructorLocator = new ConstructorLocatorImpl(arrayz);
        constructorInstantiator = new ConstructorInstantiatorImpl(arrayz);
        annotationFinder = new AnnotationFinderImpl();
        annotatedClassExtractor = new AnnotatedClassExtractorImpl(annotationFinder, new ClassBoundaryFactoryImpl());
    }

    public AnnotationInstantiatorBuilder withArrayz(final Arrayz arrayz) {
        this.arrayz = arrayz;
        return this;
    }

    public AnnotationInstantiatorBuilder withConstructorLocator(final ConstructorLocator constructorLocator) {
        this.constructorLocator = constructorLocator;
        return this;
    }

    public AnnotationInstantiatorBuilder withConstructoInstantiator(final ConstructorInstantiator constructorInstantiator) {
        this.constructorInstantiator = constructorInstantiator;
        return this;
    }

    public AnnotationInstantiatorBuilder withAnnotationFinder(final AnnotationFinder annotationFinder) {
        this.annotationFinder = annotationFinder;
        return this;
    }

    public AnnotationInstantiatorBuilder withAnnotatedClassExtractor(final AnnotatedClassExtractor annotatedClassExtractor) {
        this.annotatedClassExtractor = annotatedClassExtractor;
        return this;
    }

    public AnnotationInstantiator build() {
        return new AnnotationInstantiator(annotatedClassExtractor, constructorLocator, constructorInstantiator);
    }
}
