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

public final class APathEvaluatorWithExceptionsUnderIntTest {

    private final IMocksControl mockControl = EasyMock.createControl();
    private PathEvaluator pathEvaluator;
    private PropertyFinder mockPropertyFinder;
    private ExceptionAsserter asserter;

    @Before
    public void setup() {
        mockPropertyFinder = mockControl.createMock(PropertyFinder.class);
        pathEvaluator = new PathEvaluatorImpl(mockPropertyFinder);
        asserter = new ExceptionAsserterImpl();
    }

    @SuppressWarnings("ThrowableInstanceNeverThrown")
    @SuppressionReason(SuppressionReason.Reason.TEST_VALUE)
    @Test
    public void shouldThrowAnAPropertyFinderExceptionIfThePropertyIsNotFound() throws NoSuchMethodException {
        expectProperty("authentication", Employee.class, "getAuthentication");
        EasyMock.expect(mockPropertyFinder.findMethodFor("boohoo", Authentication.class)).andThrow(new PropertyFinderException("test"));
        mockControl.replay();

        asserter.runAndAssertException(PathEvaluatorException.class, PropertyFinderException.class, "test",
                new Exceptional() {public void run() { pathEvaluator.evaluate("authentication.boohoo", new Employee()); }});
    }

    @SuppressWarnings("ThrowableInstanceNeverThrown")
    @SuppressionReason(SuppressionReason.Reason.TEST_VALUE)
    @Test
    public void shouldThrowExceptionsUnchanged() {
        EasyMock.expect(mockPropertyFinder.findMethodFor("boohoo", Authentication.class)).andThrow(new NullPointerException());
        mockControl.replay();

        asserter.runAndAssertException(new ExceptionInfoImpl(PathEvaluatorException.class,
                                        new ExceptionInfoImpl(NullPointerException.class)),
                new Exceptional() {public void run() { pathEvaluator.evaluate("boohoo", new Authentication()); }});
    }

    private void expectProperty(final String property, final Class<?> parentClass, final String methodName) throws NoSuchMethodException {
        EasyMock.expect(mockPropertyFinder.findMethodFor(property, parentClass)).
                andReturn(parentClass.getMethod(methodName));
    }
}
