package com.googlecode.pinthura.reflection;

public final class FieldSetterException extends RuntimeException {

    private static final long serialVersionUID = -4779017357034796192L;

    public FieldSetterException(final String message) {
        super(message);
    }

    public FieldSetterException(final Throwable cause) {
        super(cause);
    }


    public FieldSetterException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
