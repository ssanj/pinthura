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
package com.googlecode.pinthura.traverser.collection.filter;

import com.googlecode.pinthura.traverser.collection.CollectionElementHandler;

public final class CollectionElementFilterHandler<Input, Output> implements CollectionElementHandler<Input, Output> {

    private final CollectionElementFilter<Input> filter;
    private final CollectionElementHandler<Input, Output> handler;

    public CollectionElementFilterHandler(final CollectionElementFilter<Input> filter,
                                          final CollectionElementHandler<Input, Output> handler) {
        this.filter = filter;
        this.handler = handler;
    }

    public void handle(final Input element) {
        if (filter.filter(element)) {
            handler.handle(element);
        }
    }

    public Output getResult() {
        return handler.getResult();
    }
}
