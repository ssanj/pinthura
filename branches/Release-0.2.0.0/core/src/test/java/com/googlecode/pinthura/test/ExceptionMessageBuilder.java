package com.googlecode.pinthura.test;

public final class ExceptionMessageBuilder {
    private Class<?> expectedClass;
    private Class<?> receivedClass;
    private Object expectedObject;
    private Object receivedObject;

    public ExceptionMessageBuilder withExpectedClass(final Class<?> expectedClass) {
        this.expectedClass = expectedClass;
        return this;
    }

    public ExceptionMessageBuilder withReceivedClass(final Class<?> receivedClass) {
        this.receivedClass = receivedClass;
        return this;
    }

    public ExceptionMessageBuilder withExpectedObject(final Object expectedObject) {
        this.expectedObject = expectedObject;
        return this;
    }

    public ExceptionMessageBuilder withReceivedObject(final Object receivedObject) {
        this.receivedObject = receivedObject;
        return this;
    }

    public String build() {
        if (expectedClass != null && receivedClass != null) {
            return new StringBuilder().
                    append("\nExpected: <").append(expectedClass).append(">\n     ").
                    append("got: <").append(receivedClass).append(">\n").toString();
        }

        if (expectedObject != null && receivedObject != null) {
            return new StringBuilder().
                        append("\nExpected: \"").append(expectedObject).append("\"\n     ").
                        append("got: \"").append(receivedObject).append("\"\n").toString();
        }

        return "";
    }
}
