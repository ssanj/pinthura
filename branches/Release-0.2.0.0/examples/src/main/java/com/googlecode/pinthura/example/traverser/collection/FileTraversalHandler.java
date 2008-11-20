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
import com.googlecode.pinthura.traverser.collection.CollectionElementHandler;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

public final class FileTraversalHandler implements CollectionElementHandler<String, ProcessingResult> {

    private final Map<String, String> documentationMap = new HashMap<String, String>();
    private final List<String> configFilesList = new ArrayList<String>();
    private final LineFileReader reader;
    private final ProcessingResultFactory resultFactory;

    public FileTraversalHandler(final LineFileReader reader, final ProcessingResultFactory resultFactory) {
        this.reader = reader;
        this.resultFactory = resultFactory;
    }

    public void handle(final String file) {
        if (file.matches("^.+\\.(txt|doc|readme)$")) {
            String content = reader.readLine(file);
            documentationMap.put(file, content.matches("^##Update##.+$") ? "This file needs updating." : content);
        }

        if (file.matches("^.+\\.(config$|properties|xml)$")) {
            configFilesList.add(file);
        }
    }

    public ProcessingResult getResult() {
        return resultFactory.create(documentationMap, configFilesList);
    }
}
