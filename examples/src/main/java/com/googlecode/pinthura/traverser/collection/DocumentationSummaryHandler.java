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

import java.util.Map;

public final class DocumentationSummaryHandler extends SummaryHandler {

    private final Map<String, String> docMap;

    public DocumentationSummaryHandler(final String banner, final Map<String, String> docMap) {
        super(banner);
        this.docMap = docMap;
    }

    public void handle(final String file) {
        getSummary().addSummaryLine(file + ":" + docMap.get(file));
    }
}

