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

import com.googlecode.pinthura.factory.boundary.ClassBoundaryImpl;
import com.googlecode.pinthura.factory.boundary.ClassBoundary;

public final class ClassInstanceImpl implements ClassInstance {

    private ClassBoundary<?> clazz;
    private final Object instance;

    public <T> ClassInstanceImpl(final Class<T> clazz, final Object instance) {
        this(new ClassBoundaryImpl<T>(clazz), instance);
    }

    public <T> ClassInstanceImpl(final ClassBoundary<T> clazz, final Object instance) {
        this.instance = instance;
        this.clazz = clazz;
    }

    @SuppressWarnings({ "unchecked" })
    public <T> ClassBoundary<T> getClazz() {
        return  (ClassBoundary<T>) clazz;
    }

    @SuppressWarnings({ "unchecked" })
    public <T> T getInstance() {
        return (T) instance;
    }
}
