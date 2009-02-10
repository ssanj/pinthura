package com.googlecode.pinthura.test;

/**
 * Models the <code>Exception</code> hierarchy limited to messages for each exception. Supports nested forms of the
 * prior.
 */
public interface ExceptionInfo {
    /**
     * Message token used for when there is no message.
     */
    String NO_MESSAGE = "EXCEPTIONINFO -> NO MESSAGE";

    /**
     * Implementation of <code>NullExceptionInfo</code> used to signify the lack of an exception.
     */
    NullExceptionInfo NO_EXCEPTION = new NullExceptionInfo();

    /**
     * Returns the exception <code>Class</code>.
     * @return The exception <code>Class</code>.
     */
    Class<? extends Throwable> getExceptionClass();

    /**
     * Returns the exception message.
     * @return The exception message.
     */
    String getMessage();

    /**
     * Returns the nested exception (if any).
     * @return The nested exception.
     */
    ExceptionInfo getNestedException();

    /**
     * Return true if <code>ExceptionInfo</code> has a nested exception, false if not.
     * @return True if <code>ExceptionInfo</code> has a nested exception, false if not.
     */
    boolean hasNestedException();

    /**
     * Return true if <code>ExceptionInfo</code> has a message, false if not.
     * @return True if <code>ExceptionInfo</code> has a message, false if not.
     */
    boolean hasMessage();

    /**
     * Models a null object representing the state where an exception has not been supplied. All methods throw an
     * <code>UnsupportedOperationException</code>.
     */
    class NullExceptionInfo implements ExceptionInfo {

        @Override
        public Class<? extends Throwable> getExceptionClass() {
            throw new UnsupportedOperationException("getExceptionClass() called on NullExceptionInfo.");
        }

        @Override
        public String getMessage() {
            throw new UnsupportedOperationException("getMessage() called on NullExceptionInfo.");
        }

        @Override
        public ExceptionInfo getNestedException() {
            throw new UnsupportedOperationException("getNestedException() called on NullExceptionInfo.");
        }

        @Override
        public boolean hasNestedException() {
            throw new UnsupportedOperationException("hasNestedException() called on NullExceptionInfo.");
        }

        @Override
        public boolean hasMessage() {
            throw new UnsupportedOperationException("hasMessage() called on NullExceptionInfo.");
        }
    }
}
