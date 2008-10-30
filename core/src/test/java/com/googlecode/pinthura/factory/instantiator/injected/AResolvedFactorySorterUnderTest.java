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
import com.googlecode.pinthura.util.Arrayz;
import com.googlecode.pinthura.util.CreationBroker;
import com.googlecode.pinthura.util.CreationListener;
import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;
import org.junit.Before;
import org.junit.Test;

public final class AResolvedFactorySorterUnderTest {

    private final IMocksControl mockControl = EasyMock.createControl();
    private ClassInstanceFactory mockClassInstanceFactory;
    private CreationBroker mockCreationBroker;

    @Before
    public void setup() {
        mockClassInstanceFactory = mockControl.createMock(ClassInstanceFactory.class);
        mockCreationBroker = mockControl.createMock(CreationBroker.class);
    }

    @Test
    public void shouldSortFactoriesInTheMiddle() {
        //CHECKSTYLE_OFF
        expectSortedInstances(1, 3, 5);
        //CHECKSTYLE_ON
    }

    @Test
    public void shouldSortFactoriesAtTheEdges() {
        //CHECKSTYLE_OFF
        expectSortedInstances(0, 4, 5);
        //CHECKSTYLE_ON
    }

    @Test
    public void shouldSortFactoriesWhenThereAreNoGaps() {
        expectSortedInstances(0, 1, 2);
    }

    @Test(expected = ArrayIndexOutOfBoundsException.class)
    public void shouldThrowExceptionIfTheIndecesAreOutOfBounds() {
        //CHECKSTYLE_OFF
        expectSortedInstances(1, 11, 2);
        //CHECKSTYLE_ON
    }

    @SuppressWarnings({ "unchecked" })
    private void expectSortedInstances(final int firstIndex, final int secondIndex, final int size) {
        Factory[] factories = expectFactories(firstIndex, secondIndex);
        FactoryCreator mockFactoryCreator = mockControl.createMock(FactoryCreator.class);
        ClassInstance[] classInstances = new ClassInstance[size];
        ClassInstance mockClassInstance1 = expectInstance1(mockFactoryCreator);
        ClassInstance mockClassInstance2 = expectInstance2(mockFactoryCreator);
        mockCreationBroker.addCreationListener(EasyMock.eq(FactoryCreator.class), EasyMock.isA(CreationListener.class));
        mockControl.replay();

        ResolvedFactorySorter sorter = new ResolvedFactorySorterImpl(mockClassInstanceFactory, mockCreationBroker);
        sorter.instanceCreated(mockFactoryCreator);
        sorter.sort(factories, classInstances);
        assertThat(classInstances[firstIndex], equalTo(mockClassInstance1));
        assertThat(classInstances[secondIndex], equalTo(mockClassInstance2));

        mockControl.verify();
    }

    private ClassInstance expectInstance2(final FactoryCreator mockFactoryCreator) {
        UrlBoundaryFactory mockUrlBoundaryFactory = mockControl.createMock(UrlBoundaryFactory.class);
        EasyMock.expect(mockFactoryCreator.create(UrlBoundaryFactory.class)).andReturn(mockUrlBoundaryFactory);
        ClassInstance mockClassInstance2 = mockControl.createMock(ClassInstance.class);
        EasyMock.expect(mockClassInstanceFactory.createClassInstance(UrlBoundaryFactory.class, mockUrlBoundaryFactory)).
                andReturn(mockClassInstance2);
        return mockClassInstance2;
    }

    private ClassInstance expectInstance1(final FactoryCreator mockFactoryCreator) {
        ShapeFactory mockShapeFactory = mockControl.createMock(ShapeFactory.class);
        EasyMock.expect(mockFactoryCreator.create(ShapeFactory.class)).andReturn(mockShapeFactory);
        ClassInstance mockClassInstance1 = mockControl.createMock(ClassInstance.class);
        EasyMock.expect(mockClassInstanceFactory.createClassInstance(ShapeFactory.class, mockShapeFactory)).
                andReturn(mockClassInstance1);
        return mockClassInstance1;
    }

    private Factory[] expectFactories(final int firstIndex, final int secondIndex) {
        Factory mockFactory1 = mockControl.createMock(Factory.class);
        Factory mockFactory2 = mockControl.createMock(Factory.class);
        expectFactoryClass(mockFactory1, ShapeFactory.class, firstIndex);
        expectFactoryClass(mockFactory2, UrlBoundaryFactory.class, secondIndex);
        return Arrayz.createArray(mockFactory1, mockFactory2);
    }

    private void expectFactoryClass(final Factory mockFactory, final Class<?> factoryClass, final int index) {
        EasyMock.expect(mockFactory.factoryClass());
        EasyMock.expectLastCall().andReturn(factoryClass).times(2);
        EasyMock.expect(mockFactory.index()).andReturn(index);
    }
}
