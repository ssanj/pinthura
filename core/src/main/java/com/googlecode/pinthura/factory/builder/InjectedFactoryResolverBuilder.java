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
package com.googlecode.pinthura.factory.builder;

import com.googlecode.pinthura.annotation.AnnotationFinder;
import com.googlecode.pinthura.annotation.AnnotationFinderImpl;
import com.googlecode.pinthura.boundary.java.lang.ClassBoundaryFactory;
import com.googlecode.pinthura.boundary.java.lang.ClassBoundaryFactoryImpl;
import com.googlecode.pinthura.factory.instantiator.AnnotatedFactoryExtractorImpl;
import com.googlecode.pinthura.factory.instantiator.ClassInstanceFactory;
import com.googlecode.pinthura.factory.instantiator.ClassInstanceFactoryImpl;
import com.googlecode.pinthura.factory.instantiator.injected.InjectedFactoryResolver;
import com.googlecode.pinthura.factory.instantiator.injected.InjectedFactoryResolverImpl;
import com.googlecode.pinthura.factory.instantiator.injected.InjectedFactoryValuesFactory;
import com.googlecode.pinthura.factory.instantiator.injected.InjectedFactoryValuesFactoryImpl;
import com.googlecode.pinthura.factory.instantiator.injected.InjectedInstanceSorterFactoryImpl;
import com.googlecode.pinthura.util.ArrayzImpl;
import com.googlecode.pinthura.util.CreationBroker;
import com.googlecode.pinthura.util.CreationBrokerImpl;

public final class InjectedFactoryResolverBuilder {

    private CreationBroker creationBroker;
    private AnnotationFinder annotationFinder;
    private ClassInstanceFactory classInstanceFactory;
    private ClassBoundaryFactory classBoundaryFactory;
    private InjectedFactoryValuesFactory injectedFactoryValuesFactory;

    public InjectedFactoryResolverBuilder() {
        creationBroker = new CreationBrokerImpl();
        annotationFinder = new AnnotationFinderImpl();
        classInstanceFactory = new ClassInstanceFactoryImpl();
        classBoundaryFactory = new ClassBoundaryFactoryImpl();
        injectedFactoryValuesFactory = new InjectedFactoryValuesFactoryImpl(new ArrayzImpl());
    }

    public InjectedFactoryResolverBuilder withCreationBroker(final CreationBroker creationBroker) {
        this.creationBroker = creationBroker;
        return this;
    }

    public InjectedFactoryResolverBuilder withClassInstanceFactory(final ClassInstanceFactory classInstanceFactory) {
        this.classInstanceFactory = classInstanceFactory;
        return this;
    }

    public InjectedFactoryResolverBuilder withClassBoundaryFactory(final ClassBoundaryFactory classBoundaryFactory) {
        this.classBoundaryFactory = classBoundaryFactory;
        return this;
    }

    public InjectedFactoryResolverBuilder withAnnotationFinder(final AnnotationFinder annotationFinder) {
        this.annotationFinder = annotationFinder;
        return this;
    }

    public InjectedFactoryResolverBuilder withFactoryValuesFactory(final InjectedFactoryValuesFactory injectedFactoryValuesFactory) {
        this.injectedFactoryValuesFactory = injectedFactoryValuesFactory;
        return this;
    }

    public InjectedFactoryResolver build() {
        //TODO: use the factoryCreator for this.
        //TODO: pass in all object so that they can be created from a common bootstrapper if required.
        return  new InjectedFactoryResolverImpl(new AnnotatedFactoryExtractorImpl(annotationFinder, classBoundaryFactory),
                classInstanceFactory,
                new InjectedInstanceSorterFactoryImpl(classInstanceFactory, creationBroker),
                injectedFactoryValuesFactory);
    }
}
