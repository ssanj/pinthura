package com.googlecode.pinthura.injection;

public final class MockInjectionException extends RuntimeException {

    private static final long serialVersionUID = 2344535921290079543L;

    public MockInjectionException(final String message) {
        super(message);
    }

    public MockInjectionException(final Throwable cause) {
        super(cause);
    }


    public MockInjectionException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
