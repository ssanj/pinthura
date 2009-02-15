/*
 * Copyright 2008 Sanjiv Sahayam
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
     * Asserts that the <code>Exception</code> provided is not null and is of the type of <code>Class</code> provided. If not throws
     * an <code>AssertionError</code>.
     * @param exception The <code>Exception</code> provided.
     * @param exceptionClass The <code>Class</code> of the expected <code>Exception</code>
     * @param <EX> The type of the <code>Exception</code>.
     * @throws AssertionError  If the <code>Exception</code> provided is null or is not of the type of <code>Class</code> provided.
     */    
    <EX> void assertValidException(Throwable exception, Class<EX> exceptionClass) throws AssertionError;

    /**
     * Runs an <code>Exceptional</code> implementation and asserts properties about a thrown <code>Exception</code>. If
     * the assertions fail or the <code>Exceptional</code> implementation does not throw an <code>Exception</code> an
     * <code>AssertionError</code> is thrown.
     * @param exceptionInfo Defines the properties that will be asserted.
     * @param ex The <code>Exceptional</code> implementation to run.
     * @throws AssertionError If an assertion fails or the <code>Exceptional</code> code does not throw an <code>Exception</code>.
     */
    void runAndAssertException(ExceptionInfo exceptionInfo, Exceptional ex) throws AssertionError;
}
