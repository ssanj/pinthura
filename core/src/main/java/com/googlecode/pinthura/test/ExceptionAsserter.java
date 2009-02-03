package com.googlecode.pinthura.test;

public interface ExceptionAsserter {

    void assertExceptionMessage(final Throwable exception, final String message);

    <EX> void assertValidException(final Throwable exception, final Class<EX> exceptionClass);

    <EX, NEX> void runAndAssertException(final Class<EX> expectedExceptionClass, final Class<NEX> expectedNestedExceptionClass,
                                                 final String message, final ExceptionAsserterImpl.Exceptional ex);

    <EX, NEX> void runAndAssertException(final Class<EX> expectedExceptionClass, final Class<NEX> expectedNestedExceptionClass,
                                                 final ExceptionAsserterImpl.Exceptional ex);

    <EX> void runAndAssertException(final Class<EX> expectedExceptionClass, final ExceptionAsserterImpl.Exceptional ex);

    <EX> void runAndAssertException(final Class<EX> expectedExceptionClass, final String message,
                                            final ExceptionAsserterImpl.Exceptional ex);

    interface Exceptional {

        void run();
    }

    final class NullException extends RuntimeException {

        private static final long serialVersionUID = -2757999455436180171L;
    }

}
