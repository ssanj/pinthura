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

/**
 * Models the <code>Exception</code> hierarchy with focus on the messages and nested exceptions for each exception.
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
     * The <code>Class</code> name of the exception <code>Class</code>.
     * @return The <code>Class</code> name of the exception <code>Class</code>.
     */
    String getExceptionClassName();

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

        @Override
        public String getExceptionClassName() {
            throw new UnsupportedOperationException("getExceptionClassName() called on NullExceptionInfo.");
        }
    }
}
