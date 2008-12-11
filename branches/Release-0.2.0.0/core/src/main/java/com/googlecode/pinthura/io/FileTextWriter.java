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

/**
 * Defines an inteface that facilitates the writing of textual files when given a file name and the contents. The implementation should
 * handle all opening and closing of the specified file.
 */
public interface FileTextWriter {

    /**
     * Appends the supplied sources to an existing file (if exists or creates one if not).
     * @param fileName The name of the file to append to.
     * @param sources The data to append to the file.
     * @throws FileTextWriterException If there is an error appending/creating the file.
     */
    void append(String fileName, Iterable<String> sources) throws FileTextWriterException;

    /**
     * Writes the supplied sources to the named file (and creates it if it does not exist).
     * @param fileName The name of the file to create.
     * @param sources The contents of the file.
     * @throws FileTextWriterException If there is an error creating the file.
     */
    void write(String fileName, Iterable<String> sources) throws FileTextWriterException;
}
