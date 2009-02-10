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
        nestedException = new NullExceptionInfo();
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
        return nestedException == NO_EXCEPTION;
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
