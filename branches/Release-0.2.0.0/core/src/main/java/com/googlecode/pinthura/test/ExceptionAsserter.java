package com.googlecode.pinthura.test;

//TODO: update to use Deux.
public interface ExceptionAsserter {

    /**
     * Asserts that message of the supplied <code>Exception</code> is the same as the message supplied. If not throws
     * an <code>AssertionError</code>.
     * @param exception The <code>Exception</code>.
     * @param message The expected message.
     */    
    void assertExceptionMessage(Throwable exception, String message);

    /**
     * Asserts that the <code>Exception</code> provided is of the type of <code>Class</code> provided. If not throws
     * an <code>AssertionError</code>.
     * @param exception The <code>Exception</code> provided.
     * @param exceptionClass The <code>Class</code> of the expected <code>Exception</code>
     * @param <EX> The type of the <code>Exception</code>.
     */    
    <EX> void assertValidException(Throwable exception, Class<EX> exceptionClass);

    /**
     * Runs an <code>Exceptional</code> piece of code and asserts properties about a thrown <code>Exception</code>. If
     * the assertions fail or the <code>Exceptional</code> does not throw an <code>Exception</code> an
     * <code>AssertionError</code> is thrown.
     * @param expectedExceptionClass The <code>Class</code> of the top-level <code>Exception</code>.
     * @param expectedNestedExceptionClass The <code>Class</code> of the nested <code>Exception</code>.
     * @param message The message of the nested <code>Exception</code>.
     * @param ex The <code>Exceptional</code> to run.
     * @param <EX> The type of top-level <code>Exception</code>.
     * @param <NEX> The type of nested <code>Exception</code>.
     * @throws AssertionError If an assertion fails or the <code>Exceptional</code> code does not throw an <code>Exception</code>.
     */    
    <EX, NEX> void runAndAssertException(Class<EX> expectedExceptionClass, Class<NEX> expectedNestedExceptionClass,
                                                 String message, Exceptional ex) throws AssertionError;

    /**
     * Runs an <code>Exceptional</code> piece of code and asserts properties about a thrown <code>Exception</code>. If
     * the assertions fail or the <code>Exceptional</code> does not throw an <code>Exception</code> an
     * <code>AssertionError</code> is thrown.
     * @param expectedExceptionClass The <code>Class</code> of the top-level <code>Exception</code>.
     * @param expectedNestedExceptionClass The <code>Class</code> of the nested <code>Exception</code>.
     * @param ex The <code>Exceptional</code> to run.
     * @param <EX> The type of top-level <code>Exception</code>.
     * @param <NEX> The type of nested <code>Exception</code>.
     * @throws AssertionError If an assertion fails or the <code>Exceptional</code> code does not throw an <code>Exception</code>.
     */    
    <EX, NEX> void runAndAssertException(Class<EX> expectedExceptionClass, Class<NEX> expectedNestedExceptionClass,
                                                 Exceptional ex) throws AssertionError;

    /**
     * Runs an <code>Exceptional</code> piece of code and asserts properties about a thrown <code>Exception</code>. If
     * the assertions fail or the <code>Exceptional</code> does not throw an <code>Exception</code> an
     * <code>AssertionError</code> is thrown.
     * @param expectedExceptionClass The <code>Class</code> of the top-level <code>Exception</code>.
     * @param ex The <code>Exceptional</code> to run.
     * @param <EX> The type of top-level <code>Exception</code>.
     * @throws AssertionError If an assertion fails or the <code>Exceptional</code> code does not throw an <code>Exception</code>.
     */    
    <EX> void runAndAssertException(Class<EX> expectedExceptionClass, Exceptional ex) throws AssertionError;

    /**
     * Runs an <code>Exceptional</code> piece of code and asserts properties about a thrown <code>Exception</code>. If
     * the assertions fail or the <code>Exceptional</code> does not throw an <code>Exception</code> an
     * <code>AssertionError</code> is thrown.
     * @param expectedExceptionClass The <code>Class</code> of the top-level <code>Exception</code>.
     * @param message The message of the nested <code>Exception</code>.
     * @param ex The <code>Exceptional</code> to run.
     * @param <EX> The type of top-level <code>Exception</code>.
     * @throws AssertionError If an assertion fails or the <code>Exceptional</code> code does not throw an <code>Exception</code>.
     */        
    <EX> void runAndAssertException(Class<EX> expectedExceptionClass, String message, Exceptional ex) throws AssertionError;
}
