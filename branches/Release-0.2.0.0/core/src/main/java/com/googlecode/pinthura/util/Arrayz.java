package com.googlecode.pinthura.util;

import com.googlecode.pinthura.injection.filters.ItemFilter;

import java.util.Collection;
import java.util.List;

/**
 * A collection of useful methods for working with arrays.
 */
public interface Arrayz {

    /**
     * Given a variable number of objects of a type <T> returns an T[] of the same objects.
     * @param objects The objects to include in the T[].
     * @param <T> The type of objects in the array.
     * @return A T[] of the supplied objects.
     */    
    <T> T[] fromObjects(T... objects);

    /**
     * Given a <code>Collection</code> of elements of type <T> returns its contents as a T[].
     * @param collection The <code>Collection</code> whose items are to be returned as a T[].
     * @param clazz The Class<T>
     * @param <T> The type of object in the <code>Collection</code>.
     * @return A T[] built from the contents of the <code>Collection</code> supplied. If the a <code>Set<T></code> is supplied the order of
     * elements returned in the T[] is indeterminate.
     */    
    <T> T[] fromCollection(Collection<? extends T> collection, Class<T> clazz);

    /**
     * Filters the items in a supplied <code>Collection</code> of type <T> and returns the result in a <code>List</code> of the same type.
     * @param items The items to be filtered.
     * @param itemFilter The filter to use when filtering items.
     * @param <T> The type of items.
     * @return A <code>List<T></code> without the items filtered out by the supplied filter.
     */    
    <T> List<T> filter(Collection<T> items, ItemFilter<T> itemFilter);
}
