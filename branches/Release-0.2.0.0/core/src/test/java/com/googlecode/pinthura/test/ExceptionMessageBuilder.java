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

/**
 * Builds the error message for a given type of error.
 */
public final class ExceptionMessageBuilder {

    private ExceptionMessageBuilderType type;
    private String message = "";

    public ExceptionMessageBuilderForClassType withExpectedClass(final Class<?> expectedClass) {
        type = new ExceptionMessageBuilderForClass(this, expectedClass);
        return (ExceptionMessageBuilderForClass) type;
    }

    public ExceptionMessageBuilderForStringsType withExpectedString(final String expectedString) {
        type = new ExceptionMessageBuilderForStrings(this, expectedString);
        return (ExceptionMessageBuilderForStrings) type;
    }

    public ExceptionMessageBuilderForObjectType withExpectedObject(final Object expectedObject) {
        type = new ExceptionMessageBuilderForObject(this, expectedObject);
        return (ExceptionMessageBuilderForObjectType) type;
    }

    public ExceptionMessageBuilder havingMessage(final String message) {
        this.message = message;
        return this;
    }

    public String build() {
        if (type != null) {
            return message + type.build();
        }

        throw new IllegalStateException("ExceptionMessageBuilder has not been correctly configure.");
    }

    /**
     * All  builder types implement this.
     */
    private interface ExceptionMessageBuilderType {
        String build();
    }

    public interface ExceptionMessageBuilderForClassType {
        ExceptionMessageBuilder andReceivedClass(Class<?> receivedClass);
    }

    /**
     * Each message builder implements 2 interfaces. One for the type of class it processes and the other for
     * <code>ExceptionMessageBuilderType</code>. This prevents external classes accessing the {@link ExceptionMessageBuilderType#build()}
     * method. This reduces confusion on the classes using this api and in abstraction where it only exposes what is needed to external classes.  
     */
    private static final class ExceptionMessageBuilderForClass implements ExceptionMessageBuilderForClassType, ExceptionMessageBuilderType {

        private final ExceptionMessageBuilder parent;
        private Class<?> receivedClass;
        private final Class<?> expectedClass;

        private ExceptionMessageBuilderForClass(final ExceptionMessageBuilder parent, final Class<?> expectedClass) {
            this.parent = parent;
            this.expectedClass = expectedClass;
        }

        public ExceptionMessageBuilder andReceivedClass(final Class<?> receivedClass) {
            this.receivedClass = receivedClass;
            return parent;
        }

        public String build() {
            return new StringBuilder().
                    append("\nExpected: <").append(expectedClass).append(">\n     ").
                    append("got: <").append(receivedClass).append(">\n").toString();

        }
    }

    public interface ExceptionMessageBuilderForStringsType {
        ExceptionMessageBuilder andReceivedString(String receivedObject);
    }

    private static final class ExceptionMessageBuilderForStrings implements ExceptionMessageBuilderForStringsType, ExceptionMessageBuilderType {

        private final ExceptionMessageBuilder parent;
        private final String expectedObject;
        private String receivedObject;

        private ExceptionMessageBuilderForStrings(final ExceptionMessageBuilder parent, final String expectedObject) {
            this.parent = parent;
            this.expectedObject = expectedObject;
        }

        public ExceptionMessageBuilder andReceivedString(final String receivedObject) {
            this.receivedObject = receivedObject;
            return parent;
        }

        public String build() {
            return new StringBuilder().
                    append("\nExpected: \"").append(expectedObject).append("\"\n     ").
                    append("got: \"").append(receivedObject).append("\"\n").toString();
        }

    }

    public interface ExceptionMessageBuilderForObjectType {
        ExceptionMessageBuilder andReceivedObject(Object receivedObject);
    }

    private static final class ExceptionMessageBuilderForObject implements ExceptionMessageBuilderForObjectType, ExceptionMessageBuilderType {

        private final ExceptionMessageBuilder parent;
        private final Object expectedObject;
        private Object receivedObject;

        private ExceptionMessageBuilderForObject(final ExceptionMessageBuilder parent, final Object expectedObject) {
            this.parent = parent;
            this.expectedObject = expectedObject;
        }

        public ExceptionMessageBuilder andReceivedObject(final Object receivedObject) {
            this.receivedObject = receivedObject;
            return parent;
        }

        public String build() {
            return new StringBuilder().
                    append("\nExpected: ").append(expectedObject).append("\n     ").
                    append("got: ").append(receivedObject).append("\n").toString();
        }
    }
}
