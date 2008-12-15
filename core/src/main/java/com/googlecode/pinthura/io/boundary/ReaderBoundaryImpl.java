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
package com.googlecode.pinthura.io.boundary;

import java.io.IOException;
import java.io.Reader;

public final class ReaderBoundaryImpl implements ReaderBoundary {

    private final Reader reader;

    public ReaderBoundaryImpl(final Reader reader) {
        this.reader = reader;
    }

    public int read() {
        try {
            return reader.read();
        } catch (IOException e) {
            throw new ReaderBoundaryException(e);
        }
    }

    public Reader getReader() {
        return reader;
    }

    @Override
    public boolean equals(final Object object) {
        return this == object || object != null && object instanceof ReaderBoundary && reader.equals(((ReaderBoundary) object).getReader());
    }

    @Override
    public int hashCode() {
        return reader.hashCode();
    }
}
