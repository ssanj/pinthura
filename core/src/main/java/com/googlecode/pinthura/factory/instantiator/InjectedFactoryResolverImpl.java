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

import com.googlecode.pinthura.factory.Factory;
import com.googlecode.pinthura.factory.FactoryCreator;
import com.googlecode.pinthura.factory.MethodParam;
import com.googlecode.pinthura.factory.boundary.ClassBoundary;

public final class InjectedFactoryResolverImpl implements InjectedFactoryResolver {

    private final AnnotatedFactoryExtractor extractor;
    private final ResolverObjectFactory objectFactory;
    private FactoryCreator factoryCreator;

    public InjectedFactoryResolverImpl(final AnnotatedFactoryExtractor extractor, final ResolverObjectFactory objectFactory,
                                       final FactoryCreationMonitor factoryCreationMonitor) {
        this.extractor = extractor;
        this.objectFactory = objectFactory;
        factoryCreationMonitor.registerInterest(this);
    }

    @SuppressWarnings({ "unchecked" })
    public InjectedFactoryValues resolve(final MethodParam methodParam) {
        Factory[] factories = extractor.extractFactories(methodParam);
        ClassBoundary<?>[] methodParamTypes = methodParam.getParameterTypes();
        ClassInstance[] classInstances = objectFactory.createClassInstanceArray(methodParamTypes.length + factories.length);

        addResolvedFactories(factories, classInstances);
        fillInTheBlanks(methodParam, methodParamTypes, classInstances);

        return objectFactory.createInjectedFactoryValues(classInstances);
    }

    //Probably not the best way of doing this, but certainly "a way".
    public void factoryCreated(final FactoryCreationEvent e) {
        factoryCreator = e.getInstance();
    }

    private void addResolvedFactories(final Factory[] factories, final ClassInstance[] classInstances) {
        for (Factory factory : factories) {
            classInstances[factory.index()] = objectFactory.createClassInstance(factory.factoryClass(),
                    factoryCreator.create(factory.factoryClass()));
        }
    }

    private void fillInTheBlanks(final MethodParam methodParam, final ClassBoundary<?>[] methodParamTypes,
                                 final ClassInstance[] classInstances) {
        Object[] methodParamArgs = methodParam.getArguments();
        for (int x = 0, count = 0; x < classInstances.length && count < methodParamTypes.length; x++) {
            if (classInstances[x] == null) {
                classInstances[x] = objectFactory.createClassInstance(methodParamTypes[count], methodParamArgs[count++]);
            }
        }
    }
}
