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
package com.googlecode.pinthura.example.traverser.collection.old;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.ArrayList;

public final class FileSummarizer {

    private final Map<String, String> documentationMap = new HashMap<String, String>();
    private final List<String> configFilesList = new ArrayList<String>();
    private final LineFileReader reader;
    private final DirectoryLister lister;
    private final List<Summary> summaryList = new ArrayList<Summary>();


    public FileSummarizer(final LineFileReader reader, final DirectoryLister lister) {
        this.reader = reader;
        this.lister = lister;
    }

    public List<Summary> summarizeDictory(final String directory) {
        List<String> files = lister.list(directory);
        processFiles(files);

        summaryList.add(createSummary(documentationMap.keySet(), documentationMap, "Documentation Files"));
        summaryList.add(createSummary(configFilesList, "Config Files"));
        return summaryList;
    }

    private Summary createSummary(final List<String> files, final String banner) {
        Summary summary = new SummaryImpl(banner);
        for (String confFile : files) {
            summary.addSummaryLine(confFile);
        }
        summary.close();
        return summary;

    }

    private void processFiles(final List<String> files) {
        for (String file : files) {

            if (file.matches("^.+\\.(txt|doc|readme)$")) {
                String content = reader.readLine(file);
                documentationMap.put(file, content.matches("^##Update##.+$") ? "This file needs updating." : content);
            }

            if (file.matches("^.+\\.(config$|properties|xml)$")) {
                configFilesList.add(file);
            }
        }
    }

    private Summary createSummary(final Set<String> files, final Map<String, String> contentsMap, final String banner) {
        Summary summary = new SummaryImpl(banner);
        for (String confFile : files) {
            summary.addSummaryLine(confFile + ":" + contentsMap.get(confFile));
        }
        summary.close();
        return summary;
    }
}
