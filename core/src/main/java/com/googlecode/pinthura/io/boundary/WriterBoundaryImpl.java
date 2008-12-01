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
import java.io.Writer;

public final class WriterBoundaryImpl implements WriterBoundary {

    private final Writer writer;

    public WriterBoundaryImpl(final Writer writer) {
        this.writer = writer;
    }

    public void write(final String str) throws WriterBoundaryException {
        try {
            writer.write(str);
        } catch (IOException e) {
            throw new WriterBoundaryException(e);
        }
    }

    public void flush() throws WriterBoundaryException {
        try {
            writer.flush();
        } catch (IOException e) {
            throw new WriterBoundaryException(e);
        }
    }

    public void close() throws WriterBoundaryException {
        try {
            writer.close();
        } catch (IOException e) {
            throw new WriterBoundaryException(e);
        }
    }

    public Writer getWriter() {
        return writer;
    }

    @Override
    public boolean equals(final Object object) {
        return  object != null
                && object instanceof WriterBoundary
                && writer.equals(((WriterBoundary) object).getWriter());
    }

    @Override
    public int hashCode() {
        return writer.hashCode();
    }

    public String toString() {
        return "WriterBoundaryImpl[" + writer.toString() + "]";
    }

}
