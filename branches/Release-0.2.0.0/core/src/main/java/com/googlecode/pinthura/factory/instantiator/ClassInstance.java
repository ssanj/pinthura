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
 * Defines a pair of consisting of a <code>ClassBoundary</code> and an instance of that <code>ClassBoundary</code>.
 */
//TODO: This class should have <T> instead of the individual methods.
public interface ClassInstance {

    /**
     * Returns the contained <code>ClassBoundary</code>.
     * @param <T> The type of <code>Class</code> within the <code>ClassBoundary</code>
     * @return The contained <code>ClassBoundary</code>.
     */
    <T> ClassBoundary<T> getClazz();

    /**
     * Returns the instance of a <code>Class</code>.
     * @param <T> The type of the instance which should be type compatible with the <code>ClassBoundary</code> 
     * @return The instance of <code>ClassBoundary</code>
     */
    <T> T getInstance();
}
