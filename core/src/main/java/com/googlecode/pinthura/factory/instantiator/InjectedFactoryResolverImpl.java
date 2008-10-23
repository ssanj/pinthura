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
import com.googlecode.pinthura.factory.InjectedFactory;
import com.googlecode.pinthura.factory.Factory;
import com.googlecode.pinthura.factory.boundary.ClassBoundary;
import com.googlecode.pinthura.factory.boundary.ClassBoundaryImpl;

public final class InjectedFactoryResolverImpl implements InjectedFactoryResolver {

    private final AnnotatedFactoryExtractor extractor;

    public InjectedFactoryResolverImpl(final AnnotatedFactoryExtractor extractor) {
        this.extractor = extractor;
    }

    @SuppressWarnings({ "unchecked" })
    public InjectedFactoryValues resolve(final MethodParam methodParam) {
        InjectedFactory injectedFactory = extractor.extract(methodParam);
        Factory[] factories = injectedFactory.value();

        ClassBoundary<?>[] methodParamTypes = methodParam.getParameterTypes();
        ClassBoundary[] arguments = new ClassBoundary<?>[methodParamTypes.length + factories.length];

        for (Factory factory : factories) {
            arguments[factory.index()] = new ClassBoundaryImpl(factory.factoryClass());
        }

        int count = 0;
        for (int x=0; x < arguments.length && count < methodParamTypes.length; x++) {
            if (arguments[x] == null) {
                arguments[x] = methodParamTypes[count++];
            }
        }

        return new InjectedFactoryValuesImpl(arguments);
    }
}
