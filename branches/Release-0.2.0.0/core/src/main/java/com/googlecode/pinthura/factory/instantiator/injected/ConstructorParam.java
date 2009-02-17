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
package com.googlecode.pinthura.factory.instantiator.injected;

import com.googlecode.pinthura.boundary.java.lang.ClassBoundary;

/**
 * Encapsulates <code>Constructor</code> parameters. This includes <code>Class</code> types and instances of those
 * <code>Classes</code>.
 */
public interface ConstructorParam {

    /**
     * A <code>ClassBoundary[]</code> of types used for a <code>Constructor</code>.
     * @return A <code>ClassBoundary[]</code> of types used for a <code>Constructor</code>.
     */
    //TODO: change this to List<ClassBoundary>
    ClassBoundary<?>[] getConstructorTypes();

    /**
     * A <code>Object[]</code> of the instances of the types returned by {@link #getConstructorTypes()}.
     * @return A <code>Object[]</code> of the instances of the types returned by getConstructorTypes().
     */
    //TODO: change this to List<Object>
    Object[] getConstructorArguments();
}
