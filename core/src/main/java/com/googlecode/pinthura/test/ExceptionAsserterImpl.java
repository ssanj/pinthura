package com.googlecode.pinthura.test;

import com.googlecode.pinthura.annotation.SuppressionReason;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

public final class ExceptionAsserterImpl implements ExceptionAsserter {

    public void assertExceptionMessage(final Throwable exception, final String message)  throws AssertionError {
        assertThat(exception.getMessage(), equalTo(message));
    }

    @SuppressWarnings({"unchecked"})
    @SuppressionReason(SuppressionReason.Reason.CANT_INFER_GENERICS)
    public <EX> void assertValidException(final Throwable exception, final Class<EX> expectedExceptionClass)  throws AssertionError {
        assertThat("Exception is null. Expected " + expectedExceptionClass.getName() + ".", exception, notNullValue());
        assertThat((Class<EX>) exception.getClass(), equalTo(expectedExceptionClass));
    }

    public void runAndAssertException(final ExceptionInfo exceptionInfo, final Exceptional ex) throws AssertionError {
        try {
            ex.run();
            fail("Expected [" + exceptionInfo.getExceptionClassName() + "] was not thrown.");
        } catch (Exception e) {
            ExceptionInfo currentExceptionInfo = exceptionInfo;
            Throwable throwable = e;
            assertExceptionAndMessage(currentExceptionInfo, throwable);

            while (currentExceptionInfo.hasNestedException()) {
                currentExceptionInfo = currentExceptionInfo.getNestedException();
                throwable = throwable.getCause();
                assertExceptionAndMessage(currentExceptionInfo, throwable);
            }
        }
    }

    private void assertExceptionAndMessage(final ExceptionInfo exceptionInfo, final Throwable e) {
        assertValidException(e, exceptionInfo.getExceptionClass());

        if (exceptionInfo.hasMessage()) {
            assertExceptionMessage(e, exceptionInfo.getMessage());
        }
    }
}
