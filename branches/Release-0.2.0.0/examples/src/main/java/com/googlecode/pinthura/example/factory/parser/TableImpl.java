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

import java.util.List;
import java.util.ArrayList;

public final class TableImpl implements Table {

    private final String name;
    private final List<TableColumnImpl> columns;

    public TableImpl(final String name) {
        this.name = name;
        columns = new ArrayList<TableColumnImpl>();
    }

    public void addColumn(final String columnName, final String type) {
        columns.add(new TableColumnImpl(columnName, type));
    }

    public String getName() {
        return name;
    }

    public List<TableColumnImpl> getColumns() {
        return columns;
    }

    @Override
    public String toString() {
        return new StringBuilder().append("Table: ").append(name).append(", Columns: ").append(columns).toString();
    }
}
