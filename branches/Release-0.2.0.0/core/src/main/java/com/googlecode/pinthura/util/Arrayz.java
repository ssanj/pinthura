package com.googlecode.pinthura.util;

import com.googlecode.pinthura.injection.filters.ItemFilter;

import java.util.Collection;
import java.util.List;

public interface Arrayz {

    <T> T[] fromObjects(T... objects);
    
    <T> T[] fromCollection(Collection<? extends T> collection, Class<T> clazz);

    <T> List<T> filter(Collection<T> items, ItemFilter<T> itemFilter);
}
