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

import com.googlecode.pinthura.annotation.SuppressionReason;
import com.googlecode.pinthura.boundary.java.lang.ClassBoundary;
import com.googlecode.pinthura.boundary.java.lang.ClassBoundaryImpl;
import com.googlecode.pinthura.data.UrlBoundaryImpl;
import com.googlecode.pinthura.filter.FilterLink;
import com.googlecode.pinthura.filter.MatchNotFoundException;
import com.googlecode.pinthura.test.ExceptionAsserter;
import com.googlecode.pinthura.test.ExceptionAsserterImpl;
import com.googlecode.pinthura.test.Exceptional;
import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.fail;
import static org.junit.Assert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

public final class AnInstanceCreatorWithErrorsUnderTest {

    private final IMocksControl mockControl = EasyMock.createControl();
    private FilterLink<MethodParam, Object> mockFilterLink;
    private MethodParam mockMethodParam;
    private InstanceCreator instanceCreator;
    private ExceptionAsserter exceptionAsserter;

    @SuppressWarnings("unchecked")
    @SuppressionReason(SuppressionReason.Reason.CANT_INFER_GENERICS_ON_MOCKS)
    @Before
    public void setup() {
        mockMethodParam = mockControl.createMock(MethodParam.class);
        mockFilterLink = mockControl.createMock(FilterLink.class);
        exceptionAsserter = new ExceptionAsserterImpl();

        instanceCreator = new InstanceCreatorImpl(mockFilterLink);
    }

    @SuppressWarnings({ "ThrowableInstanceNeverThrown", "unchecked" })
    @Test
    public void shouldThrownANewExceptionIfAMatchNotFoundExceptionIsThrown() {
        ClassBoundary classBoundary = new ClassBoundaryImpl(UrlBoundaryImpl.class);
        EasyMock.expect(mockFilterLink.filter(mockMethodParam)).andThrow(new MatchNotFoundException());
        EasyMock.expect(mockMethodParam.getReturnType()).andReturn(classBoundary);
        mockControl.replay();

        //TODO: can't make this use ExceptionAsserter until we can match the message on the expected exception.
        try {
            instanceCreator.createInstance(mockMethodParam);
            fail();
        } catch (InstanceCreationException e) {
            assertThat(e.getCause().getClass() == MatchNotFoundException.class, equalTo(true));
            assertThat(e.getMessage(),
                    equalTo("Could not create instance of ClassBoundaryImpl[class com.googlecode.pinthura.data.UrlBoundaryImpl]"));
        }
    }

    @SuppressWarnings("ThrowableInstanceNeverThrown")
    @Test
    public void shouldRethrowOtherExceptionsDirectly() {
        EasyMock.expect(mockFilterLink.filter(mockMethodParam)).andThrow(new IllegalArgumentException());
        mockControl.replay();
        
        exceptionAsserter.runAndAssertException(IllegalArgumentException.class, new Exceptional() {
            @Override public void run() { instanceCreator.createInstance(mockMethodParam) ;}});
    }
}
