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
import com.googlecode.pinthura.util.ArrayzImpl;
import com.googlecode.pinthura.util.CreationBroker;
import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class ResolvedFactorySorterIncubator {

    private final IMocksControl mockControl;
    private final List<Factory> annotationFactories;
    private final ClassInstanceFactory mockClassInstanceFactory;
    private final FactoryCreator mockFactoryCreator;
    private final CreationBroker mockCreationBroker;
    private final List<Integer> expectedIndeces;
    private ClassInstance[] classInstances;
    private final Map<Integer, FactoryAnnoationClassInstance> fciMap;
    private ArrayzImpl arrayz;

    public ResolvedFactorySorterIncubator() {
        mockControl = EasyMock.createControl();
        mockFactoryCreator = mockControl.createMock(FactoryCreator.class);
        mockCreationBroker = mockControl.createMock(CreationBroker.class);
        mockClassInstanceFactory = mockControl.createMock(ClassInstanceFactory.class);
        EasyMock.expect(mockCreationBroker.getInstanceFor(FactoryCreator.class)).andReturn(mockFactoryCreator);

        annotationFactories = new ArrayList<Factory>();
        arrayz = new ArrayzImpl();
        fciMap = new HashMap<Integer, FactoryAnnoationClassInstance>();
        expectedIndeces = new ArrayList<Integer>();
    }

    public FactoryDigression supplyFactory(final Class<?> factoryClass) {
        FactoryAnnoationClassInstance fci = new FactoryAnnoationClassInstance(mockControl, mockClassInstanceFactory,
                factoryClass);
        EasyMock.expect(mockFactoryCreator.create(fci.getClazz())).andReturn(fci.getInstance());
        return new FactoryDigression(this, fci);
    }

    public ResolvedFactorySorterIncubator performSort() {
        mockControl.replay();

        ResolvedFactorySorter sorter = new ResolvedFactorySorterImpl(mockClassInstanceFactory, mockCreationBroker);
        sorter.sort(arrayz.fromCollection(annotationFactories, Factory.class), classInstances);
        return this;
    }

    public ResolvedFactorySorterIncubator classInstance(final ClassInstance classInstance) {
        assertThat(classInstance, equalTo(fciMap.get(getLastIndex()).getClassInstance()));
        return this;
    }

    public ResolvedFactorySorterIncubator observe() {
        return this;
    }

    public ResolvedFactorySorterIncubator isReturned() {
        return this;
    }

    public void done() {
        mockControl.verify();
    }

    public ResolvedFactorySorterIncubator instanceAt(final int index) {
        expectedIndeces.add(index);
        return this;
    }

    public ResolvedFactorySorterIncubator supplyParameterClassInstances(final ClassInstance[] classInstances) {
        this.classInstances = classInstances;
        return this;
    }


    public ResolvedFactorySorterIncubator is() {
        return this;
    }

    public ResolvedFactorySorterIncubator other() {
        return this;
    }

    public ResolvedFactorySorterIncubator instances() {
        return this;
    }

    public ResolvedFactorySorterIncubator are() {
        return this;
    }

    public ResolvedFactorySorterIncubator untouched() {

        for (int index = 0; index < classInstances.length; index++) {
            if (expectedIndeces.contains(index)) {
                continue;
            }

            assertThat(classInstances[index], nullValue());
        }

        return this;
    }

    private int getLastIndex() {
        return expectedIndeces.get(expectedIndeces.size() - 1);
    }

    public static final class FactoryDigression {

        private ResolvedFactorySorterIncubator parent;
        private final FactoryAnnoationClassInstance fci;

        public FactoryDigression(final ResolvedFactorySorterIncubator parent, final FactoryAnnoationClassInstance fci) {
            this.parent = parent;
            this.fci = fci;

            parent.annotationFactories.add(fci.getFactory());
            EasyMock.expect(fci.getFactory().factoryClass());
            EasyMock.expectLastCall().andReturn(fci.getClazz()).times(2);
        }

        public ResolvedFactorySorterIncubator forIndex(final int index) {
            EasyMock.expect(fci.getFactory().index()).andReturn(index);
            parent.fciMap.put(index, fci);
            return parent;
        }
    }
}
