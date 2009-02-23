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
package com.googlecode.pinthura.injection.filters;

import com.googlecode.pinthura.boundary.java.lang.reflect.FieldBoundary;
import com.googlecode.pinthura.util.ItemFilter;

/**
 * Filters fields that start with the supplied <code>prefix</code>.
 */
public final class MockPrefixFilter implements ItemFilter<FieldBoundary> {

    private final String prefix;

    /**
     * The mock prefix.
     * @param prefix The mock prefix.
     */
    public MockPrefixFilter(final String prefix) {
        this.prefix = prefix;
    }

    /**
     * Returns true if the field starts with the mock prefix and false if not.
     * @param item The item to filter.
     * @return The value true if the field starts with the mock prefix and false if not.
     */
    public boolean include(final FieldBoundary item) {
        return item.getName().startsWith(prefix);
    }
}
