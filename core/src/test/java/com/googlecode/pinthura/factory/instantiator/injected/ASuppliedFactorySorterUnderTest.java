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
    public void shouldSortInstancesIntoEmptyLocations() {
        ClassInstance[] classInstances = new ClassInstance[3];
        ClassInstance mockInstance1 = mockControl.createMock(ClassInstance.class);
        ClassInstance mockInstance2 = mockControl.createMock(ClassInstance.class);
        classInstances[0] = mockInstance1;
        classInstances[2] = mockInstance2;
        ClassBoundary mockClassBoundary = mockControl.createMock(ClassBoundary.class);
        EasyMock.expect(mockMethodParam.getParameterTypes()).andReturn(Arrayz.createArray(mockClassBoundary));
        EasyMock.expect(mockMethodParam.getArguments()).andReturn(Arrayz.createArray(STRING_INSTANCE));
        ClassInstance mockClassInstance = mockControl.createMock(ClassInstance.class);
        EasyMock.expect(mockClassInstanceFactory.createClassInstance(mockClassBoundary, STRING_INSTANCE)).andReturn(mockClassInstance);
        mockControl.replay();

        sorter.sort(mockMethodParam, classInstances);
        assertThat(classInstances[0], sameInstance(mockInstance1));
        assertThat(classInstances[1], sameInstance(mockClassInstance));
        assertThat(classInstances[2], sameInstance(mockInstance2));

        mockControl.verify();
    }
}
