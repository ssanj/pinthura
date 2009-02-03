package com.googlecode.pinthura.test;

import com.googlecode.pinthura.annotation.SuppressionReason;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;
import org.junit.Before;
import org.junit.Test;

public final class AnExceptionAsserterWithAssertExceptionMessageUnderTest {

    private ExceptionAsserter asserter;

    @Before
    public void setup() {
        asserter = new ExceptionAsserterImpl();
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
        try {
            asserter.assertExceptionMessage(new NullPointerException("It is null amigo"), "all is good");
        } catch (AssertionError e) {
            assertThat(e.getMessage(), equalTo("\nExpected: \"all is good\"\n     got: \"It is null amigo\"\n"));
        }
    }
}
