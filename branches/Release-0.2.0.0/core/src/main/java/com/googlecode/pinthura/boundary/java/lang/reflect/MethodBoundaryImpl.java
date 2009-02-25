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

import com.googlecode.pinthura.boundary.java.lang.ClassBoundary;
import com.googlecode.pinthura.boundary.java.lang.ClassBoundaryImpl;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public final class MethodBoundaryImpl implements MethodBoundary {

    private final Method method;

    public MethodBoundaryImpl(final Method method) {
        this.method = method;
    }

    @SuppressWarnings("unchecked")
    public ClassBoundary<?> getReturnType() {
        return new ClassBoundaryImpl(method.getReturnType());
    }

    public String getName() {
        return method.getName();
    }

    public Method getMethod() {
        return method;
    }

    public Object invoke(final Object obj, final Object... args) {
        try {
            return method.invoke(obj, args);
        } catch (Exception e) {
            throw new MethodBoundaryException(e);
        }
    }

    @SuppressWarnings("unchecked")
    public ClassBoundary<?>[] getParameterTypes() {
        List<ClassBoundary<?>> classList = new ArrayList<ClassBoundary<?>>();
        for (Class<?> clazz : method.getParameterTypes()) {
            classList.add(new ClassBoundaryImpl(clazz));
        }

        return classList.toArray(new ClassBoundary[classList.size()]);
    }

    public <T extends Annotation> T getAnnotation(final ClassBoundary<T> annotationClass) {
        return method.getAnnotation(annotationClass.getClazz());
    }

    @Override
    public boolean equals(final Object o) {
        return o != null && o instanceof MethodBoundary && method.equals(((MethodBoundary) o).getMethod());

    }

    @Override
    public int hashCode() {
        return method.hashCode();
    }
}
