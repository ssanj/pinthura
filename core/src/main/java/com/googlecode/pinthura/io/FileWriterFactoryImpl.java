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

import com.googlecode.pinthura.boundary.java.io.WriterBoundary;
import com.googlecode.pinthura.boundary.java.io.WriterBoundaryImpl;
import com.googlecode.pinthura.io.util.FileUtil;
import com.googlecode.pinthura.io.util.FileUtilImpl;

import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;

/**
 * Creates a <code>WriterBoundary</code> which wraps a <code>OutputStreamWriter</code> to a specified file in either
 * append or overwrite mode.
 */
public final class FileWriterFactoryImpl implements FileWriterFactory {

    private final FileUtil fileUtil;

    /**
     * Constructor.
     */
    public FileWriterFactoryImpl() {
        fileUtil = new FileUtilImpl();
    }

    /**
     * Creates a <code>WriterBoundary</code> to a named file in an append or overwrite mode.
     *
     * @param fileName The name of the file to create a <code>WriterBoundary</code> for. If the path to this file does not exist the
     * directory path will be created.
     * @param append If true, creates a <code>WriterBoundary</code> for appending, if false for creates one for overwriting.
     * @return A <code>WriterBoundary</code> to the name file in the mode requested.
     * @throws FileWriterFactoryException If the file can't be opened for writing.  
     */
    public WriterBoundary create(final String fileName, final boolean append) throws FileWriterFactoryException {
        try {
            fileUtil.createPathIfNeeded(fileName);
            return new WriterBoundaryImpl(new OutputStreamWriter(new BufferedOutputStream(new FileOutputStream(fileName, append))));
        } catch (FileNotFoundException e) {
            throw new FileWriterFactoryException(e);
        }
    }
}
