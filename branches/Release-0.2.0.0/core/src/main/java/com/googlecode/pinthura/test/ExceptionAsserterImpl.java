package com.googlecode.pinthura.test;

import com.googlecode.pinthura.annotation.SuppressionReason;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

public final class ExceptionAsserterImpl implements ExceptionAsserter {

    private static final String NO_MESSAGE = "NO MESSAGE";

    /**
     * Asserts that message of the supplied <code>Exception</code> is the same as the message supplied. If not throws
     * an <code>AssertionError</code>.
     * @param exception The <code>Exception</code>.
     * @param message The expected message.
     */
    public void assertExceptionMessage(final Throwable exception, final String message) {
        assertThat(exception.getMessage(), equalTo(message));
    }

    /**
     * Asserts that the <code>Exception</code> provided is of the type of <code>Class</code> provided. If not throws
     * an <code>AssertionError</code>.
     * @param exception The <code>Exception</code> provided.
     * @param exceptionClass The <code>Class</code> of the expected <code>Exception</code>
     * @param <EX> The type of the <code>Exception</code>. 
     */
    @SuppressWarnings({"unchecked"})
    @SuppressionReason(SuppressionReason.Reason.CANT_INFER_GENERICS)
    public <EX> void assertValidException(final Throwable exception, final Class<EX> exceptionClass) {
        assertThat("Exception is null.", exception, notNullValue());
        assertThat((Class<EX>) exception.getClass(), equalTo(exceptionClass));
    }

    @SuppressWarnings({"unchecked"})
    @SuppressionReason(SuppressionReason.Reason.CANT_INFER_GENERICS)
    public <EX, NEX> void runAndAssertException(final Class<EX> expectedExceptionClass, final Class<NEX> expectedNestedExceptionClass,
                                                 final String message, final ExceptionAsserter.Exceptional ex) {
        try {
            ex.run();
            fail("Expected [" + expectedExceptionClass.getName() + "] was not thrown.");
        } catch (Exception e) {
            assertValidException(e, expectedExceptionClass);

            if (expectsNestedException(expectedNestedExceptionClass)) {
                assertValidException(e.getCause(), expectedNestedExceptionClass);

                if (hasMessage(message)) {
                    assertExceptionMessage(e.getCause(), message);
                }                
            }
        }
    }

    public <EX, NEX> void runAndAssertException(final Class<EX> expectedExceptionClass, final Class<NEX> expectedNestedExceptionClass,
                                                 final ExceptionAsserter.Exceptional ex) {
        runAndAssertException(expectedExceptionClass,  expectedNestedExceptionClass, NO_MESSAGE, ex);
    }

    public <EX> void runAndAssertException(final Class<EX> expectedExceptionClass, final ExceptionAsserter.Exceptional ex) {
        runAndAssertException(expectedExceptionClass,  ExceptionAsserter.NullException.class, NO_MESSAGE, ex);
    }

    public <EX> void runAndAssertException(final Class<EX> expectedExceptionClass, final String message, final ExceptionAsserter.Exceptional ex) {
        runAndAssertException(expectedExceptionClass,  ExceptionAsserter.NullException.class, message, ex);
    }

    private <NEX> boolean expectsNestedException(final Class<NEX> nestedExceptionClass) {
        return nestedExceptionClass != ExceptionAsserter.NullException.class;
    }

    private boolean hasMessage(final String message) {
        return !message.equals(NO_MESSAGE);
    }
}
