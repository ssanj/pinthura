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

import com.googlecode.pinthura.factory.boundary.FieldBoundary;
import com.googlecode.pinthura.util.ItemFilter;

public final class MockPrefixFilter implements ItemFilter<FieldBoundary> {

    private final String prefix;

    public MockPrefixFilter(final String prefix) {
        this.prefix = prefix;
    }

    public boolean include(final FieldBoundary item) {
        return item.getName().startsWith(prefix);
    }
}
