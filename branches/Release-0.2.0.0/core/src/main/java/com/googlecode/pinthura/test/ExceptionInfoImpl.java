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

public final class ExceptionInfoImpl implements ExceptionInfo {

    private final Class<? extends Throwable> exceptionClass;
    private final String message;
    private final ExceptionInfo nestedException;

    public ExceptionInfoImpl(final Class<? extends Throwable> exceptionClass, final String message, final ExceptionInfo nestedException) {
        this.exceptionClass = exceptionClass;
        this.message = message;
        this.nestedException = nestedException;
    }

    public ExceptionInfoImpl(final Class<? extends Throwable> exceptionClass) {
        this.exceptionClass = exceptionClass;
        message = NO_MESSAGE;
        nestedException = NO_EXCEPTION;
    }

    public ExceptionInfoImpl(final Class<? extends Throwable> exceptionClass, final String message) {
        this.exceptionClass = exceptionClass;
        this.message = message;
        nestedException = NO_EXCEPTION;
    }

    public ExceptionInfoImpl(final Class<? extends Throwable> exceptionClass, final ExceptionInfo nestedException) {
        this.exceptionClass = exceptionClass;
        message = NO_MESSAGE;
        this.nestedException = nestedException;
    }

    public boolean hasNestedException() {
        return nestedException != NO_EXCEPTION;
    }

    public boolean hasMessage() {
        return !message.equals(NO_MESSAGE);
    }

    public String getExceptionClassName() {
        return exceptionClass.getName();
    }

    public Class<? extends Throwable> getExceptionClass() {
        return exceptionClass;
    }

    public String getMessage() {
        return message;
    }

    public ExceptionInfo getNestedException() {
        return nestedException;
    }

}
