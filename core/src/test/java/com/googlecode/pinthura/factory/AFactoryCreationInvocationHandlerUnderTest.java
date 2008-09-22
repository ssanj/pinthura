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
package com.googlecode.pinthura.factory;

import com.googlecode.pinthura.data.UrlBoundary;
import com.googlecode.pinthura.data.UrlBoundaryFactory;
import com.googlecode.pinthura.data.UrlBoundaryImpl;
import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public final class AFactoryCreationInvocationHandlerUnderTest {

    private static final String URL1            = "http://test.org";
    private static final String CREATION_METHOD = "createUrlBoundary";

    private final IMocksControl mockControl = EasyMock.createControl();

    private InvocationHandler handler;
    private ClassLocator mockFilterChain;
    private MethodParamFactory mockMethodParamFactory;
    private Class<UrlBoundaryFactory> factoryInterface;

    @SuppressWarnings({ "unchecked" })
    @Before
    public void setup() {
        mockFilterChain = mockControl.createMock(ClassLocator.class);
        mockMethodParamFactory = mockControl.createMock(MethodParamFactory.class);
        factoryInterface = UrlBoundaryFactory.class;

        handler = new FactoryCreationInvocationHandler(mockFilterChain, mockMethodParamFactory, factoryInterface);
    }

    @Test
    public void shouldCreateAnInstanceWithConstructorArguments() throws Throwable {
        Method method = getMethod(UrlBoundaryFactory.class, CREATION_METHOD, String.class);
        expectCreateInstance(URL1, method, URL1);
    }

    @Test
    public void shouldCreateAnInstanceWithANoArgConstructor() throws Throwable {
        Method method = getMethod(UrlBoundaryFactory.class, CREATION_METHOD);
        expectCreateInstance(UrlBoundaryImpl.DEFAULT_URL, method);
    }

    @SuppressWarnings({ "unchecked" })
    private void expectCreateInstance(final String expectUrl, final Method method, final Object... params) throws Throwable {
        MethodParam mockMethodParam = mockControl.createMock(MethodParam.class);

        EasyMock.expect(mockMethodParamFactory.create(method, params, factoryInterface)).andReturn(mockMethodParam);
        EasyMock.expect(mockFilterChain.filter(mockMethodParam));
        EasyMock.expectLastCall().andReturn(UrlBoundaryImpl.class);

        mockControl.replay();

        UrlBoundary result = (UrlBoundary) handler.invoke(null, method, params);
        assertUrlBoundary(result, expectUrl);

        mockControl.verify();
    }

    private void assertUrlBoundary(final UrlBoundary result, final String expectedUrl) {
        assertThat(UrlBoundaryImpl.class.isAssignableFrom(result.getClass()), equalTo(true));
        assertThat(result.getUrlAsString(), equalTo(expectedUrl));
    }

    private Method getMethod(final Class<?> clazz, final String methodName, final Class... classes) throws Exception {
        return clazz.getDeclaredMethod(methodName, classes);
    }
}
