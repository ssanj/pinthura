package com.googlecode.pinthura.test;

import com.googlecode.pinthura.annotation.SuppressionReason;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

//TODO: Clean this up.
public final class Asserter {

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
    public static <EX, NEX> void assertException(final Class<EX> exceptionClass, final Class<NEX> nestedExceptionClass, final String message,
                                      final Exceptional ex) {
        try {
            ex.run();
            fail("Expected [" + exceptionClass + "] was not thrown.");
        } catch (Exception e) {
            assertValidException(e, exceptionClass);

            if (expectsNestedException(nestedExceptionClass)) {
                assertValidException(e.getCause(), nestedExceptionClass);

                if (hasMessage(message)) {
                    assertExceptionMessage(e.getCause(), message);
                }                
            }
        }
    }

    public static <EX, NEX> void assertException(final Class<EX> exceptionClass, final Class<NEX> nestedExceptionClass,
                                                 final Exceptional ex) {
        assertException(exceptionClass,  nestedExceptionClass, NO_MESSAGE, ex);
    }

    public static <EX> void assertException(final Class<EX> exceptionClass, final Exceptional ex) {
        assertException(exceptionClass,  NullException.class, NO_MESSAGE, ex);
    }

    public static <EX> void assertException(final Class<EX> exceptionClass, final String message, final Exceptional ex) {
        assertException(exceptionClass,  NullException.class, message, ex);
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
