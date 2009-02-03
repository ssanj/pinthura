package com.googlecode.pinthura.test;

import com.googlecode.pinthura.annotation.SuppressionReason;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

//TODO: Clean this up.
public final class ExceptionAsserter {

    private static final String NO_MESSAGE = "NO MESSAGE";

    public static void assertExceptionMessage(final Throwable exception, final String message) {
        assertThat(exception.getMessage(), equalTo(message));
    }

    @SuppressWarnings({"unchecked"})
    @SuppressionReason(SuppressionReason.Reason.CANT_INFER_GENERICS)
    public static <EX> void assertValidException(final Throwable exception, final Class<EX> exceptionClass) {
        assertThat("Exception is null.", exception, notNullValue());
        assertThat("Expected [" + exceptionClass + "] got [" + exception.getClass() + "]",
                (Class<EX>) exception.getClass(), equalTo(exceptionClass));
    }

    @SuppressWarnings({"unchecked"})
    @SuppressionReason(SuppressionReason.Reason.CANT_INFER_GENERICS)
    public static <EX, NEX> void assertException(final Class<EX> expectedExceptionClass, final Class<NEX> expectedNestedExceptionClass,
                                                 final String message, final Exceptional ex) {
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

    public static <EX, NEX> void assertException(final Class<EX> expectedExceptionClass, final Class<NEX> expectedNestedExceptionClass,
                                                 final Exceptional ex) {
        assertException(expectedExceptionClass,  expectedNestedExceptionClass, NO_MESSAGE, ex);
    }

    public static <EX> void assertException(final Class<EX> expectedExceptionClass, final Exceptional ex) {
        assertException(expectedExceptionClass,  NullException.class, NO_MESSAGE, ex);
    }

    public static <EX> void assertException(final Class<EX> expectedExceptionClass, final String message, final Exceptional ex) {
        assertException(expectedExceptionClass,  NullException.class, message, ex);
    }

    private static <NEX> boolean expectsNestedException(final Class<NEX> nestedExceptionClass) {
        return nestedExceptionClass != NullException.class;
    }

    private static boolean hasMessage(final String message) {
        return !message.equals(NO_MESSAGE);
    }

    public interface Exceptional {

        void run();
    }

    private static final class NullException extends RuntimeException {

        private static final long serialVersionUID = -2757999455436180171L;
    }
}
