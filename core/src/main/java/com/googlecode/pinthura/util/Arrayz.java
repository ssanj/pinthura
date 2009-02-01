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

import com.googlecode.pinthura.injection.filters.ItemFilter;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * A collection of useful methods for working with arrays.
 */
//TODO: should this have an interface?
public final class Arrayz {

    private Arrayz() {
        //Utility class.
    }

    /**
     * Given a variable number of objects of a type <T> returns an T[] of the same objects.
     * @param objects The objects to include in the T[].
     * @param <T> The type of objects in the array.
     * @return A T[] of the supplied objects.
     */
    public static <T> T[] fromObjects(final T... objects) {
        return objects;
    }

    /**
     * Given a <code>Collection</code> of elements of type <T> returns its contents as a T[].
     * @param collection The <code>Collection</code> whose items are to be returned as a T[].
     * @param clazz The Class<T>
     * @param <T> The type of object in the <code>Collection</code>.
     * @return A T[] built from the contents of the <code>Collection</code> supplied. If the a <code>Set<T></code> is supplied the order of
     * elements returned in the T[] is indeterminate.
     */
    @SuppressWarnings({ "unchecked" })
    public static <T> T[] fromCollection(final Collection<T> collection, final Class<T> clazz) {
        return collection.toArray((T[]) Array.newInstance(clazz, collection.size()));
    }

    /**
     * Filters the items in a supplied <code>Collection</code> of type <T> and returns the result in a <code>List</code> of the same type.
     * @param items The items to be filtered.
     * @param itemFilter The filter to use when filtering items.
     * @param <T> The type of items.
     * @return A <code>List<T></code> without the items filtered out by the supplied filter.
     */
    public static <T> List<T> filter(final Collection<T> items, final ItemFilter<T> itemFilter) {
        List<T> filteredList = new ArrayList<T>();

        for (T item : items) {
            if (itemFilter.include(item)) {
                filteredList.add(item);
            }
        }

        return filteredList;
    }
}
