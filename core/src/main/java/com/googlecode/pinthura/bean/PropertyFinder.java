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
package com.googlecode.pinthura.bean;

import com.googlecode.pinthura.boundary.java.lang.reflect.MethodBoundary;

/**
 * Finds a named-property on given <code>Class</code>. 
 */
public interface PropertyFinder {

    /**
     * Returns the <code>MethodBoundary</code> given a property and the parent <code>Class</code>.
     *
     * @param property The property to search for.
     * @param parentClass The parent <code>Class</code>.
     * @param <ParentClass> The type of parent.
     * @return The <code>MethodBoundary</code> for the property provided.
     * @throws PropertyFinderException If the property can't be found.
     */
    <ParentClass> MethodBoundary findMethodFor(String property, Class<ParentClass> parentClass) throws PropertyFinderException;
}
