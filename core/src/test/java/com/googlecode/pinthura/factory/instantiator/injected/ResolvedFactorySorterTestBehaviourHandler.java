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
import com.googlecode.pinthura.util.Arrayz;
import com.googlecode.pinthura.util.CreationBroker;
import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import static org.hamcrest.core.IsEqual.equalTo;
import org.junit.Assert;

import java.util.ArrayList;
import java.util.List;

public final class ResolvedFactorySorterTestBehaviourHandler {

    private final IMocksControl mockControl;
    private final List<ClassInstance> classInstances;
    private final List<Factory> factories;
    private final ClassInstanceFactory mockClassInstanceFactory;
    private FactoryCreator mockFactoryCreator;
    private CreationBroker mockCreationBroker;

    public ResolvedFactorySorterTestBehaviourHandler() {
        mockControl = EasyMock.createControl();
        mockFactoryCreator = mockControl.createMock(FactoryCreator.class);
        mockCreationBroker = mockControl.createMock(CreationBroker.class);
        mockClassInstanceFactory = mockControl.createMock(ClassInstanceFactory.class);

        classInstances = new ArrayList<ClassInstance>();
        factories = new ArrayList<Factory>();
    }

    public ResolvedFactorySorterTestBehaviourHandler addFactory(final Class<?> factoryClass, final int index) {
        Factory mockFactory = mockControl.createMock(Factory.class);
        factories.add(mockFactory);
        EasyMock.expect(mockFactory.factoryClass());
        EasyMock.expectLastCall().andReturn(factoryClass).times(2);
        EasyMock.expect(mockFactory.index()).andReturn(index);
        addClassInstance(factoryClass);
        return this;
    }

    private ResolvedFactorySorterTestBehaviourHandler addClassInstance(final Class<?> factoryClass) {
        Object mockFactoryInstance = mockControl.createMock(factoryClass);
        EasyMock.expect(mockFactoryCreator.create(factoryClass)).andReturn(mockFactoryInstance);
        ClassInstance mockClassInstance = mockControl.createMock(ClassInstance.class);
        EasyMock.expect(mockClassInstanceFactory.createClassInstance(factoryClass, mockFactoryInstance)).
                andReturn(mockClassInstance);
        classInstances.add(mockClassInstance);
        return this;
    }

    @SuppressWarnings({ "unchecked" })
    public void replay() {
        EasyMock.expect(mockCreationBroker.getInstanceFor(FactoryCreator.class)).andReturn(mockFactoryCreator);
        mockControl.replay();
    }

    public void sort(final ClassInstance[] classInstances) {
        ResolvedFactorySorter sorter = new ResolvedFactorySorterImpl(mockClassInstanceFactory, mockCreationBroker);
        sorter.sort(Arrayz.createArray(factories, Factory.class), classInstances);
    }

    public void expectFirstClassInstanceIsEqual(final ClassInstance classInstance) {
        Assert.assertThat(classInstance, equalTo(classInstances.get(0)));
    }

    public void expectSecondClassInstanceIsEqual(final ClassInstance classInstance) {
        Assert.assertThat(classInstance, equalTo(classInstances.get(1)));
    }

    public void verify() {
        mockControl.verify();
    }
}
