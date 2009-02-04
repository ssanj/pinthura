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
package com.googlecode.pinthura.annotation;

import com.googlecode.pinthura.boundary.java.lang.ClassBoundary;
import com.googlecode.pinthura.boundary.java.lang.reflect.MethodBoundary;

import java.lang.annotation.Annotation;

public final class AnnotationFinderImpl implements AnnotationFinder {

    public <T extends Annotation> T find(final MethodBoundary method, final ClassBoundary<T> annotationClass)
            throws AnnotationNotFoundException {
        T annotation = method.getAnnotation(annotationClass);

        if (annotation != null) {
            return annotation;
        }

        throw new AnnotationNotFoundException();
    }
}
