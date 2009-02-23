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

/**
 * Sets the value of a field on a given object.
 */
public interface FieldSetter {

    /**
     * Sets the value of the field with the supplied value or throws a <code>FieldSetterException</code> if the field
     * can't be set.
     * @param field The field whose value is to be set.
     * @param instance The object on which the field resides.
     * @param value The new value of the field.
     * @param <V> The type of the value.
     * @throws FieldSetterException If the field can't be set.
     */
    <V> void setValue(FieldBoundary<V> field, Object instance, V value) throws FieldSetterException;
}
