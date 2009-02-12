package com.googlecode.pinthura.test;

public final class ExceptionMessageBuilder {
    private Class<?> expectedClass;
    private Class<?> receivedClass;

    public ExceptionMessageBuilder withExpectedClass(final Class<?> expectedClass) {
        this.expectedClass = expectedClass;
        return this;
    }

    public ExceptionMessageBuilder withReceivedClass(final Class<?> receivedClass) {
        this.receivedClass = receivedClass;
        return this;
    }

    public String build() {
        if (expectedClass != null && receivedClass != null) {
            return new StringBuilder().
                    append("\nExpected: <").append(expectedClass).append(">\n").append("     ").
                    append("got: <").append(receivedClass).append(">\n").toString();
        }

        return "";
    }
}
