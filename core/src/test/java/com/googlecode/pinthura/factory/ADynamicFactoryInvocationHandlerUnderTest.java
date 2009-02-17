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
import static org.hamcrest.core.IsSame.sameInstance;
import static org.junit.Assert.assertThat;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public final class ADynamicFactoryInvocationHandlerUnderTest {

    private static final String URL1            = "http://test.org";
    private static final String METHOD_1        = "createUrlBoundary";
    private static final Object PROXY           = null;

    private final IMocksControl mockControl = EasyMock.createControl();
    private InvocationHandler handler;
    private InstanceCreator mockInstanceCreator;
    private MethodParamFactory mockMethodParamFactory;

    @SuppressWarnings("unchecked")
    @Before
    public void setup() {
        mockInstanceCreator = mockControl.createMock(InstanceCreator.class);
        mockMethodParamFactory = mockControl.createMock(MethodParamFactory.class);

        handler = new DynamicFactoryInvocationHandler(mockInstanceCreator, mockMethodParamFactory);
    }

    @Test
    public void shouldReturnAnInstanceOfTheRequestedType() throws Throwable {
        UrlBoundary expectedResult = new UrlBoundaryImpl(URL1);
        Method method = getMethod(UrlBoundaryFactory.class, METHOD_1, String.class);
        Object[] arguments = {URL1};
        MethodParam mockMethodParam = mockControl.createMock(MethodParam.class);

        EasyMock.expect(mockMethodParamFactory.create(method, arguments)).andReturn(mockMethodParam);
        EasyMock.expect(mockInstanceCreator.createInstance(mockMethodParam)).andReturn(expectedResult);
        mockControl.replay();

        UrlBoundary result = (UrlBoundary) handler.invoke(PROXY, method, arguments);
        assertThat(result, sameInstance(expectedResult));

        mockControl.verify();
    }

    private Method getMethod(final Class<?> clazz, final String methodName, final Class... classes) throws Exception {
        return clazz.getDeclaredMethod(methodName, classes);
    }
}
