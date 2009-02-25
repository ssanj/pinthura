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
package com.googlecode.pinthura.bean;

import com.googlecode.pinthura.annotation.SuppressionReason;
import com.googlecode.pinthura.boundary.java.lang.ClassBoundary;
import com.googlecode.pinthura.boundary.java.lang.ClassBoundaryFactory;
import com.googlecode.pinthura.boundary.java.lang.reflect.MethodBoundary;
import com.googlecode.pinthura.data.Authentication;
import com.googlecode.pinthura.data.Employee;
import com.googlecode.pinthura.test.ExceptionAsserter;
import com.googlecode.pinthura.test.ExceptionAsserterImpl;
import com.googlecode.pinthura.test.ExceptionInfoImpl;
import com.googlecode.pinthura.test.Exceptional;
import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import org.junit.Before;
import org.junit.Test;

//TODO: move this to an incubator.
public final class APathEvaluatorWithExceptionsUnderTest {

    private IMocksControl mockControl;
    private PathEvaluator pathEvaluator;
    private PropertyFinder mockPropertyFinder;
    private ExceptionAsserter asserter;
    private ClassBoundaryFactory mockClassBoundaryFactory;

    @Before
    public void setup() {
        mockControl = EasyMock.createControl();
        mockClassBoundaryFactory = mockControl.createMock(ClassBoundaryFactory.class);
        mockPropertyFinder = mockControl.createMock(PropertyFinder.class);

        pathEvaluator = new PathEvaluatorImpl(mockPropertyFinder, mockClassBoundaryFactory);
        asserter = new ExceptionAsserterImpl();
    }

    @SuppressWarnings({"ThrowableInstanceNeverThrown", "unchecked"})
    @SuppressionReason({SuppressionReason.Reason.TEST_VALUE, SuppressionReason.Reason.CANT_INFER_GENERICS_ON_MOCKS})
    @Test
    public void shouldThrowAnAPropertyFinderExceptionIfThePropertyIsNotFound() throws NoSuchMethodException {
        ClassBoundary mockEmployeeClassBoundary = mockControl.createMock(ClassBoundary.class);
        EasyMock.expect(mockClassBoundaryFactory.create(Employee.class)).andReturn(mockEmployeeClassBoundary);
        MethodBoundary mockAuthenticationMethodBoundary = mockControl.createMock(MethodBoundary.class);
        EasyMock.expect(mockPropertyFinder.findMethodFor("authentication", mockEmployeeClassBoundary)).andReturn(mockAuthenticationMethodBoundary);
        EasyMock.expect(mockAuthenticationMethodBoundary.invoke(EasyMock.isA(Employee.class))).andReturn(new Authentication());

        ClassBoundary mockAuthenticationClassBoundary = mockControl.createMock(ClassBoundary.class);
        EasyMock.expect(mockClassBoundaryFactory.create(Authentication.class)).andReturn(mockAuthenticationClassBoundary);
        EasyMock.expect(mockPropertyFinder.findMethodFor("boohoo", mockAuthenticationClassBoundary)).andThrow(new PropertyFinderException("test"));
        mockControl.replay();

        asserter.runAndAssertException(new ExceptionInfoImpl(PathEvaluatorException.class, new ExceptionInfoImpl(PropertyFinderException.class, "test")),
                new Exceptional() {public void run() { pathEvaluator.evaluate("authentication.boohoo", new Employee()); }});
    }

    @SuppressWarnings({"ThrowableInstanceNeverThrown", "unchecked"})
    @SuppressionReason({SuppressionReason.Reason.TEST_VALUE, SuppressionReason.Reason.CANT_INFER_GENERICS_ON_MOCKS})
    @Test
    public void shouldThrowExceptionsUnchanged() {
        ClassBoundary mockAuthenticationClassBoundary = mockControl.createMock(ClassBoundary.class);
        EasyMock.expect(mockClassBoundaryFactory.create(Authentication.class)).andReturn(mockAuthenticationClassBoundary);
        EasyMock.expect(mockPropertyFinder.findMethodFor("boohoo", mockAuthenticationClassBoundary)).andThrow(new NullPointerException());
        mockControl.replay();

        asserter.runAndAssertException(new ExceptionInfoImpl(PathEvaluatorException.class,
                                        new ExceptionInfoImpl(NullPointerException.class)),
                new Exceptional() {public void run() { pathEvaluator.evaluate("boohoo", new Authentication()); }});
    }

}
