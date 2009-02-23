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

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public final class ArrayzImpl implements Arrayz {

    public <T> T[] fromObjects(final T... objects) {
        return objects;
    }

    @SuppressWarnings("unchecked")
    public <T> T[] fromCollection(final Collection<? extends T> collection, final Class<T> clazz) {
        return collection.toArray((T[]) Array.newInstance(clazz, collection.size()));
    }

    public <T> List<T> filter(final Collection<T> items, final ItemFilter<T> itemFilter) {
        List<T> filteredList = new ArrayList<T>();

        for (T item : items) {
            if (itemFilter.include(item)) {
                filteredList.add(item);
            }
        }

        return filteredList;
    }
}
