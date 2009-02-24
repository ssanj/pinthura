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

/**
 * Facilitates the searching for an <code>Annotation</code> given the <code>Annotation</code> type and the method on which the
 * <code>Annotation</code> resides.
 */
public interface AnnotationFinder {

    /**
     * Returns an <code>Annotation</code> on a method, when given the type of the <code>Annotation</code>.
     * 
     * @param method The method on which to search for the applied annotation.
     * @param annotationClass The <code>Class</code> of the <code>Annotation</code>.
     * @param <T> The type of the <code>Annotation</code>.
     * @throws AnnotationNotFoundException If the <code>Annotation</code> requested can't be found on the method supplied.
     * @return The <code>Annotation</code> instance or a  <code>AnnotationNotFoundException</code> if the <code>Annotation</code> could not
     * be found.
     */
    <T extends Annotation> T find(MethodBoundary method, ClassBoundary<T> annotationClass) throws AnnotationNotFoundException;
}
