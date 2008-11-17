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

import com.googlecode.pinthura.traverser.CollectionTraverser;
import com.googlecode.pinthura.example.traverser.collection.old.DirectoryLister;
import com.googlecode.pinthura.example.traverser.collection.old.LineFileReader;
import com.googlecode.pinthura.example.traverser.collection.old.Summary;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public final class FileSummarizer {

    private final DirectoryLister lister;
    private final List<Summary> summaryList = new ArrayList<Summary>();
    private final CollectionTraverser traverser;
    private final SummarizerFactory summarizerFactory;


    public FileSummarizer(final DirectoryLister lister, final CollectionTraverser traverser, final SummarizerFactory summarizerFactory) {
        this.lister = lister;
        this.traverser = traverser;
        this.summarizerFactory = summarizerFactory;
    }

    public List<Summary> summarizeDirectory(final String directory, final LineFileReader lineFileReader) {
        List<String> files = lister.list(directory);
        ProcessingResult result = traverser.forEach(files, summarizerFactory.createFileTraverser(lineFileReader));

        summaryList.add(createDocumentSummary(result));
        summaryList.add(createConfigSummary(result));
        return summaryList;
    }

    private Summary createConfigSummary(final ProcessingResult result) {
        return traverser.forEach(result.getConfigFiles(), summarizerFactory.createSummary("Config Files"));
    }

    private Summary createDocumentSummary(final ProcessingResult result) {
        Map<String, String> docMap = result.getDocumentation();
        return traverser.forEach(docMap.keySet(), summarizerFactory.createSummary("Documentation Files", docMap));
    }
}

