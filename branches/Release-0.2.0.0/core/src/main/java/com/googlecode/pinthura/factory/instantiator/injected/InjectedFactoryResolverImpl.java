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

import com.googlecode.pinthura.factory.Factory;
import com.googlecode.pinthura.factory.MethodParam;
import com.googlecode.pinthura.factory.boundary.ClassBoundary;
import com.googlecode.pinthura.factory.instantiator.AnnotatedFactoryExtractor;
import com.googlecode.pinthura.factory.instantiator.ClassInstance;
import com.googlecode.pinthura.factory.instantiator.ClassInstanceFactory;

public final class InjectedFactoryResolverImpl implements InjectedFactoryResolver {

    private final AnnotatedFactoryExtractor extractor;
    private final ClassInstanceFactory classInstanceFactory;
    private final InjectedInstanceSorterFactory instanceSorterFactory;
    private final InjectedFactoryValuesFactory factoryValuesFactory;

    public InjectedFactoryResolverImpl(final AnnotatedFactoryExtractor extractor, final ClassInstanceFactory classInstanceFactory,
        final InjectedInstanceSorterFactory instanceSorterFactory, final InjectedFactoryValuesFactory factoryValuesFactory) {
        this.extractor = extractor;
        this.classInstanceFactory = classInstanceFactory;
        this.instanceSorterFactory = instanceSorterFactory;
        this.factoryValuesFactory = factoryValuesFactory;
    }

    @SuppressWarnings({ "unchecked" })
    public InjectedFactoryValues resolve(final MethodParam methodParam) {
        Factory[] factories = extractor.extractFactories(methodParam);
        ClassBoundary<?>[] methodParamTypes = methodParam.getParameterTypes();
        ClassInstance[] classInstances = classInstanceFactory.createClassInstanceArray(methodParamTypes.length + factories.length);

        instanceSorterFactory.createResolvedSorter().sort(factories, classInstances);
        instanceSorterFactory.createSuppliedSorter().sort(methodParam, classInstances);

        return factoryValuesFactory.createInjectedFactoryValues(classInstances);
    }
}
