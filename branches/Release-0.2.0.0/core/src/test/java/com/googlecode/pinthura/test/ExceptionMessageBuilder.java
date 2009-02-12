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

public final class ExceptionMessageBuilder {

    private ExceptionMessageBuilderForClass classBuilder;
    private ExceptionMessageBuilderForStrings stringBuilder;
    private ExceptionMessageBuilderForObject objectBuilder;

    public ExceptionMessageBuilderForClass withExpectedClass(final Class<?> expectedClass) {
        classBuilder = new ExceptionMessageBuilderForClass(this, expectedClass);
        return classBuilder;
    }

    public ExceptionMessageBuilderForStrings withExpectedString(final String expectedString) {
        stringBuilder = new ExceptionMessageBuilderForStrings(this, expectedString);
        return stringBuilder;
    }

    public ExceptionMessageBuilderForObject withExpectedObject(final Object expectedObject) {
        objectBuilder = new ExceptionMessageBuilderForObject(this, expectedObject);
        return objectBuilder;
    }

    public String build() {
        if (classBuilder != null) {
            return classBuilder.build();
        }

        if (stringBuilder != null) {
            return stringBuilder.build();
        }

        if (objectBuilder != null) {
            return objectBuilder.build();
        }

        throw new IllegalStateException("ExceptionMessageBuilder has not been correctly configure.");
    }

    public static final class ExceptionMessageBuilderForClass {

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

        private String build() {
            return new StringBuilder().
                    append("\nExpected: <").append(expectedClass).append(">\n     ").
                    append("got: <").append(receivedClass).append(">\n").toString();

        }
    }

    public static final class ExceptionMessageBuilderForStrings {

        private final ExceptionMessageBuilder parent;
        private final Object expectedObject;
        private Object receivedObject;

        private ExceptionMessageBuilderForStrings(final ExceptionMessageBuilder parent, final Object expectedObject) {
            this.parent = parent;
            this.expectedObject = expectedObject;
        }

        public ExceptionMessageBuilder andReceivedString(final Object receivedObject) {
            this.receivedObject = receivedObject;
            return parent;
        }

        private String build() {
            return new StringBuilder().
                    append("\nExpected: \"").append(expectedObject).append("\"\n     ").
                    append("got: \"").append(receivedObject).append("\"\n").toString();
        }

    }

    public static final class ExceptionMessageBuilderForObject {

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

        private String build() {
            return new StringBuilder().
                    append("\nExpected: ").append(expectedObject).append("\n     ").
                    append("got: ").append(receivedObject).append("\n").toString();
        }
    }
}
