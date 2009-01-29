package com.googlecode.pinthura.boundary.java.lang.reflect;

public final class FieldBoundaryException extends RuntimeException {

    private static final long serialVersionUID = 8552307756879367483L;

    public FieldBoundaryException(final String message) {
        super(message);
    }

    public FieldBoundaryException(final Throwable cause) {
        super(cause);
    }


    public FieldBoundaryException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
