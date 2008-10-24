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

public final class ResolvedFactorySorterImpl implements ResolvedFactorySorter {

    private final ClassInstanceFactory classInstanceFactory;
    private FactoryCreator factoryCreator;

    public ResolvedFactorySorterImpl(final ClassInstanceFactory classInstanceFactory, final FactoryCreationMonitor factoryCreationMonitor) {
        this.classInstanceFactory = classInstanceFactory;
        factoryCreationMonitor.registerInterest(this);
    }

    public void sort(final Factory[] factories, final ClassInstance[] classInstances) {
        for (Factory factory : factories) {
            classInstances[factory.index()] = classInstanceFactory.createClassInstance(factory.factoryClass(),
                    factoryCreator.create(factory.factoryClass()));
        }
    }

    public void factoryCreated(final FactoryCreationEvent e) {
        factoryCreator = e.getInstance();
    }
}
