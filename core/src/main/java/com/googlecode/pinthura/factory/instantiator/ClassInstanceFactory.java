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

/**
 * Creates a <code>ClassInstance</code> given various parameters.
 */
public interface ClassInstanceFactory {

    /**
     * Returns a <code>ClassInstance</code> given a <code>Class</code> and its instance.
     * @param clazz The <code>Class</code>.
     * @param instance The instance.
     * @param <T> The types of <code>Class</code> and its instance.
     * @return A <code>ClassInstance</code>.
     */
    <T> ClassInstance createClassInstance(Class<T> clazz, T instance);

    /**
     * Returns a <code>ClassInstance</code> given a <code>ClassBoundary</code> and its instance.
     * @param clazz The class.
     * @param instance The instance.
     * @param <T> The types of <code>ClassBoundary</code> and its instance.
     * @return A <code>ClassInstance</code>.
     */
    <T> ClassInstance createClassInstance(ClassBoundary<T> clazz, T instance);

    /**
     * Returns a <code>ClassInstance[]</code> of requested size.
     * @param size The size of the array.
     * @return A <code>ClassInstance[]</code> of requested size.
     */
    ClassInstance[] createClassInstanceArray(int size);
}
