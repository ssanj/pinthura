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
package com.googlecode.pinthura.factory.creator;

import com.googlecode.pinthura.data.UrlBoundary;
import com.googlecode.pinthura.data.UrlBoundaryImpl;
import com.googlecode.pinthura.data.Square;
import com.googlecode.pinthura.data.SquareImpl;
import com.googlecode.pinthura.factory.MethodParam;
import com.googlecode.pinthura.factory.boundary.ClassBoundary;
import com.googlecode.pinthura.factory.boundary.ClassBoundaryImpl;
import com.googlecode.pinthura.factory.boundary.ConstructorBoundary;
import com.googlecode.pinthura.factory.locator.deriver.ClassNameDeriver;
import com.googlecode.pinthura.filter.MatchNotFoundException;
import static junit.framework.Assert.fail;
import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import static org.hamcrest.core.IsEqual.equalTo;
import org.hamcrest.core.IsSame;
import static org.junit.Assert.assertThat;
import org.junit.Before;
import org.junit.Test;

public final class ASimpleInstantiatorUnderTest {

    private static final String URL_BOUNDARY_IMPL       = "com.googlecode.pinthura.data.UrlBoundaryImpl";
    private static final String SQUARE_BOUNDARY_IMPL    = "com.googlecode.pinthura.data.SqaureImpl";
    private static final String COLLECTION_IMPL         = "java.util.CollectionImpl";
    private static final int PARAMETER_1                = 20;

    private final IMocksControl mockControl = EasyMock.createControl();
    private MethodParam mockMethodParam;
    private ClassNameDeriver mockClassNameDeriver;
    private SimpleInstantiator instantiator;

    @Before
    public void setup() {
        mockMethodParam = mockControl.createMock(MethodParam.class);
        mockClassNameDeriver = mockControl.createMock(ClassNameDeriver.class);

        instantiator = new SimpleInstantiator(mockClassNameDeriver);
    }

    @SuppressWarnings({ "unchecked" })
    @Test
    public void shouldCreateAnInstance() {
        ClassBoundary mockLoadedClass = loadClass(URL_BOUNDARY_IMPL);
        ConstructorBoundary mockConstructorBoundary = findConstructor(mockLoadedClass, new ClassBoundary[] {});

        UrlBoundary urlBoundary = new UrlBoundaryImpl();
        instantiateConstructor(mockConstructorBoundary, urlBoundary, new Object[] {});
        mockControl.replay();

        UrlBoundary result = (UrlBoundary) instantiator.filter(mockMethodParam);
        assertThat(result, IsSame.sameInstance(urlBoundary));

        mockControl.verify();
    }

    @SuppressWarnings({ "unchecked" })
    @Test
    public void shouldCreateAnInstanceWithParameters() {
        ClassBoundary mockLoadedClass = loadClass(SQUARE_BOUNDARY_IMPL);
        ClassBoundary<?>[] parameterTypes = {new ClassBoundaryImpl(int.class)};
        ConstructorBoundary mockConstructorBoundary = findConstructor(mockLoadedClass, parameterTypes);

        Square square = new SquareImpl(PARAMETER_1);
        instantiateConstructor(mockConstructorBoundary, square, new Object[] {PARAMETER_1});
        mockControl.replay();

        Square result = (Square) instantiator.filter(mockMethodParam);
        assertThat(result, IsSame.sameInstance(square));

        mockControl.verify();
    }

    @SuppressWarnings({ "unchecked", "ThrowableInstanceNeverThrown" })
    @Test
    public void shouldThrowAnExceptionIfTheInstanceCantBeCreated() {
        ClassBoundary mockClassBoundary = mockControl.createMock(ClassBoundary.class);
        EasyMock.expect(mockMethodParam.getReturnType()).andReturn(mockClassBoundary).times(2);
        EasyMock.expect(mockClassNameDeriver.derive(EasyMock.isA(ClassBoundary.class))).andReturn(COLLECTION_IMPL);
        EasyMock.expect(mockClassBoundary.forName(COLLECTION_IMPL)).andThrow(new IllegalStateException());
        mockControl.replay();

        try {
            instantiator.filter(mockMethodParam);
            fail();
        } catch (MatchNotFoundException e) {
            assertThat(e.getCause().getClass() == IllegalStateException.class, equalTo(true));
            assertThat(e.getMessage(), equalTo("Could not load implementation for class: java.util.CollectionImpl"));
        }
    }

    private void instantiateConstructor(final ConstructorBoundary mockConstructorBoundary, final Object object, final Object[] args) {
        EasyMock.expect(mockMethodParam.getArguments()).andReturn(args);
        EasyMock.expect(mockConstructorBoundary.newInstance(args)).andReturn(object);
    }

    @SuppressWarnings({ "unchecked" })
    private ConstructorBoundary findConstructor(final ClassBoundary mockLoadedClass, final ClassBoundary<?>[] parameterTypes) {
        EasyMock.expect(mockMethodParam.getParameterTypes()).andReturn(parameterTypes);

        ConstructorBoundary<?> mockConstructorBoundary = mockControl.createMock(ConstructorBoundary.class);
        EasyMock.expect(mockLoadedClass.getConstructor(parameterTypes)).andReturn(mockConstructorBoundary);
        return mockConstructorBoundary;
    }

    @SuppressWarnings({ "unchecked" })
    private ClassBoundary loadClass(final String className) {
        ClassBoundary mockClassBoundary = mockControl.createMock(ClassBoundary.class);
        EasyMock.expect(mockMethodParam.getReturnType()).andReturn(mockClassBoundary).times(2);
        EasyMock.expect(mockClassNameDeriver.derive(mockClassBoundary)).andReturn(className);

        ClassBoundary mockLoadedClass = mockControl.createMock(ClassBoundary.class);
        EasyMock.expect(mockClassBoundary.forName(className)).andReturn(mockLoadedClass);
        return mockLoadedClass;
    }

    @Test
    public void shouldReturnItsName() {
        assertThat(instantiator.getFilterName(), equalTo("Simple Instantiator"));
    }
}
