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
package com.googlecode.pinthura.example.factory.cache;

import java.util.List;
import java.util.ArrayList;

public final class SingleInstanceCache implements ObjectCache {

    private final List<ObjectCacheListener> listeners;
    private final ObjectCacheEventFactory factory;

    public SingleInstanceCache(final ObjectCacheEventFactory factory) {
        this.factory = factory;
        listeners = new ArrayList<ObjectCacheListener>();
    }

    public void addListener(final ObjectCacheListener listener) {
        listeners.add(listener);
    }

    public void setInstance(final Object obj) {
        for (ObjectCacheListener listener : listeners) {
            listener.instanceCreated(factory.create(obj));
        }
    }
}
