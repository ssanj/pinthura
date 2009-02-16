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
import com.googlecode.pinthura.boundary.java.lang.reflect.ConstructorBoundary;
import com.googlecode.pinthura.factory.MethodParam;

/**
 * Locates a constructor given various parameters.
 */
public interface ConstructorLocator {

    /**
     * Returns a constructor given a <code>MethodParam</code> and the name of the <code>Class</code> on which the constructor exists.
     * @param methodParam The method containing the constructor parameters.
     * @param className The name of the class on which the constructor exists.
     * @param <T> The type of the <code>Class</code> on which the constructor exists.
     * @return A constructor.
     * Exceptions are propagated from underlying classes.
     */
    <T> ConstructorBoundary<T> locate(MethodParam methodParam, String className);

    /**
     * Returns a constructor given a <code>MethodParam</code> and the class on which the constructor exists.
     * @param methodParam The method containing the constructor parameters.
     * @param clazz The class on which the constructor exists.
     * @param <T> The type of the <code>Class</code> on which the constructor exists.
     * @return A constructor.
     * Exceptions are propagated from underlying classes.
     */
    <T> ConstructorBoundary<T> locate(MethodParam methodParam, ClassBoundary<T> clazz);
}
