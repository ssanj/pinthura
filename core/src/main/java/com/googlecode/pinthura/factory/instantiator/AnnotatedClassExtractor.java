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
package com.googlecode.pinthura.factory.instantiator;

import com.googlecode.pinthura.boundary.java.lang.ClassBoundary;
import com.googlecode.pinthura.factory.MethodParam;

/**
 * Extracts a <code>ClassBoundary</code> for a specific annotation which exists on the supplied <code>MethodParam</code>.
 */
public interface AnnotatedClassExtractor {

    /**
     * Returns a <code>ClassBoundary</code> for a specifc annotation given the <code>MethodParam</code> which contains
     * the annotation.
     * @param param The method that contains the annotation.
     * @param <T> The type of annotation.
     * @return The <code>ClassBoundary</code> for the annotation retrieved.
     * Exceptions are propagated from underlying classes.
     */
    <T> ClassBoundary<T> extract(MethodParam param);
}
