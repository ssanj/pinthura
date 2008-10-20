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

import com.googlecode.pinthura.data.UrlBoundaryFactory;
import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import static org.hamcrest.core.IsEqual.equalTo;
import org.hamcrest.core.IsNull;
import org.hamcrest.core.IsSame;
import static org.junit.Assert.assertThat;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

public final class AFactoryCreatorUnderTest {

//    private final IMocksControl mockControl = EasyMock.createControl();
//
//    private InvocationFactory mockInvocationFactory;
//    private InvocationHandler mockInvocationHandler;
//
//    @Before
//    public void setup() {
//        mockInvocationFactory = mockControl.createMock(InvocationFactory.class);
//        mockInvocationHandler = mockControl.createMock(InvocationHandler.class);
//    }
//
//    @Test
//    public void shouldCreateAnInstanceForAGivenFactoryInterface() {
//        expectCreateFactory(UrlBoundaryFactory.class);
//    }
//
//    @Test
//    public void shouldCreateAnInstanceForAnotherFactoryInterface() {
//        expectCreateFactory(InvocationFactory.class);
//    }
//
//    private <T> void expectCreateFactory(final Class<T> factoryClass) {
//        EasyMock.expect(mockInvocationFactory.create()).andReturn(mockInvocationHandler);
//        mockControl.replay();
//
//        FactoryCreator fc = new FactoryCreatorImpl(mockInvocationFactory);
//        T proxy = fc.create(factoryClass);
//
//        assertThat(proxy, IsNull.notNullValue());
//        assertThat(Proxy.isProxyClass(proxy.getClass()), equalTo(true));
//        assertThat(Proxy.getInvocationHandler(proxy), IsSame.sameInstance(mockInvocationHandler));
//
//        mockControl.verify();
//    }
}
