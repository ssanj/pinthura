package com.googlecode.pinthura.test;

import com.googlecode.pinthura.annotation.SuppressionReason;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;

public final class Asserter {

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
}
