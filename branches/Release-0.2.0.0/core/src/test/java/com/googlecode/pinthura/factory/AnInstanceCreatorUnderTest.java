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

public final class AnInstanceCreatorUnderTest {

    private static final String STRING_INSTANCE = "String instance!";
    private static final int INTEGER_INSTANCE   = 100;

    private final IMocksControl mockControl = EasyMock.createControl();
    private FilterLink<MethodParam, Object> mockFilterLink;
    private MethodParam mockMethodParam;
    private InstanceCreator instanceCreator;

    @SuppressWarnings({ "unchecked" })
    @Before
    public void setup() {
        mockMethodParam = mockControl.createMock(MethodParam.class);
        mockFilterLink = mockControl.createMock(FilterLink.class);

        instanceCreator = new InstanceCreatorImpl(mockFilterLink);
    }

    @Test
    public void shouldCreateAnInstanceGivenAMethodParam() {
        expectInstanceCreation(STRING_INSTANCE);
    }

    @Test
    public void shouldCreateAnotherInstanceGivenAnotherMethodParam() {
        expectInstanceCreation(INTEGER_INSTANCE);
    }

    private void expectInstanceCreation(final Object createdInstance) {
        EasyMock.expect(mockFilterLink.filter(mockMethodParam)).andReturn(createdInstance);
        mockControl.replay();

        Object instance = instanceCreator.createInstance(mockMethodParam);
        assertThat(instance, equalTo(createdInstance));

        mockControl.verify();
    }
}
