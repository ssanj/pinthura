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

/**
 * Signifies whether to include or exclude an item.
 * @param <T> The type of the item.
 */
public interface ItemFilter<T> {

    /**
     * Returns true if the item should be included and false if not.
     * 
     * @param item The item to filter.
     * @return The value true if the item should be included and false if not.
     */
    boolean include(T item);
}
