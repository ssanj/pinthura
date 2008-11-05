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

import com.googlecode.pinthura.data.Person;
import com.googlecode.pinthura.data.Shape;
import com.googlecode.pinthura.data.Square;
import com.googlecode.pinthura.factory.MethodParam;
import com.googlecode.pinthura.factory.boundary.ClassBoundary;
import com.googlecode.pinthura.factory.instantiator.ClassInstance;
import com.googlecode.pinthura.factory.instantiator.ClassInstanceFactory;
import com.googlecode.pinthura.util.Arrayz;
import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import static org.hamcrest.core.IsSame.sameInstance;
import static org.junit.Assert.assertThat;
import org.junit.Before;
import org.junit.Test;

//TODO: Move to TestBehaviourHandler
public final class ASuppliedFactorySorterUnderTest {

    private static final String STRING_INSTANCE = "testing";

    private final IMocksControl mockControl = EasyMock.createControl();
    private ClassInstanceFactory mockClassInstanceFactory;
    private SuppliedFactorySorter sorter;
    private MethodParam mockMethodParam;

    @Before
    public void setup() {
        mockClassInstanceFactory = mockControl.createMock(ClassInstanceFactory.class);
        mockMethodParam = mockControl.createMock(MethodParam.class);
        sorter = new SuppliedFactorySorterImpl(mockClassInstanceFactory);
    }

    @SuppressWarnings({ "unchecked" })
    @Test
    public void shouldSortInstancesIntoASingleGap() {
        //CHECKSTYLE_OFF
        ClassInstance[] classInstances = new ClassInstance[3];
        //CHECKSTYLE_ON
        ClassInstance mockInstance1 = mockControl.createMock(ClassInstance.class);
        ClassInstance mockInstance2 = mockControl.createMock(ClassInstance.class);
        classInstances[0] = mockInstance1;
        classInstances[2] = mockInstance2;
        ClassBoundary mockClassBoundary = mockControl.createMock(ClassBoundary.class);
        EasyMock.expect(mockMethodParam.getParameterTypes()).andReturn(Arrayz.fromObjects(mockClassBoundary));
        EasyMock.expect(mockMethodParam.getArguments()).andReturn(Arrayz.fromObjects(STRING_INSTANCE));
        ClassInstance mockClassInstance = mockControl.createMock(ClassInstance.class);
        EasyMock.expect(mockClassInstanceFactory.createClassInstance(mockClassBoundary, STRING_INSTANCE)).andReturn(mockClassInstance);
        mockControl.replay();

        sorter.sort(mockMethodParam, classInstances);
        assertThat(classInstances[0], sameInstance(mockInstance1));
        assertThat(classInstances[1], sameInstance(mockClassInstance));
        assertThat(classInstances[2], sameInstance(mockInstance2));

        mockControl.verify();
    }

    @SuppressWarnings({ "unchecked" })
    @Test
    public void shouldSortInstancesAroundUsedSpaces() {
        //CHECKSTYLE_OFF
        ClassInstance[] classInstances = new ClassInstance[4];
        //CHECKSTYLE_ON
        ClassInstance mockUsedInstance = mockControl.createMock(ClassInstance.class);
        classInstances[2] = mockUsedInstance;

        ClassBoundary mockClassBoundary1 = mockControl.createMock(ClassBoundary.class);
        ClassBoundary mockClassBoundary2 = mockControl.createMock(ClassBoundary.class);
        ClassBoundary mockClassBoundary3 = mockControl.createMock(ClassBoundary.class);
        EasyMock.expect(mockMethodParam.getParameterTypes()).
                andReturn(Arrayz.fromObjects(mockClassBoundary1, mockClassBoundary2, mockClassBoundary3));

        Person mockPerson = mockControl.createMock(Person.class);
        Shape mockShape = mockControl.createMock(Shape.class);
        Square mockSquare = mockControl.createMock(Square.class);
        EasyMock.expect(mockMethodParam.getArguments()).andReturn(Arrayz.fromObjects(mockPerson, mockShape, mockSquare));

        ClassInstance mockClassInstance1 = mockControl.createMock(ClassInstance.class);
        ClassInstance mockClassInstance2 = mockControl.createMock(ClassInstance.class);
        ClassInstance mockClassInstance3 = mockControl.createMock(ClassInstance.class);
        EasyMock.expect(mockClassInstanceFactory.createClassInstance(mockClassBoundary1, mockPerson)).andReturn(mockClassInstance1);
        EasyMock.expect(mockClassInstanceFactory.createClassInstance(mockClassBoundary2, mockShape)).andReturn(mockClassInstance2);
        EasyMock.expect(mockClassInstanceFactory.createClassInstance(mockClassBoundary3, mockSquare)).andReturn(mockClassInstance3);
        mockControl.replay();

        sorter.sort(mockMethodParam, classInstances);
        assertThat(classInstances[0], sameInstance(mockClassInstance1));
        assertThat(classInstances[1], sameInstance(mockClassInstance2));
        assertThat(classInstances[2], sameInstance(mockUsedInstance));
        //CHECKSTYLE_OFF
        assertThat(classInstances[3], sameInstance(mockClassInstance3));
        //CHECKSTYLE_ON

        mockControl.verify();
    }
}
