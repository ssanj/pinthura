package com.googlecode.pinthura.factory.instantiator.injected;

public final class ResolvedFactorySorterException extends RuntimeException {

    private static final long serialVersionUID = -3429209765210264881L;

    public ResolvedFactorySorterException(final String message) {
        super(message);
    }

    public ResolvedFactorySorterException(final Throwable cause) {
        super(cause);
    }


    public ResolvedFactorySorterException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
