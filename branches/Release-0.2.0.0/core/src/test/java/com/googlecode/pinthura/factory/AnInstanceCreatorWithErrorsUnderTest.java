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
import com.googlecode.pinthura.processer.ChainOfResponsibility;
import com.googlecode.pinthura.processer.CouldNotProcessInputException;
import com.googlecode.pinthura.test.ExceptionAsserter;
import com.googlecode.pinthura.test.ExceptionAsserterImpl;
import com.googlecode.pinthura.test.ExceptionInfoImpl;
import com.googlecode.pinthura.test.Exceptional;
import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import org.junit.Before;
import org.junit.Test;

public final class AnInstanceCreatorWithErrorsUnderTest {

    private final IMocksControl mockControl = EasyMock.createControl();
    private MethodParam mockMethodParam;
    private InstanceCreator instanceCreator;
    private ExceptionAsserter exceptionAsserter;
    private ChainOfResponsibility mockChainOfResponsibility;

    @SuppressWarnings("unchecked")
    @SuppressionReason(SuppressionReason.Reason.CANT_INFER_GENERICS_ON_MOCKS)
    @Before
    public void setup() {
        mockMethodParam = mockControl.createMock(MethodParam.class);
        mockChainOfResponsibility = mockControl.createMock(ChainOfResponsibility.class);
        exceptionAsserter = new ExceptionAsserterImpl();

        instanceCreator = new InstanceCreatorImpl(mockChainOfResponsibility);
    }

    @SuppressWarnings({ "ThrowableInstanceNeverThrown", "unchecked" })
    @Test
    public void shouldThrownANewExceptionIfAMatchNotFoundExceptionIsThrown() {
        ClassBoundary classBoundary = new ClassBoundaryImpl(UrlBoundaryImpl.class);
        EasyMock.expect(mockChainOfResponsibility.process(mockMethodParam)).andThrow(new CouldNotProcessInputException());
        EasyMock.expect(mockMethodParam.getReturnType()).andReturn(classBoundary);
        mockControl.replay();

        exceptionAsserter.runAndAssertException(new ExceptionInfoImpl(InstanceCreationException.class,
                "Could not create instance of ClassBoundaryImpl[class com.googlecode.pinthura.data.UrlBoundaryImpl]"),
                new Exceptional() {@Override public void run() { instanceCreator.createInstance(mockMethodParam); }});
    }

    @SuppressWarnings({"ThrowableInstanceNeverThrown", "unchecked"})
    @SuppressionReason({SuppressionReason.Reason.CANT_INFER_GENERICS_ON_MOCKS, SuppressionReason.Reason.TEST_TYPE})
    @Test
    public void shouldRethrowOtherExceptionsDirectly() {
        EasyMock.expect(mockChainOfResponsibility.process(mockMethodParam)).andThrow(new IllegalArgumentException());
        mockControl.replay();
        
        exceptionAsserter.runAndAssertException(new ExceptionInfoImpl(IllegalArgumentException.class), new Exceptional() {
            @Override public void run() { instanceCreator.createInstance(mockMethodParam) ;}});
    }
}
