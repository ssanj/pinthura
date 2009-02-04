package com.googlecode.pinthura.test;

public interface ExceptionAsserter {

    void assertExceptionMessage(final Throwable exception, final String message);

    <EX> void assertValidException(final Throwable exception, final Class<EX> exceptionClass);

    <EX, NEX> void runAndAssertException(final Class<EX> expectedExceptionClass, final Class<NEX> expectedNestedExceptionClass,
                                                 final String message, final Exceptional ex);

    <EX, NEX> void runAndAssertException(final Class<EX> expectedExceptionClass, final Class<NEX> expectedNestedExceptionClass,
                                                 final Exceptional ex);

    <EX> void runAndAssertException(final Class<EX> expectedExceptionClass, final Exceptional ex);

    <EX> void runAndAssertException(final Class<EX> expectedExceptionClass, final String message,
                                            final Exceptional ex);

}
