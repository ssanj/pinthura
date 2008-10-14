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

import com.googlecode.pinthura.traverser.Break;
import com.googlecode.pinthura.traverser.CollectionTraverser;

import java.util.Collection;
import java.util.Iterator;

public final class CollectionTraverserImpl implements CollectionTraverser {

    private final PathResolver pathResolver;

    public CollectionTraverserImpl(final PathResolver pathResolver) {
        this.pathResolver = pathResolver;
    }

    public <Input, Target, Output> Output forEach(final Collection<? extends Input> collection, final String path,
                                                  final CollectionElementHandler<Target, Output> handler) {
        return simpleForEach(collection, path, handler);
    }

    public <Input, Output> Output forEach(final Collection<? extends Input> collection,
                          final CollectionElementHandler< Input, Output> handler) {
        return simpleForEach(collection, PathResolver.NO_PATH, handler);
    }

    public <Input, Target, Output> Output forEachWithIndex(final Collection<? extends Input> collection, final String path,
                                          final CollectionElementWithIndexHandler<Target, Output> handler) {
        return indexedforEach(collection, path, handler);
    }

    public <Input, Output> Output forEachWithIndex(final Collection<? extends Input> collection,
                                          final CollectionElementWithIndexHandler<Input, Output> handler) {
        return indexedforEach(collection, PathResolver.NO_PATH, handler);
    }

    public <Input, Output> Output forEachWithResult(final Collection<? extends Input> collection,
                                          final CollectionElementWithPartialResult<Input, Output> handler, final Output prevResult) {

        Output result = prevResult;
        for (Input input : collection) {
            try {
                result = handler.handle(input, result);
            } catch (Break b) {
                break;
            }
        }

        return result;
    }

    @SuppressWarnings({ "unchecked" })
    private <Input, Target, Output> Output indexedforEach(final Collection<? extends Input> collection, final String path,
                                                          final CollectionElementWithIndexHandler<Target, Output> handler) {

        final Iterator<? extends Input> iterator = collection.iterator();

        long index = 0;
        while (iterator.hasNext()) {
            try {
                handler.handle((Target) resolvePath(path, iterator.next()), (index == 0), !iterator.hasNext(), index++);
            } catch (Break b) {
                break;
            }
        }

        return handler.getResult();
    }

    @SuppressWarnings({ "unchecked" })
    private <Input, Target, Output> Output simpleForEach(final Collection<? extends Input> collection, final String path,
                                                         final CollectionElementHandler<Target, Output> handler) {

        for (Input input : collection) {
            try {
                handler.handle((Target) resolvePath(path, input));
            } catch (Break b) {
                break;
            }
        }

        return handler.getResult();
    }

    @SuppressWarnings({ "unchecked" })
    private <Input, Target> Target resolvePath(final String path, final Input input) {
        return (Target) pathResolver.resolvePath(path, input);
    }
}
