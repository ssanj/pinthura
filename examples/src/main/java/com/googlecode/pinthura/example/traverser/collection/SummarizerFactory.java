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
package com.googlecode.pinthura.example.traverser.collection;

import com.googlecode.pinthura.example.traverser.collection.old.LineFileReader;
import com.googlecode.pinthura.example.traverser.collection.old.Summary;
import com.googlecode.pinthura.factory.Factory;
import com.googlecode.pinthura.factory.Implementation;
import com.googlecode.pinthura.factory.InjectedFactory;
import com.googlecode.pinthura.traverser.collection.CollectionElementHandler;

import java.util.Map;

public interface SummarizerFactory {

    @Implementation(FileTraversalHandler.class)
    CollectionElementHandler<String, ProcessingResult> createFileTraverser(final LineFileReader lineFileReader);

    @Implementation(ConfigSummaryHandler.class)
    @InjectedFactory({ @Factory(factoryClass = SummaryFactory.class, index = 1) })
    CollectionElementHandler<String, Summary> createSummary(String banner);

    @Implementation(DocumentationSummaryHandler.class)
    @InjectedFactory({ @Factory(factoryClass = SummaryFactory.class, index = 2) })
    CollectionElementHandler<String, Summary> createSummary(final String banner, final Map<String, String> docMap);
}
