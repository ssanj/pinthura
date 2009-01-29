package com.googlecode.pinthura.reflection;

public final class FieldFinderException extends RuntimeException {

    private static final long serialVersionUID = -9052833367536407444L;

    public FieldFinderException(final String message) {
        super(message);
    }

    public FieldFinderException(final Throwable cause) {
        super(cause);
    }


    public FieldFinderException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
