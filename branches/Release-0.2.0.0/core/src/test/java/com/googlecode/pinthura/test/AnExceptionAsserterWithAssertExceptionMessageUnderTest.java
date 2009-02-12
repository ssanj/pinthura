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
        String message = "blah de blah";
        asserter.assertExceptionMessage(new RuntimeException(message), message);
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
            assertThat(e.getMessage(), equalTo(exceptionMessageBuilder.withExpectedObject(randomMessage2).withReceivedObject(randomMessage1).build()));
        }
    }
}
