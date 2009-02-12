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
            assertThat(e.getMessage(), equalTo("Exception is null. Expected java.lang.Exception.\nExpected: not null\n     got: null\n"));
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
                    withReceivedClass(RuntimeException.class).build()));
        }
    }
}
