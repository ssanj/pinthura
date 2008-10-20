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

import com.googlecode.pinthura.data.UrlBoundaryImpl;
import com.googlecode.pinthura.factory.boundary.ClassBoundary;
import com.googlecode.pinthura.factory.boundary.ClassBoundaryImpl;
import com.googlecode.pinthura.factory.locator.InstanceCreationException;
import com.googlecode.pinthura.filter.FilterLink;
import com.googlecode.pinthura.filter.MatchNotFoundException;
import static junit.framework.Assert.fail;
import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;
import org.junit.Before;
import org.junit.Test;

public final class AnInstanceCreatorWithErrorsUnderTest {

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

    @SuppressWarnings({ "ThrowableInstanceNeverThrown", "unchecked" })
    @Test
    public void shouldThrownANewExceptionIfAMatchNotFoundExceptionIsThrown() {
        ClassBoundary classBoundary = new ClassBoundaryImpl(UrlBoundaryImpl.class);
        EasyMock.expect(mockFilterLink.filter(mockMethodParam)).andThrow(new MatchNotFoundException());
        EasyMock.expect(mockMethodParam.getReturnType()).andReturn(classBoundary);
        mockControl.replay();

        try {
            instanceCreator.createInstance(mockMethodParam);
            fail();
        } catch (InstanceCreationException e) {
            assertThat(e.getCause().getClass() == MatchNotFoundException.class, equalTo(true));
            assertThat(e.getMessage(), equalTo("Could not create instance of class com.googlecode.pinthura.data.UrlBoundaryImpl"));
        }
    }

    @SuppressWarnings({ "ThrowableInstanceNeverThrown" })
    @Test (expected = IllegalArgumentException.class)
    public void shouldRethrowOtherExceptionsDirectly() {
        EasyMock.expect(mockFilterLink.filter(mockMethodParam)).andThrow(new IllegalArgumentException());
        mockControl.replay();

        instanceCreator.createInstance(mockMethodParam);
    }

}
