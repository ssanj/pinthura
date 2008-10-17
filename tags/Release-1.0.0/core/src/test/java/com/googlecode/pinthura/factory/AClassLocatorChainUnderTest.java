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

import com.googlecode.pinthura.filter.FilterLink;
import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;
import org.junit.Before;
import org.junit.Test;

public final class AClassLocatorChainUnderTest {

    private final IMocksControl mockControl = EasyMock.createControl();

    private FilterLink mockFilterLink;
    private MethodParam mockMethodParam;

    @Before
    public void setup() {
        mockMethodParam = mockControl.createMock(MethodParam.class);
        mockFilterLink = mockControl.createMock(FilterLink.class);
    }

    @SuppressWarnings({ "unchecked" })
    @Test
    public void shouldDelgateToTheSuppliedFilterLink() {
        EasyMock.expect(mockFilterLink.filter(mockMethodParam)).andReturn(String.class);
        mockControl.replay();

        Class<String> aClass = (Class<String>) new ClassLocatorChain(mockFilterLink).filter(mockMethodParam);
        assertThat(aClass, equalTo(String.class));

        mockControl.verify();
    }

    @SuppressWarnings({ "unchecked" })
    @Test
    public void shouldReturnItsName() {
        assertThat(new ClassLocatorChain(mockFilterLink).getFilterName(), equalTo("Class Locator Chain"));
    }
}
