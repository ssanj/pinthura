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
package com.googlecode.pinthura.boundary.java.lang.reflect;

import com.googlecode.pinthura.boundary.java.lang.reflect.ConstructorBoundaryException;

import java.lang.reflect.Constructor;

public final class ConstructorBoundaryImpl<T> implements ConstructorBoundary<T> {

    private final Constructor<T> constructor;

    public ConstructorBoundaryImpl(final Constructor<T> constructor) {
        this.constructor = constructor;
    }

    public Object newInstance(final Object[] arguments) {
        try {
            return constructor.newInstance(arguments);
        } catch (Exception e) {
            throw new ConstructorBoundaryException(e);
        }
    }

    public Constructor<T> getConstructor() {
        return constructor;
    }
}
