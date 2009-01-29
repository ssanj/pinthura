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

import com.googlecode.pinthura.boundary.java.io.ReaderBoundary;
import static com.googlecode.pinthura.boundary.java.io.ReaderBoundary.NullObject.NULL_OBJECT;

public final class FileContentsReader implements TextFileReader {

    private final FileReaderFactory readerFactory;

    public FileContentsReader(final FileReaderFactory readerFactory) {
        this.readerFactory = readerFactory;
    }

    public String read(final String fileName) {
        ReaderBoundary readerBoundary = NULL_OBJECT; //Avoid using nulls with NullObject.

        try {
            StringBuilder builder = new StringBuilder();
            readerBoundary = readerFactory.create(fileName);

            int readCharacter;
            while ((readCharacter = readerBoundary.read()) != -1) {
                builder.append((char) readCharacter);
            }

            return builder.toString();
        } catch (Exception e) {
            throw new TextFileReaderException(e);
        } finally {
            readerBoundary.close();
        }
    }
}
