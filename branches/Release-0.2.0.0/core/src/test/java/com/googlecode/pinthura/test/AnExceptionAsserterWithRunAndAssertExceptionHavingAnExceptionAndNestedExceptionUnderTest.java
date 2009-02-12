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

import com.googlecode.pinthura.bean.PathEvaluatorException;
import com.googlecode.pinthura.bean.PropertyFinderException;
import com.googlecode.pinthura.annotation.SuppressionReason;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.Test;

import java.io.FileNotFoundException;

@SuppressWarnings({"ThrowableInstanceNeverThrown"})
@SuppressionReason(SuppressionReason.Reason.TEST_VALUE)
public final class AnExceptionAsserterWithRunAndAssertExceptionHavingAnExceptionAndNestedExceptionUnderTest {

    private ExceptionAsserter asserter;
    private ExceptionMessageBuilder exceptionMessageBuilder;

    @Before
    public void setup() {
        asserter = new ExceptionAsserterImpl();
        exceptionMessageBuilder = new ExceptionMessageBuilder();
    }

    @Test
    public void shouldPassWhenExceptionsAreValid() {
        asserter.runAndAssertException(new ExceptionInfoImpl(PathEvaluatorException.class, new ExceptionInfoImpl(ArrayIndexOutOfBoundsException.class)),
                new Exceptional() { @Override public void run() {
                    throw new PathEvaluatorException(new ArrayIndexOutOfBoundsException());
                }
        });
    }

    @Test
    public void shouldFailForAnInvalidException() {
        try {
            asserter.runAndAssertException(new ExceptionInfoImpl(ArrayIndexOutOfBoundsException.class, new ExceptionInfoImpl(PathEvaluatorException.class)),
                    new Exceptional() { @Override public void run() {
                        throw new PathEvaluatorException(new ArrayIndexOutOfBoundsException());
                    }
            });
            fail("Expected AssertionError.");
        } catch (AssertionError e) {
            assertThat(e.getMessage(),
                    equalTo(exceptionMessageBuilder.withExpectedClass(ArrayIndexOutOfBoundsException.class).
                                                    andReceivedClass(PathEvaluatorException.class).
                                                    build()));
        }
    }

    @Test
    public void shouldFailForAnInvalidNestedException() {
        try {
            asserter.runAndAssertException(new ExceptionInfoImpl(PropertyFinderException.class, new ExceptionInfoImpl(NullPointerException.class)),
                    new Exceptional() { @Override public void run() {
                        throw new PropertyFinderException(new FileNotFoundException());
                    }
            });
            fail("Expected AssertionError.");
        } catch (AssertionError e) {
            assertThat(e.getMessage(),
                    equalTo(exceptionMessageBuilder.withExpectedClass(NullPointerException.class).
                                                    andReceivedClass(FileNotFoundException.class).
                                                    build()));
        }
    }

    @Test
    public void shouldFailIfAnExceptionIsNotThrown() {
        try {
            asserter.runAndAssertException(new ExceptionInfoImpl(PropertyFinderException.class, new ExceptionInfoImpl(NullPointerException.class)),
                    new Exceptional() { @Override public void run() { /*do nothing.*/ }});
            fail("Expected AssertionError.");
        } catch (AssertionError e) {
            assertThat(e.getMessage(), equalTo("Expected [com.googlecode.pinthura.bean.PropertyFinderException] was not thrown."));
        }
    }
}
