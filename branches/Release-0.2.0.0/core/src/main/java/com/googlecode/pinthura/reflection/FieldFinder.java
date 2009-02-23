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
package com.googlecode.pinthura.reflection;

import com.googlecode.pinthura.boundary.java.lang.reflect.FieldBoundary;

import java.util.List;

/**
 * Finds <code>fields</code> on an object given a criteria such as name or prefix.
 */
public interface FieldFinder {

    /**
     * Returns a field given its name and the object its on or throws a <code>FieldFinderException</code> if the field can't be
     * found.
     *
     * @param varName The name of the field.
     * @param instance  The object that contains the field.
     * @param <T> The type of field.
     * @return The named field.
     * @throws FieldFinderException If the field can't be found.
     */
    <T> FieldBoundary<T> findByName(String varName, Object instance) throws FieldFinderException;

    /**
     * Returns a <code>List</code> of fields given a prefix and an object or throws a <code>FieldFinderException</code> if fields
     * of the given prefix can't be found.
     *
     * @param prefix The prefix of the fields to return.
     * @param instance The object that contains the fields.
     * @return A <code>List</code> of fields of the given prefix.
     * @throws FieldFinderException If fields of the given prefix can't be found.
     */
    List<FieldBoundary<?>> findByPrefix(String prefix, Object instance) throws FieldFinderException;
}
