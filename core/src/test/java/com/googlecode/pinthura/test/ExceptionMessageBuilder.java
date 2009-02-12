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
    private Object expectedObject;
    private Object receivedObject;
    private ExceptionMessageBuilderForClass classBuilder;

    public ExceptionMessageBuilderForClass withExpectedClass(final Class<?> expectedClass) {
        classBuilder = new ExceptionMessageBuilderForClass(this, expectedClass);
        return classBuilder;
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
        if (classBuilder != null) {
            return classBuilder.build();
        }

        if (expectedObject != null && receivedObject != null) {
            return new StringBuilder().
                        append("\nExpected: \"").append(expectedObject).append("\"\n     ").
                        append("got: \"").append(receivedObject).append("\"\n").toString();
        }

        return "";
    }

    public static final class ExceptionMessageBuilderForClass {

        private final ExceptionMessageBuilder parent;
        private Class<?> receivedClass;
        private final Class<?> expectedClass;

        private ExceptionMessageBuilderForClass(final ExceptionMessageBuilder parent, final Class<?> expectedClass) {
            this.parent = parent;
            this.expectedClass = expectedClass;
        }

        ExceptionMessageBuilder andReceivedClass(final Class<?> receivedClass) {
            this.receivedClass = receivedClass;
            return parent;
        }

        String build() {
            return new StringBuilder().
                    append("\nExpected: <").append(expectedClass).append(">\n     ").
                    append("got: <").append(receivedClass).append(">\n").toString();

        }
    }
}
