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

/**
 * This interface defines how a <code>WriteBoundary</code> is created for a specified file in either overwrite or append mode.
 */
public interface FileWriterFactory {

    /**
     * Creates a <code>WriterBoundary</code> for the file specified.
     * @param fileName The name of the file to create a <code>WriterBoundary</code> for. If the path to this file does not exist the
     * directory path will be created.
     * @param append If true, creates a <code>WriterBoundary</code> for appending, if false for overwriting.
     * @return A <code>WriterBoundary</code> to the specified file.
     * @throws FileWriterFactoryException If the <code>WriterBoundary</code> could not be created.
     *
     */
    WriterBoundary create(String fileName, boolean append) throws FileWriterFactoryException;
}
