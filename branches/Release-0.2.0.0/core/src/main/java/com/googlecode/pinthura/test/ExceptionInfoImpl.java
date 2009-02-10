package com.googlecode.pinthura.test;

public final class ExceptionInfoImpl implements ExceptionInfo {

    private static final NullExceptionInfo NO_EXCEPTION = new NullExceptionInfo();
    
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
        return message.equals(NO_MESSAGE);
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

    private static final class NullExceptionInfo implements ExceptionInfo {

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
    }
}
