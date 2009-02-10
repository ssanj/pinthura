package com.googlecode.pinthura.test;

import com.googlecode.pinthura.annotation.SuppressionReason;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

public final class ExceptionAsserterImpl implements ExceptionAsserter {

    /**
     * Message token used for when there is no message.
     */
    private static final String NO_MESSAGE = "NO MESSAGE";

    public void assertExceptionMessage(final Throwable exception, final String message) {
        assertThat(exception.getMessage(), equalTo(message));
    }

    @SuppressWarnings({"unchecked"})
    @SuppressionReason(SuppressionReason.Reason.CANT_INFER_GENERICS)
    public <EX> void assertValidException(final Throwable exception, final Class<EX> expectedExceptionClass) {
        assertThat("Exception is null.", exception, notNullValue());
        assertThat((Class<EX>) exception.getClass(), equalTo(expectedExceptionClass));
    }

    @SuppressWarnings({"unchecked"})
    @SuppressionReason(SuppressionReason.Reason.CANT_INFER_GENERICS)
    public <EX, NEX> void runAndAssertException(final Class<EX> expectedExceptionClass, final Class<NEX> expectedNestedExceptionClass,
                                                final String message, final Exceptional ex) throws AssertionError {
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

                return;
            }

            if (hasMessage(message)) {
                assertExceptionMessage(e, message);
            }
        }
    }

    public <EX, NEX> void runAndAssertException(final Class<EX> expectedExceptionClass, final Class<NEX> expectedNestedExceptionClass,
                                                 final Exceptional ex) throws AssertionError {
        runAndAssertException(expectedExceptionClass,  expectedNestedExceptionClass, NO_MESSAGE, ex);
    }

    public void runAndAssertException(final ExceptionInfo exceptionInfo, final Exceptional ex) throws AssertionError {
        try {
            ex.run();
            fail("Expected [" + exceptionInfo.getExceptionClassName() + "] was not thrown.");
        } catch (Exception e) {
            assertValidException(e, exceptionInfo.getExceptionClass());

//            if (expectsNestedException(expectedNestedExceptionClass)) {
//                assertValidException(e.getCause(), expectedNestedExceptionClass);
//
//                if (hasMessage(message)) {
//                    assertExceptionMessage(e.getCause(), message);
//                }
//
//                return;
//            }
            if (exceptionInfo.hasMessage()) {
                assertExceptionMessage(e, exceptionInfo.getMessage());
            }
        }
    }


    private <NEX> boolean expectsNestedException(final Class<NEX> nestedExceptionClass) {
        return nestedExceptionClass != NullException.class;
    }

    private boolean hasMessage(final String message) {
        return !message.equals(NO_MESSAGE);
    }
}
