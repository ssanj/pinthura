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
package com.googlecode.pinthura.example.factory.parser;

import com.googlecode.pinthura.example.factory.parser.factory.StringTrimmerFactory;
import com.googlecode.pinthura.example.factory.parser.factory.ColumnCleanerFactory;

public final class TableDataExtractorImpl implements TableDataExtractor {

    private static final String COLUMN_ENDERS = "(\\s|\\(|\\))";

    private final StringTrimmerFactory trimmerFactory;
    private final ColumnCleanerFactory columnCleanerFactory;

    public TableDataExtractorImpl(final StringTrimmerFactory trimmerFactory, final ColumnCleanerFactory columnCleanerFactory) {
        this.trimmerFactory = trimmerFactory;
        this.columnCleanerFactory = columnCleanerFactory;
    }

    public String extractName(final String text) {
        try {
            return trimmerFactory.createTrimmer().trim(text.split(COLUMN_ENDERS))[2];
        } catch (Exception e) {
            throw new TableDataExtractorException("The table name could not be extracted from: [" + text + "]", e);
        }
    }

    public String[] extractColumn(final String text) {
        String[] columnSpec = trimmerFactory.createTrimmer().trim(text.split(COLUMN_ENDERS));
        columnSpec[1] = columnCleanerFactory.createColumnTypeCleaner().clean(columnSpec[1]);
        return new String[] {columnSpec[0], columnSpec[1]};
    }
}
