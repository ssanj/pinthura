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
package com.googlecode.pinthura.traverser.collection.old;

public final class SummaryImpl implements Summary {

    private int lineCount = 0;

    public SummaryImpl(final String banner) {
        System.out.println("-------------" + banner + "-------------");
    }

    public Summary addSummaryLine(final String summaryLine) {
        System.out.println(++lineCount + ". " + summaryLine);
        return this;
    }

    public void close() {
        System.out.println("");
    }
}
