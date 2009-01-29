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

import com.googlecode.pinthura.boundary.java.lang.ClassBoundary;
import com.googlecode.pinthura.boundary.java.lang.ClassBoundaryImpl;
import com.googlecode.pinthura.boundary.java.lang.reflect.ConstructorBoundary;
import com.googlecode.pinthura.data.Square;
import com.googlecode.pinthura.factory.MethodParam;
import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import static org.hamcrest.core.IsSame.sameInstance;
import static org.junit.Assert.assertThat;
import org.junit.Before;
import org.junit.Test;

public final class AConstructorLocatorUnderTest {

    private static final String CLASS_NAME = "com.googlecode.pinthura.data.SquareImpl";

    private final IMocksControl mockControl = EasyMock.createControl();
    private ConstructorLocator constructorLocator;
    private MethodParam mockMethodParam;

    @Before
    public void setup() {
        mockMethodParam = mockControl.createMock(MethodParam.class);
        constructorLocator = new ConstructorLocatorImpl();
    }

    @SuppressWarnings({ "unchecked" })
    @Test
    public void shouldLocateAConstructor() {
        ClassBoundary mockReturnType = mockControl.createMock(ClassBoundary.class);
        ClassBoundary mockLoadedClass = mockControl.createMock(ClassBoundary.class);
        ConstructorBoundary mockConstructorBoundary = mockControl.createMock(ConstructorBoundary.class);

        EasyMock.expect(mockMethodParam.getReturnType()).andReturn(mockReturnType);
        EasyMock.expect(mockReturnType.forName(CLASS_NAME)).andReturn(mockLoadedClass);

        ClassBoundary<?>[] argTypes = createArgTypes();
        EasyMock.expect(mockMethodParam.getParameterTypes()).andReturn(argTypes);
        EasyMock.expect(mockLoadedClass.getConstructor(argTypes)).andReturn(mockConstructorBoundary);
        mockControl.replay();

        ConstructorBoundary<Square> constructorBoundary = constructorLocator.locate(mockMethodParam, CLASS_NAME);
        assertThat(constructorBoundary, sameInstance(mockConstructorBoundary));

        mockControl.verify();
    }

    private ClassBoundary<?>[] createArgTypes() {
        return new ClassBoundary[] {new ClassBoundaryImpl<Square>(Square.class)};
    }
}
