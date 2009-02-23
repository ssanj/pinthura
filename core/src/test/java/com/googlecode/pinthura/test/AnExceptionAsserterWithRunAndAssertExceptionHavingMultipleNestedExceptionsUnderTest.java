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
import com.googlecode.pinthura.injection.MockInjectionException;
import com.googlecode.pinthura.processer.CouldNotProcessInputException;
import com.googlecode.pinthura.util.RandomDataCreator;
import com.googlecode.pinthura.util.builder.RandomDataCreatorBuilder;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.Test;

@SuppressWarnings("ThrowableInstanceNeverThrown")
@SuppressionReason(SuppressionReason.Reason.TEST_VALUE)
public final class AnExceptionAsserterWithRunAndAssertExceptionHavingMultipleNestedExceptionsUnderTest {

    private ExceptionAsserter exceptionAsserter;
    private RandomDataCreator randomDataCreator;
    private ExceptionMessageBuilder exceptionMessageBuilder;

    @Before
    public void setup() {
        exceptionAsserter = new ExceptionAsserterImpl();
        randomDataCreator = new RandomDataCreatorBuilder().build();
        exceptionMessageBuilder = new ExceptionMessageBuilder();
    }

    @Test
    public void shouldPassWhenExceptionsAndMessagesAreValid() {
        final String randomMessage1 = randomDataCreator.createString(10);
        final String randomMessage2 = randomDataCreator.createString(14);
        final String randomMessage3 = randomDataCreator.createString(12);
        exceptionAsserter.runAndAssertException(new ExceptionInfoImpl(RuntimeException.class, randomMessage1,
                                                new ExceptionInfoImpl(CouldNotProcessInputException.class, randomMessage2,
                                                        new ExceptionInfoImpl(MockInjectionException.class, randomMessage3))),
               new Exceptional() {
           @Override
           public void run() {
               throw new RuntimeException(randomMessage1,
                       new CouldNotProcessInputException(randomMessage2,
                               new MockInjectionException(randomMessage3)));
           }
       });
    }

    @Test
    public void shouldPassWhenExceptionsAreValid() {
        exceptionAsserter.runAndAssertException(new ExceptionInfoImpl(RuntimeException.class,
                new ExceptionInfoImpl(CouldNotProcessInputException.class, new ExceptionInfoImpl(IllegalAccessException.class))),
                new Exceptional() { @Override
            public void run() { throw new RuntimeException(new CouldNotProcessInputException(new IllegalAccessException())); }});
    }
    
    @Test
    public void shouldFailWhenMessagesAreDifferent() {
        final String randomMessage1 = randomDataCreator.createString(20);
        final String randomMessage2 = randomDataCreator.createString(4);
        final String randomMessage3 = randomDataCreator.createString(7);
        String message4 = randomDataCreator.createString(9);
        final String message4Error = message4 + "Error";
        try {
            exceptionAsserter.runAndAssertException(new ExceptionInfoImpl(CouldNotProcessInputException.class, randomMessage1,
                                                    new ExceptionInfoImpl(MockInjectionException.class, randomMessage2,
                                                            new ExceptionInfoImpl(RuntimeException.class, randomMessage3,
                                                                    new ExceptionInfoImpl(IllegalArgumentException.class, message4)))),
                   new Exceptional() {
               @Override
               public void run() {
                   throw new CouldNotProcessInputException(randomMessage1,
                           new MockInjectionException(randomMessage2,
                                   new RuntimeException(randomMessage3,
                                           new IllegalArgumentException(message4Error))));
               }
           });
            fail("Expected AssertionError.");
        } catch (AssertionError ae) {
            assertThat(ae.getMessage(),
                    equalTo(exceptionMessageBuilder.withExpectedString(message4).andReceivedString(message4Error).build()));
        }
    }

    @Test
    public void shouldFailWhenExpectedExceptionsAreNotThrown() {
        try {
            exceptionAsserter.runAndAssertException(new ExceptionInfoImpl(RuntimeException.class,
                    new ExceptionInfoImpl(CouldNotProcessInputException.class, new ExceptionInfoImpl(IllegalAccessException.class,
                    new ExceptionInfoImpl(IllegalArgumentException.class, new ExceptionInfoImpl(IndexOutOfBoundsException.class))))),
                    new Exceptional() { @Override
                public void run() { throw new RuntimeException(new CouldNotProcessInputException(new IllegalAccessException())); }});
            fail("Expected AssertionError.");
        } catch (AssertionError ae) {
            assertThat(ae.getMessage(), equalTo(exceptionMessageBuilder.
                                        havingMessage("Exception is null. Expected java.lang.IllegalArgumentException.").
                                        withExpectedObject("not null").
                                        andReceivedObject("null").
                                        build()));
        }
    }
    
    @Test
    public void shouldFailWhenExceptionTypesAreIncorrect() {
        try {
            exceptionAsserter.runAndAssertException(new ExceptionInfoImpl(RuntimeException.class,
                    new ExceptionInfoImpl(CouldNotProcessInputException.class, new ExceptionInfoImpl(IllegalArgumentException.class,
                    new ExceptionInfoImpl(IndexOutOfBoundsException.class)))),
                    new Exceptional() { @Override
                public void run() {
                        throw new RuntimeException(
                                new CouldNotProcessInputException(
                                        new IllegalArgumentException(
                                                new NullPointerException()
                                        )));
                    }});
            fail("Expected AssertionError.");
        } catch (AssertionError ae) {
            assertThat(ae.getMessage(),
                    equalTo(exceptionMessageBuilder.withExpectedClass(IndexOutOfBoundsException.class).
                                                    andReceivedClass(NullPointerException.class).
                                                    build()));            
        }
    }
}
