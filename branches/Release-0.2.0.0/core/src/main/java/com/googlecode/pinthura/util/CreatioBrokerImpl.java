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

public final class CreatioBrokerImpl implements CreationBroker {

    private final Map<Class<?>, CreationListener> listeners = new HashMap<Class<?>, CreationListener>();

    public  <T> void addCreationListener(final Class<T> clazzOfInterest, final CreationListener<T> creationListener) {
        listeners.put(clazzOfInterest, creationListener);
    }

    @SuppressWarnings({ "unchecked" })
    public <T> void notifyInstanceCreated(final T instance) {
        Map<Class<?>, CreationListener> listenerCopy = new HashMap<Class<?>, CreationListener>(listeners);

        for (Class<?> clazz : listenerCopy.keySet()) {
            if (clazz.isAssignableFrom(instance.getClass())) {
                CreationListener<T> listener = listenerCopy.get(clazz);
                listener.instanceCreated(instance);
            }
        }
    }
}
