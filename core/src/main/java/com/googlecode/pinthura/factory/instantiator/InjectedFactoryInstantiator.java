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
import com.googlecode.pinthura.factory.boundary.ClassBoundary;
import com.googlecode.pinthura.filter.MatchNotFoundException;

public final class InjectedFactoryInstantiator implements InstantiationStrategy {

    private static final String FILTER_NAME = "Dynamic Parameter Instantiator";

    private final AnnotatedClassExtractor classExtractor;
    private final InjectedFactoryResolver factoryResolver;

    public InjectedFactoryInstantiator(final AnnotatedClassExtractor classExtractor, final InjectedFactoryResolver factoryResolver) {
        this.classExtractor = classExtractor;
        this.factoryResolver = factoryResolver;
    }

    public Object filter(final MethodParam methodParam) throws MatchNotFoundException {
        //TODO: Handle exceptions and throw a MatchNotFoundException.
        ClassBoundary<?> implClass = classExtractor.extract(methodParam);
        InjectedFactoryValues injectedFactoryValues = factoryResolver.resolve(methodParam);

        return implClass.getConstructor(injectedFactoryValues.getConstructorTypes()).
                newInstance(injectedFactoryValues.getConstructorArguments());
    }

    public String getFilterName() {
        return FILTER_NAME;
    }
}
