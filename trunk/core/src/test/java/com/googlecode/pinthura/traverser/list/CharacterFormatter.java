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
package com.googlecode.pinthura.traverser.list;

import com.googlecode.pinthura.traverser.collection.CollectionElementHandler;

public final class CharacterFormatter implements CollectionElementHandler<Character, String> {

    private final StringBuilder builder = new StringBuilder();

    public void handle(final Character element, final boolean isFirst, final boolean isLast, final Long index) {
        if (isFirst) {
            builder.append("[");
        }

        builder.append(element).append(index);

        if (!isLast) {
            builder.append(", ");
        }

        if (isLast) {
            builder.append("]");
        }
    }

    public String getResult() {
        return builder.toString();
    }
}

