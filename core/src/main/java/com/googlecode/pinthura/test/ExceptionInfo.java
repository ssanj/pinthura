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
}
