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
import com.googlecode.pinthura.io.boundary.WriterBoundaryImpl;
import com.googlecode.pinthura.io.util.FileUtil;
import com.googlecode.pinthura.io.util.FileUtilImpl;

import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;

public final class FileWriterFactoryImpl implements FileWriterFactory {

    private final FileUtil fileUtil;

    public FileWriterFactoryImpl() {
        fileUtil = new FileUtilImpl();
    }

    public WriterBoundary create(final String fileName, final boolean append) {
        try {
            fileUtil.createPathIfNeeded(fileName);
            return new WriterBoundaryImpl(new OutputStreamWriter(new BufferedOutputStream(new FileOutputStream(fileName, append))));
        } catch (FileNotFoundException e) {
            throw new FileWriterFactoryException(e);
        }
    }
}
