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
package com.googlecode.pinthura.io;

import com.googlecode.pinthura.io.boundary.WriterBoundary;

public final class FileTextWriterImpl implements FileTextWriter {

    private final FileWriterFactory fileWriterFactory;

    public FileTextWriterImpl(final FileWriterFactory fileWriterFactory) {
        this.fileWriterFactory = fileWriterFactory;
    }

    public void write(final String fileName, final Iterable<String> sources) {
        write(fileName, sources, false);
    }

    public void append(final String fileName, final Iterable<String> sources) {
        write(fileName, sources, true);
    }

    private void write(final String fileName, final Iterable<String> sources, final boolean append) {
        try {
            WriterBoundary writer = open(fileName, append);
            writeSources(sources, writer);
            close(writer);
        } catch (Exception e) {
            throw new FileWriterCoordinatorException(e);
        }
    }

    private void writeSources(final Iterable<String> sources, final WriterBoundary writer) {
        for (String source : sources) {
            writer.write(source);
        }
    }

    private WriterBoundary open(final String fileName, final boolean append) {
        return fileWriterFactory.create(fileName, append);
    }

    private void close(final WriterBoundary out) {
        if (out != null) {
            out.flush();
            out.close();
        }
    }
}
