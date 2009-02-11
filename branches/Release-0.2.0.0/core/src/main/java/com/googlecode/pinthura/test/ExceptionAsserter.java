package com.googlecode.pinthura.test;

public interface ExceptionAsserter {

    /**
     * Asserts that message of the supplied <code>Exception</code> is the same as the message supplied. If not throws
     * an <code>AssertionError</code>.
     * @param exception The <code>Exception</code>.
     * @param message The expected message.
     * @throws AssertionError if the message of the supplied <code>Exception</code> is not the same as the message supplied. 
     */    
    void assertExceptionMessage(Throwable exception, String message) throws AssertionError;

    /**
     * Asserts that the <code>Exception</code> provided is of the type of <code>Class</code> provided. If not throws
     * an <code>AssertionError</code>.
     * @param exception The <code>Exception</code> provided.
     * @param exceptionClass The <code>Class</code> of the expected <code>Exception</code>
     * @param <EX> The type of the <code>Exception</code>.
     * @throws AssertionError  If the <code>Exception</code> provided is not of the type of <code>Class</code> provided.
     */    
    <EX> void assertValidException(Throwable exception, Class<EX> exceptionClass) throws AssertionError;

    /**
     * Runs an <code>Exceptional</code> piece of code and asserts properties about a thrown <code>Exception</code>. If
     * the assertions fail or the <code>Exceptional</code> does not throw an <code>Exception</code> an
     * <code>AssertionError</code> is thrown.
     * @param exceptionInfo Information describing the exception.
     * @param ex The <code>Exceptional</code> to run.
     * @throws AssertionError If an assertion fails or the <code>Exceptional</code> code does not throw an <code>Exception</code>.
     */
    void runAndAssertException(ExceptionInfo exceptionInfo, Exceptional ex) throws AssertionError;
}
