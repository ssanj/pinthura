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
package com.googlecode.pinthura.traverser;

import com.googlecode.pinthura.traverser.collection.CollectionElementHandler;
import com.googlecode.pinthura.traverser.collection.CollectionElementWithIndexHandler;
import com.googlecode.pinthura.traverser.collection.CollectionElementWithPartialResult;

import java.util.Collection;

public interface CollectionTraverser {

    <Input, Target, Output> Output forEach(Collection<? extends Input> collection, String path,
                                           CollectionElementHandler<Target, Output> handler);

    <Input, Output> Output forEach(Collection<? extends Input> collection, CollectionElementHandler<Input, Output> handler);

    <Input, Output> Output forEachWithIndex(Collection<? extends Input> collection,
                                            CollectionElementWithIndexHandler<Input, Output> handler);

    <Input, Target, Output> Output forEachWithIndex(Collection<? extends Input> collection, String path,
                                   CollectionElementWithIndexHandler<Target, Output> handler);

    <Input, Output> Output forEachWithResult(Collection<? extends Input> collection,
                                             CollectionElementWithPartialResult<Input, Output> handler, Output prevResult);
}
