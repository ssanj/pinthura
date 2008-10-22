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

import com.googlecode.pinthura.bean.PathEvaluatorImpl;
import com.googlecode.pinthura.bean.PropertyFinderImpl;
import com.googlecode.pinthura.factory.FactoryCreator;
import com.googlecode.pinthura.factory.builder.FactoryCreatorBuilder;
import com.googlecode.pinthura.traverser.collection.old.DirectoryListerImpl;
import com.googlecode.pinthura.traverser.collection.old.LineFileReaderImpl;

public final class FileSummarizerRunner {

    private FileSummarizerRunner() {
        //Main class.
    }

    public static void main(final String[] args) {
        CollectionTraverserImpl traverser = new CollectionTraverserImpl(new PathResolverImpl(new PathEvaluatorImpl(
                new PropertyFinderImpl())));
        FactoryCreator factoryCreator = new FactoryCreatorBuilder().build();
        FileSummarizer fileSummarizer = new FileSummarizer(new DirectoryListerImpl(), traverser,
                factoryCreator.create(SummarizerFactory.class));
        fileSummarizer.summarizeDictory("blue", new LineFileReaderImpl());
    }
}
