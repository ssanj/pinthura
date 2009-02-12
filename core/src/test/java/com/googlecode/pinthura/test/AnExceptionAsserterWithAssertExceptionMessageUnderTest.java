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
import com.googlecode.pinthura.util.builder.RandomDataCreatorBuilder;
import com.googlecode.pinthura.util.RandomDataCreator;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;
import org.junit.Before;
import org.junit.Test;

public final class AnExceptionAsserterWithAssertExceptionMessageUnderTest {

    private ExceptionAsserter asserter;
    private ExceptionMessageBuilder exceptionMessageBuilder;
    private RandomDataCreator randomDataCreator;

    @Before
    public void setup() {
        asserter = new ExceptionAsserterImpl();
        exceptionMessageBuilder = new ExceptionMessageBuilder();
        randomDataCreator = new RandomDataCreatorBuilder().build();
    }

    @SuppressWarnings({"ThrowableInstanceNeverThrown"})
    @SuppressionReason(SuppressionReason.Reason.TEST_VALUE)
    @Test
    public void shouldPassWhenTheExceptionMessageIsCorrect() {
        String randomMessage = randomDataCreator.createFileName(15);
        asserter.assertExceptionMessage(new RuntimeException(randomMessage), randomMessage);
    }

    @SuppressWarnings({"ThrowableInstanceNeverThrown"})
    @SuppressionReason(SuppressionReason.Reason.TEST_VALUE)    
    @Test
    public void shouldFailWhenTheMessageIsIncorrect() {
        String randomMessage1 = randomDataCreator.createString(10);
        String randomMessage2 = randomDataCreator.createString(12);
        try {
            asserter.assertExceptionMessage(new NullPointerException(randomMessage1), randomMessage2);
        } catch (AssertionError e) {
            assertThat(e.getMessage(),
                    equalTo(exceptionMessageBuilder.withExpectedString(randomMessage2).andReceivedString(randomMessage1).build()));
        }
    }
}
