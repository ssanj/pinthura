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

import com.googlecode.pinthura.data.ShapeFactory;
import com.googlecode.pinthura.data.UrlBoundary;
import com.googlecode.pinthura.factory.MethodParam;
import com.googlecode.pinthura.factory.boundary.ConstructorBoundary;
import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import static org.hamcrest.core.IsSame.sameInstance;
import static org.junit.Assert.assertThat;
import org.junit.Before;
import org.junit.Test;

public final class AConstructorInstantiatorUnderTest {

    private final IMocksControl mockControl = EasyMock.createControl();
    private ConstructorInstantiator instantiator;
    private MethodParam mockMethodParam;
    private ConstructorBoundary mockConstructorBoundary;

    @Before
    public void setup() {
        mockMethodParam = mockControl.createMock(MethodParam.class);
        mockConstructorBoundary = mockControl.createMock(ConstructorBoundary.class);
        instantiator = new ConstructorInstantiatorImpl();
    }

    @Test
    public void shouldInstantiateAConstructorGivenItsParameters() {
        expectInstantiation(new Object[]{"testing 1 2 3"}, ShapeFactory.class);
    }

    @Test
    public void shouldInstantiateAParameterlessConstructor() {
        expectInstantiation(new Object[]{}, UrlBoundary.class);
    }

    @SuppressWarnings({ "unchecked" })
    private void expectInstantiation(final Object[] args, final Class<?> type) {
        Object mockTypeInstance = mockControl.createMock(type);
        EasyMock.expect(mockMethodParam.getArguments()).andReturn(args);
        EasyMock.expect(mockConstructorBoundary.newInstance(args)).andReturn(mockTypeInstance);
        mockControl.replay();

        Object result = instantiator.instantiate(mockConstructorBoundary, mockMethodParam);
        assertThat(result, sameInstance(mockTypeInstance));

        mockControl.verify();
    }
}
