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

import com.googlecode.pinthura.data.ShapeFactory;
import com.googlecode.pinthura.data.UrlBoundaryFactory;
import com.googlecode.pinthura.factory.Factory;
import com.googlecode.pinthura.factory.FactoryCreator;
import com.googlecode.pinthura.factory.instantiator.ClassInstance;
import com.googlecode.pinthura.factory.instantiator.ClassInstanceFactory;
import com.googlecode.pinthura.factory.instantiator.FactoryCreationEvent;
import com.googlecode.pinthura.factory.instantiator.FactoryCreationMonitor;
import com.googlecode.pinthura.util.Arrayz;
import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;
import org.junit.Before;
import org.junit.Test;

public final class AResolvedFactorySorterUnderTest {

    private final IMocksControl mockControl = EasyMock.createControl();
    private ClassInstanceFactory mockClassInstanceFactory;
    private FactoryCreationMonitor mockFactoryCreationMonitor;

    @Before
    public void setup() {
        mockClassInstanceFactory = mockControl.createMock(ClassInstanceFactory.class);
        mockFactoryCreationMonitor = mockControl.createMock(FactoryCreationMonitor.class);
        mockFactoryCreationMonitor.registerInterest(EasyMock.isA(ResolvedFactorySorterImpl.class));
    }

    @Test
    public void shouldSortFactoriesInTheMiddle() {
        expectSortedInstances(1, 3, 5);
    }

    @Test
    public void shouldSortFactoriesAtTheEdges() {
        expectSortedInstances(0, 4, 5);
    }

    @Test
    public void shouldSortFactoriesWhenThereAreNoGaps() {
        expectSortedInstances(0, 1, 2);
    }

    @Test(expected = ArrayIndexOutOfBoundsException.class)
    public void shouldThrowExceptionIfTheIndecesAreOutOfBounds() {
        expectSortedInstances(1, 11, 2);        
    }

    private void expectSortedInstances(final int firstIndex, final int secondIndex, final int size) {
        Factory mockFactory1 = mockControl.createMock(Factory.class);
        Factory mockFactory2 = mockControl.createMock(Factory.class);
        expectFactoryClass(mockFactory1, ShapeFactory.class, firstIndex);
        expectFactoryClass(mockFactory2, UrlBoundaryFactory.class, secondIndex);
        Factory[] factories = Arrayz.createArray(mockFactory1, mockFactory2);

        FactoryCreator mockFactoryCreator = mockControl.createMock(FactoryCreator.class);
        FactoryCreationEvent mockFactoryCreationEvent = mockControl.createMock(FactoryCreationEvent.class);
        EasyMock.expect(mockFactoryCreationEvent.getInstance()).andReturn(mockFactoryCreator);

        ClassInstance[] classInstances = new ClassInstance[size];
        ShapeFactory mockShapeFactory = mockControl.createMock(ShapeFactory.class);
        UrlBoundaryFactory mockUrlBoundaryFactory = mockControl.createMock(UrlBoundaryFactory.class);
        EasyMock.expect(mockFactoryCreator.create(ShapeFactory.class)).andReturn(mockShapeFactory);
        EasyMock.expect(mockFactoryCreator.create(UrlBoundaryFactory.class)).andReturn(mockUrlBoundaryFactory);

        ClassInstance mockClassInstance1 = mockControl.createMock(ClassInstance.class);
        ClassInstance mockClassInstance2 = mockControl.createMock(ClassInstance.class);
        EasyMock.expect(mockClassInstanceFactory.createClassInstance(ShapeFactory.class, mockShapeFactory)).
                andReturn(mockClassInstance1);
        EasyMock.expect(mockClassInstanceFactory.createClassInstance(UrlBoundaryFactory.class, mockUrlBoundaryFactory)).
                andReturn(mockClassInstance2);
        mockControl.replay();

        ResolvedFactorySorter sorter = new ResolvedFactorySorterImpl(mockClassInstanceFactory, mockFactoryCreationMonitor);
        sorter.factoryCreated(mockFactoryCreationEvent);
        sorter.sort(factories, classInstances);
        assertThat(classInstances[firstIndex], equalTo(mockClassInstance1));
        assertThat(classInstances[secondIndex], equalTo(mockClassInstance2));

        mockControl.verify();
    }

    private void expectFactoryClass(final Factory mockFactory, final Class<?> factoryClass, final int index) {
        EasyMock.expect(mockFactory.factoryClass());
        EasyMock.expectLastCall().andReturn(factoryClass).times(2);
        EasyMock.expect(mockFactory.index()).andReturn(index);
    }
}
