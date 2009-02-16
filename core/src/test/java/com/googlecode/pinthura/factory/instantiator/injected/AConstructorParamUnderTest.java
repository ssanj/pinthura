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

import com.googlecode.pinthura.annotation.SuppressionReason;
import com.googlecode.pinthura.boundary.java.lang.ClassBoundary;
import com.googlecode.pinthura.data.Shape;
import com.googlecode.pinthura.data.UrlBoundary;
import com.googlecode.pinthura.factory.instantiator.ClassInstance;
import com.googlecode.pinthura.util.Arrayz;
import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
//TODO: Move to an incubator.
public final class AConstructorParamUnderTest {

    private final IMocksControl mockControl = EasyMock.createControl();
    private ClassInstance mockClassInstance1;
    private ClassInstance mockClassInstance2;
    private ClassInstance[] classInstances;
    private Arrayz mockArrayz;

    @Before
    public void setup() {
        mockClassInstance1 = mockControl.createMock(ClassInstance.class);
        mockClassInstance2 = mockControl.createMock(ClassInstance.class);
        mockArrayz = mockControl.createMock(Arrayz.class);

        classInstances = new ClassInstance[] {mockClassInstance1,  mockClassInstance2};
    }

    @SuppressWarnings("unchecked")
    @SuppressionReason(SuppressionReason.Reason.CANT_INFER_GENERICS_ON_MOCKS)
    @Test
    public void shouldReturnConstructorTypes() {
        ClassBoundary mockClassBoundary1 = mockControl.createMock(ClassBoundary.class);
        ClassBoundary mockClassBoundary2 = mockControl.createMock(ClassBoundary.class);
        EasyMock.expect(mockClassInstance1.getClazz()).andReturn(mockClassBoundary1);
        EasyMock.expect(mockClassInstance2.getClazz()).andReturn(mockClassBoundary2);
        ClassBoundary[] classes = {mockClassBoundary1, mockClassBoundary2};
        List<ClassBoundary> classBoundaryList = Arrays.asList(classes);
        EasyMock.expect(mockArrayz.fromCollection(EasyMock.eq(classBoundaryList), EasyMock.eq(ClassBoundary.class))).
                    andReturn(classes);
        mockControl.replay();

        ConstructorParam values = new ConstructorParamImpl(classInstances, mockArrayz);
        ClassBoundary<?>[] result = values.getConstructorTypes();
        assertThat(result, equalTo(classes));

        mockControl.verify();
    }

    @Test
    public void shouldReturnConstructorArgs() {
        UrlBoundary mockUrlBoundary = mockControl.createMock(UrlBoundary.class);
        Shape mockShape = mockControl.createMock(Shape.class);
        EasyMock.expect(mockClassInstance1.getInstance()).andReturn(mockUrlBoundary);
        EasyMock.expect(mockClassInstance2.getInstance()).andReturn(mockShape);
        List<Object> instanceList = Arrays.asList(mockUrlBoundary, mockShape);
        Object[] instanceArray = {mockUrlBoundary, mockShape};
        EasyMock.expect(mockArrayz.fromCollection(EasyMock.eq(instanceList), EasyMock.eq(Object.class))).
                andReturn(instanceArray);
        mockControl.replay();

        ConstructorParam values = new ConstructorParamImpl(classInstances, mockArrayz);
        Object[] result = values.getConstructorArguments();
        assertThat(result, equalTo(instanceArray));

        mockControl.verify();
    }
}
