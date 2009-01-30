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
import com.googlecode.pinthura.factory.FactoryCreator;
import com.googlecode.pinthura.factory.instantiator.ClassInstance;
import com.googlecode.pinthura.factory.instantiator.ClassInstanceFactory;
import com.googlecode.pinthura.util.CreationBroker;

/**
 * Creates proxy instances for each supplied <code>Factory</code> annotation at the index specified with the <code>ClassInstance[]</code>
 * supplied.
 * Eg.
 * Assume [*] is a slot with a proxy.
 * If <code>@Factory factoryClass=UrlBoundaryFactory.class index=2</code>, and <code>classInstance[]</code> -> [][][][]
 *
 * Then creates a proxy of <code>UrlBoundaryFactory</code> and adds it to the 2nd index of <code>classInstance[]</code> -> [][][*][]
 */
public final class ResolvedFactorySorterImpl implements ResolvedFactorySorter {

    private final ClassInstanceFactory classInstanceFactory;
    private final FactoryCreator factoryCreator;

    public ResolvedFactorySorterImpl(final ClassInstanceFactory classInstanceFactory, final CreationBroker creationBroker) {
        this.classInstanceFactory = classInstanceFactory;
        factoryCreator = creationBroker.getInstanceFor(FactoryCreator.class);
    }

    @SuppressWarnings({ "unchecked" })
    public void sort(final Factory[] factories, final ClassInstance[] classInstances) {
        for (Factory factory : factories) {
            classInstances[factory.index()] = classInstanceFactory.createClassInstance(
                    (Class<Object>) factory.factoryClass(), factoryCreator.create(factory.factoryClass()));
        }
    }
}
