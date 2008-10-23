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
package com.googlecode.pinthura.factory.boundary;

import java.util.ArrayList;
import java.util.List;

//TODO: sort the generics in here!
public final class ClassBoundaryImpl<T> implements ClassBoundary<T> {

    private final Class<T> clazz;

    public ClassBoundaryImpl(final Class<T> clazz) {
        this.clazz = clazz;
    }

    public String getName() {
        return clazz.getName();
    }

    public Class<T> getClazz() {
        return clazz;
    }

    @SuppressWarnings({ "unchecked" })
    public ClassBoundary<T> forName(final String className) {
        try {
            return new ClassBoundaryImpl(Class.forName(className));
        } catch (ClassNotFoundException e) {
            throw new ClassBoundaryException(e);
        }
    }

    public ConstructorBoundary<T> getConstructor(final ClassBoundary<?>[] parameterTypes) {
        List<Class<?>> classList = new ArrayList<Class<?>>();
        for (ClassBoundary<?> parameterType : parameterTypes) {
            classList.add(parameterType.getClazz());
        }

        try {
            return new ConstructorBoundaryImpl<T>(clazz.getConstructor(classList.toArray(new Class<?>[classList.size()])));
        } catch (Exception e) {
            throw new ClassBoundaryException(e);
        }
    }

    @Override
    public boolean equals(final Object object) {
        return object != null && object instanceof ClassBoundary && clazz.equals(((ClassBoundary) object).getClazz());
    }

    @Override
    public int hashCode() {
        return clazz.hashCode();
    }

    public String toString() {
        return clazz.toString();
    }
}
