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
package com.googlecode.pinthura.traverser.collection;

import com.googlecode.pinthura.traverser.CollectionTraverser;

import java.util.Collection;
import java.util.Iterator;

public final class CollectionTraverserImpl implements CollectionTraverser {

    public <Input, Output> Output forEach(final Collection<? extends Input> collection,
                          final CollectionElementHandler< Input, Output> handler) {

        for (Input input : collection) {
            handler.handle(input);
        }

        return handler.getResult();
    }

    public <Input, Output> Output forEach(final Collection<? extends Input> collection,
                                          final CollectionElementWithIndexHandler<Input, Output> handler) {

        final Iterator<? extends Input> iterator = collection.iterator();

        long index = 0;
        while (iterator.hasNext()) {
            handler.handle(iterator.next(), (index == 0), !iterator.hasNext(), index++);
        }

        return handler.getResult();
    }
}
