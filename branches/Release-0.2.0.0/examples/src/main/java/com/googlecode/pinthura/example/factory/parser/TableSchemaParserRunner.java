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

import com.googlecode.pinthura.example.factory.parser.factory.ColumnCleanerFactory;
import com.googlecode.pinthura.example.factory.parser.factory.StringTrimmerFactory;
import com.googlecode.pinthura.example.factory.parser.factory.TableDataExtractorFactory;
import com.googlecode.pinthura.example.factory.parser.factory.TableFactory;
import com.googlecode.pinthura.factory.FactoryCreator;
import com.googlecode.pinthura.factory.builder.FactoryCreatorBuilder;

public final class TableSchemaParserRunner {

    private TableSchemaParserRunner() { }

    private static final String SCHEMA = "create table Person (\n"
                                    + "ID INT NOT NULL, \n"
                                    + "NAME VARCHAR(25) NOT NULL, \n"
                                    + "AGE SHORT NOT NULL);";


    public static void main(final String[] args) {
        System.out.println("With FactoryCreator");
        withFactoryCreator();
        System.out.println();
        System.out.println("Without FactoryCreator");
        withoutFactoryCreator();
    }

    private static void withoutFactoryCreator() {
        StringTrimmerFactory trimmerFactory = new StringTrimmerFactoryImpl();
        TableDataExtractorFactory extractorFactor = new TableDataExtractorFactoryImpl(trimmerFactory, new ColumnCleanerFactoryImpl());
        TableSchemaParser parser = new TableSchemaParserImpl(extractorFactor, new TableFactoryImpl(), trimmerFactory);

        System.out.println(parser.parse(SCHEMA));
    }

    private static void withFactoryCreator() {
        FactoryCreator factoryCreator = new FactoryCreatorBuilder().build();

        TableDataExtractorFactory tableDataExtractorFactory = factoryCreator.create(TableDataExtractorFactory.class);
        TableFactory tableFactory = factoryCreator.create(TableFactory.class);
        StringTrimmerFactory trimmerFactory = factoryCreator.create(StringTrimmerFactory.class);

        TableSchemaParser parser = new TableSchemaParserImpl(tableDataExtractorFactory, tableFactory, trimmerFactory);

        System.out.println(parser.parse(SCHEMA));
    }

    /**
     * These factory implementations are only used by withoutFactoryCreator().
     */
    private static class TableDataExtractorFactoryImpl implements TableDataExtractorFactory {

        private final StringTrimmerFactory stringTrimmerFactory;
        private final ColumnCleanerFactory columnCleanerFactory;

        public TableDataExtractorFactoryImpl(final StringTrimmerFactory stringTrimmerFactory,
                                             final ColumnCleanerFactory columnCleanerFactory) {
            this.columnCleanerFactory = columnCleanerFactory;
            this.stringTrimmerFactory = stringTrimmerFactory;
        }

        public TableDataExtractor createExtractor() {
            return new TableDataExtractorImpl(stringTrimmerFactory, columnCleanerFactory);
        }
    }

    private static class StringTrimmerFactoryImpl implements StringTrimmerFactory {

        public StringTrimmer createTrimmer() {
            return new StringTrimmerImpl();
        }
    }

    private static class ColumnCleanerFactoryImpl implements ColumnCleanerFactory {

        public ColumnTypeCleaner createColumnTypeCleaner() {
            return new ColumnTypeCleanerImpl();
        }
    }

    private static class TableFactoryImpl implements TableFactory {

        public Table create(final String tableName) {
            return new TableImpl(tableName);
        }
    }
}
