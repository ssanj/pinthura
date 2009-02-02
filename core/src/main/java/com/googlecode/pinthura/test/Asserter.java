package com.googlecode.pinthura.test;

import com.googlecode.pinthura.annotation.SuppressionReason;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

public final class Asserter {

    private static final String NO_MESSAGE = "NO MESSAGE";

    public static <E> void assertException(final Throwable exception, final Class<E> nested, final String message) {
        assertException(exception, nested);
        assertThat(exception.getCause().getMessage(), equalTo(message));
    }

    @SuppressWarnings({"unchecked"})
    @SuppressionReason(SuppressionReason.Reason.CANT_INFER_GENERICS)
    public static <E> void assertException(final Throwable exception, final Class<E> nested) {
        assertThat("Exception is null.", exception, notNullValue());
        assertThat("Nested Exception is null.", exception.getCause(), notNullValue());
        assertThat((Class<E>) exception.getCause().getClass(), equalTo(nested));
    }

    @SuppressWarnings({"unchecked"})
    @SuppressionReason(SuppressionReason.Reason.CANT_INFER_GENERICS)
    public static <EX, NEX> void assertException(final Class<EX> exceptionClass, final Class<NEX> nestedExceptionClass, final String message,
                                      final Exceptional ex) {
        try {
            ex.run();
            fail("Expected [" + exceptionClass + "] was not thrown.");
        } catch (Exception e) {
            assertThat((Class<EX>) e.getClass(), equalTo(exceptionClass));

            if (!message.equals(NO_MESSAGE)) {
                assertException(e, nestedExceptionClass, message);
                return;
            }

            assertException(e, nestedExceptionClass);
        }
    }

    public static <EX, NEX> void assertException(final Class<EX> exceptionClass, final Class<NEX> nestedExceptionClass,
                                                 final Exceptional ex) {
        assertException(exceptionClass,  nestedExceptionClass, NO_MESSAGE, ex);
    }

    public interface Exceptional {

        void run();
    }
}
