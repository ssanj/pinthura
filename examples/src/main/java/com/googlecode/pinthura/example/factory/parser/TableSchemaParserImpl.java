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
import com.googlecode.pinthura.example.factory.parser.factory.TableDataExtractorFactory;
import com.googlecode.pinthura.example.factory.parser.factory.TableFactory;

public final class TableSchemaParserImpl implements TableSchemaParser {

    private static final String NEW_LINE = "(\\n)";

    private final TableDataExtractorFactory extractorFactory;
    private final TableFactory tableFactory;
    private final StringTrimmerFactory trimmerFactory;


    public TableSchemaParserImpl(final TableDataExtractorFactory extractorFactory, final TableFactory tableFactory,
                                 final StringTrimmerFactory trimmerFactory) {
        this.extractorFactory = extractorFactory;
        this.tableFactory = tableFactory;
        this.trimmerFactory = trimmerFactory;
    }

    public Table parse(final String schema) {
        String[] lines = trimmerFactory.createTrimmer().trim(schema.split(NEW_LINE));

        Table table = tableFactory.create(extractorFactory.createExtractor().extractName(lines[0]));
        addTableColumns(lines, table);

        return table;
    }


    private void addTableColumns(final String[] lines, final Table table) {
        for (int index = 1; index < lines.length; index++) {
            String aLine = lines[index];
            String[] colSpec = extractorFactory.createExtractor().extractColumn(aLine);
            table.addColumn(colSpec[0], colSpec[1]);
        }
    }
}
