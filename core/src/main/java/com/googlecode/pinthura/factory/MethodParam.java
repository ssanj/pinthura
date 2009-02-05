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
package com.googlecode.pinthura.factory;

import com.googlecode.pinthura.boundary.java.lang.ClassBoundary;
import com.googlecode.pinthura.boundary.java.lang.reflect.MethodBoundary;

/**
 * Encapsulates a <code>Method</code> and its parameters and returns boundary versions of its methods.
 */
//TODO: move to internal
public interface MethodParam {

    /**
     * A <code>Class</code> boundary of the return type of the encapsulated <code>Method</code>.
     * @return A <code>Class</code> boundary of the return type of the encapsulated <code>Method</code>.
     */
    ClassBoundary<?> getReturnType();

    /**
     * The supplied method arguments.
     * @return The supplied method arguments.
     */
    //TODO: This should be a List<Object>
    Object[] getArguments();

    /**
     * A boundary representing the encapsulated <code>Method</code>.
     * @return A boundary representing the encapsulated <code>Method</code>.
     */
    MethodBoundary getMethod();

    /**
     * A <code>Class</code> boundary array representing the argument types supplied to the encapsulated <code>Method</code>.
     * @return A <code>Class</code> boundary array representing the argument types supplied to the encapsulated <code>Method</code>.
     */
    //TODO: This should be a List<Class<?>>
    ClassBoundary<?>[] getParameterTypes();
}
