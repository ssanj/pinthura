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

import com.googlecode.pinthura.traverser.collection.CollectionElementWithResultHandler;

public final class CollectionElementWithResultFilterHandler<Input, Output> implements CollectionElementWithResultHandler<Input, Output> {

    private final CollectionElementFilter<Input> filter;
    private final CollectionElementWithResultHandler<Input, Output> handler;

    public CollectionElementWithResultFilterHandler(final CollectionElementFilter<Input> filter,
                                                    final CollectionElementWithResultHandler<Input, Output> handler) {
        this.filter = filter;
        this.handler = handler;
    }

    public Output handle(final Input element, final Output prevResult) {
        if (filter.filter(element)) {
            return handler.handle(element, prevResult);
        }

        return prevResult;
    }
}
