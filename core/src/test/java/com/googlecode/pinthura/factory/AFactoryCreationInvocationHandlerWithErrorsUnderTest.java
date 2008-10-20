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

import com.googlecode.pinthura.factory.locator.InstanceCreationException;
import com.googlecode.pinthura.filter.MatchNotFoundException;
import static junit.framework.Assert.fail;
import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public final class AFactoryCreationInvocationHandlerWithErrorsUnderTest {

//    private static final String METHOD_GETBYTES = "getBytes";
//    private static final Object[] NO_PARAM      = new Object[]{};
//
//    private final IMocksControl mockControl = EasyMock.createControl();
//    private InvocationHandler handler;
//    private InstanceCreator mockFilterChain;
//    private Method method;
//    private MethodParam mockMethodParam;
//
//    @Before
//    public void setup() throws Exception {
//        MethodParamFactory mockMethodParamFactory = mockControl.createMock(MethodParamFactory.class);
//        mockFilterChain = mockControl.createMock(InstanceCreator.class);
//        mockMethodParam = mockControl.createMock(MethodParam.class);
//
//        method = getMethod(String.class, METHOD_GETBYTES);
//        EasyMock.expect(mockMethodParamFactory.create(method, NO_PARAM)).andReturn(mockMethodParam);
//
//        handler = new DynamicFactoryInvocationHandler(mockFilterChain, mockMethodParamFactory);
//    }
//
//    @SuppressWarnings({ "ThrowableInstanceNeverThrown" })
//    @Test
//    public void shouldThrowAnImplementationNotFoundExceptionIfAClassCantBeLocated() throws Throwable {
//        EasyMock.expect(mockFilterChain.filter(mockMethodParam));
//        EasyMock.expectLastCall().andThrow(new MatchNotFoundException());
//
//        mockControl.replay();
//
//        try {
//            handler.invoke(null, method, NO_PARAM);
//            fail();
//        } catch (InstanceCreationException e) {
//            mockControl.verify();
//            assertThat(e.getMessage(), equalTo("Could not find implementation for class [B"));
//        }
//    }
//
//    @SuppressWarnings({ "ThrowableInstanceNeverThrown" })
//    @Test(expected = IllegalStateException.class)
//    public void shouldRethrowAllOtherExceptions() throws Throwable {
//        EasyMock.expect(mockFilterChain.filter(mockMethodParam));
//        EasyMock.expectLastCall().andThrow(new IllegalStateException());
//        mockControl.replay();
//
//        handler.invoke(null, method, NO_PARAM);
//    }
//
//    private Method getMethod(final Class<?> clazz, final String methodName, final Class... classes) throws Exception {
//        return clazz.getDeclaredMethod(methodName, classes);
//    }
}
