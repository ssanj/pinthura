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
package com.googlecode.pinthura.util;

import java.util.HashMap;
import java.util.Map;

public final class CreationBrokerImpl implements CreationBroker {

    private final Map<Class<?>, Object> registry = new HashMap<Class<?>, Object>();

    @SuppressWarnings("unchecked")
    public <T> void setInstance(final Class<T> clazz, final T instance) {
        registry.put(clazz, instance);
    }

    public <T> T getInstanceFor(final Class<T> clazz) {
        if (registry.containsKey(clazz)) {
            return clazz.cast(registry.get(clazz));
        }

        throw new CreationBrokerException("Could not find instance for " +  clazz);
    }
}
