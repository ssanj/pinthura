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

public final class AnInjectedFactoryValuesUnderTest {

//    private final IMocksControl mockControl = EasyMock.createControl();
//    private ClassInstance mockClassInstance1;
//    private ClassInstance mockClassInstance2;
//    private ClassInstance[] classInstances;
//
//    @Before
//    public void setup() {
//        mockClassInstance1 = mockControl.createMock(ClassInstance.class);
//        mockClassInstance2 = mockControl.createMock(ClassInstance.class);
//
//
//        classInstances = ArrayzImpl.fromObjects(mockClassInstance1,  mockClassInstance2);
//    }
//
//    @SuppressWarnings({ "unchecked" })
//    @Test
//    public void shouldReturnConstructorTypes() {
//        ClassBoundary mockClassBoundary1 = mockControl.createMock(ClassBoundary.class);
//        ClassBoundary mockClassBoundary2 = mockControl.createMock(ClassBoundary.class);
//        EasyMock.expect(mockClassInstance1.getClazz()).andReturn(mockClassBoundary1);
//        EasyMock.expect(mockClassInstance2.getClazz()).andReturn(mockClassBoundary2);
//        mockControl.replay();
//
//        InjectedFactoryValues values = new InjectedFactoryValuesImpl(classInstances, arrayz);
//        ClassBoundary<?>[] result = values.getConstructorTypes();
//        assertThat(result, equalTo(ArrayzImpl.fromObjects(mockClassBoundary1, mockClassBoundary2)));
//
//        mockControl.verify();
//    }
//
//    @Test
//    public void shouldReturnConstructorArgs() {
//        UrlBoundary mockUrlBoundary = mockControl.createMock(UrlBoundary.class);
//        Shape mockShape = mockControl.createMock(Shape.class);
//        EasyMock.expect(mockClassInstance1.getInstance()).andReturn(mockUrlBoundary);
//        EasyMock.expect(mockClassInstance2.getInstance()).andReturn(mockShape);
//        mockControl.replay();
//
//        InjectedFactoryValues values = new InjectedFactoryValuesImpl(classInstances, arrayz);
//        Object[] result = values.getConstructorArguments();
//        assertThat(result, equalTo(ArrayzImpl.fromObjects(mockUrlBoundary, mockShape)));
//
//        mockControl.verify();
//    }
}
