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
package com.googlecode.pinthura.test;

import com.googlecode.pinthura.annotation.SuppressionReason;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.Test;

public final class AnExceptionAsserterWithAssertValidExceptionUnderTest {

    private ExceptionAsserter asserter;
    private ExceptionMessageBuilder exceptionMessageBuilder;

    @Before
    public void setup() {
        asserter = new ExceptionAsserterImpl();
        exceptionMessageBuilder = new ExceptionMessageBuilder();
    }

    @SuppressWarnings("ThrowableInstanceNeverThrown")
    @SuppressionReason(SuppressionReason.Reason.TEST_VALUE)
    @Test
    public void shouldPassOnAValidException() {
        Exception exception = new ClassNotFoundException();
        asserter.assertValidException(exception, ClassNotFoundException.class);
    }

    @Test
    public void shouldFailWhenAnExceptionIsNull() {
        try {
            asserter.assertValidException(null, Exception.class);
            fail("Expected AssertionError.");
        } catch (AssertionError e) {
            assertThat(e.getMessage(), equalTo(new StringBuilder("Exception is null. Expected java.lang.Exception.").
                    append(exceptionMessageBuilder.withExpectedObject("not null").andReceivedObject("null").build()).toString()));
        }
    }
    
    @SuppressWarnings("ThrowableInstanceNeverThrown")
    @SuppressionReason(SuppressionReason.Reason.TEST_VALUE)
    @Test
    public void shouldFailWhenAnExceptionIsOfTheWrongClass() {
        try {
            asserter.assertValidException(new RuntimeException(), IllegalArgumentException.class);
        } catch (AssertionError e) {
            assertThat(e.getMessage(), equalTo(exceptionMessageBuilder.withExpectedClass(IllegalArgumentException.class).
                                                                    andReceivedClass(RuntimeException.class).
                                                                    build()));
        }
    }
}
