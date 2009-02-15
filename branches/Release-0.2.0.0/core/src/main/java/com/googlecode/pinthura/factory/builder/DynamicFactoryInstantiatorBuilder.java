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
import com.googlecode.pinthura.factory.instantiator.AnnotatedClassExtractorImpl;
import com.googlecode.pinthura.factory.instantiator.injected.DynamicFactoryInstantiator;
import com.googlecode.pinthura.util.CreationBroker;
import com.googlecode.pinthura.util.CreationBrokerImpl;

public final class DynamicFactoryInstantiatorBuilder {

    private CreationBroker creationBroker;
    private AnnotationFinder annotationFinder;

    public DynamicFactoryInstantiatorBuilder() {
        creationBroker = new CreationBrokerImpl();
        annotationFinder = new AnnotationFinderImpl();
    }

    public DynamicFactoryInstantiatorBuilder withCreationBroker(final CreationBroker creationBroker) {
        this.creationBroker = creationBroker;
        return this;
    }

    public DynamicFactoryInstantiatorBuilder withAnnotationFinder(final AnnotationFinder annotationFinder) {
        this.annotationFinder = annotationFinder;
        return this;
    }

    public DynamicFactoryInstantiator build() {
        return new DynamicFactoryInstantiator(new AnnotatedClassExtractorImpl(annotationFinder),
                new InjectedFactoryResolverBuilder(creationBroker).build());
    }
}
