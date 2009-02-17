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

import com.googlecode.pinthura.boundary.java.lang.reflect.ConstructorBoundary;
import com.googlecode.pinthura.data.UrlBoundary;
import com.googlecode.pinthura.factory.MethodParam;
import com.googlecode.pinthura.factory.locator.deriver.ClassNameDeriver;
import com.googlecode.pinthura.filter.CouldNotProcessInputException;
import static junit.framework.Assert.fail;
import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsSame.sameInstance;
import static org.junit.Assert.assertThat;
import org.junit.Before;
import org.junit.Test;

public final class ASimpleInstantiatorUnderTest {

    private static final String URL_BOUNDARY_IMPL = "com.googlecode.pinthura.data.UrlBoundaryImpl";

    private final IMocksControl mockControl = EasyMock.createControl();
    private MethodParam mockMethodParam;
    private ClassNameDeriver mockClassNameDeriver;
    private SimpleInstantiator instantiator;
    private ConstructorLocator mockConstructorLocator;
    private ConstructorInstantiator mockConstructorInstantiator;

    @Before
    public void setup() {
        mockMethodParam = mockControl.createMock(MethodParam.class);
        mockClassNameDeriver = mockControl.createMock(ClassNameDeriver.class);
        mockConstructorLocator = mockControl.createMock(ConstructorLocator.class);
        mockConstructorInstantiator = mockControl.createMock(ConstructorInstantiator.class);

        instantiator = new SimpleInstantiator(mockClassNameDeriver, mockConstructorLocator, mockConstructorInstantiator);
    }

    @Test
    public void shouldCreateAnInstance() {
        UrlBoundary mockUrlBoundary = mockControl.createMock(UrlBoundary.class);
        expectInstantiation(URL_BOUNDARY_IMPL, mockUrlBoundary);
        mockControl.replay();

        UrlBoundary result = (UrlBoundary) instantiator.process(mockMethodParam);
        assertThat(result, sameInstance(mockUrlBoundary));

        mockControl.verify();
    }

    @SuppressWarnings({ "ThrowableInstanceNeverThrown" })
    @Test
    public void shouldThrowAnExceptionIfTheClassNameCantBeDerived() {
        EasyMock.expect(mockClassNameDeriver.derive(mockMethodParam)).andThrow(new IllegalStateException());
        mockControl.replay();

        try {
            instantiator.process(mockMethodParam);
            fail();
        } catch (CouldNotProcessInputException e) {
            assertThat(e.getCause().getClass() == IllegalStateException.class, equalTo(true));
            assertThat(e.getMessage(), equalTo("Could not load implementation for class: [Unknown]"));
        }
    }

    @SuppressWarnings({ "ThrowableInstanceNeverThrown" })
    @Test
    public void shouldThrowAnExceptionIfTheInstanceCantBeCreated() {
        EasyMock.expect(mockClassNameDeriver.derive(mockMethodParam)).andReturn(URL_BOUNDARY_IMPL);
        EasyMock.expect(mockConstructorLocator.locate(mockMethodParam, URL_BOUNDARY_IMPL)).andThrow(new IllegalArgumentException());
        mockControl.replay();

        try {
            instantiator.process(mockMethodParam);
            fail();
        } catch (CouldNotProcessInputException e) {
            assertThat(e.getCause().getClass() == IllegalArgumentException.class, equalTo(true));
            assertThat(e.getMessage(), equalTo("Could not load implementation for class: com.googlecode.pinthura.data.UrlBoundaryImpl"));
        }
    }

    @Test
    public void shouldReturnItsName() {
        assertThat(instantiator.getProcesserName(), equalTo("Simple Instantiator"));
    }

    @SuppressWarnings("unchecked")
    private void expectInstantiation(final String className, final Object instance) {
        EasyMock.expect(mockClassNameDeriver.derive(mockMethodParam)).andReturn(className);

        ConstructorBoundary mockConstructorBoundary = mockControl.createMock(ConstructorBoundary.class);

        EasyMock.expect(mockConstructorLocator.locate(mockMethodParam, className)).andReturn(mockConstructorBoundary);
        EasyMock.expect(mockConstructorInstantiator.instantiate(mockMethodParam, mockConstructorBoundary)).andReturn(instance);
    }
}
