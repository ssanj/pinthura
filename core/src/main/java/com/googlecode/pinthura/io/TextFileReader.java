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
 * Reads a named text file.
 */
public interface TextFileReader {

    /**
     * Reads the named text file and returns its contents.
     * 
     * @param fileName The name of the text file.
     * @return The contents of the text file
     * @throws TextFileReaderException If the file contents can't be read for one reason or another.
     */
    String read(String fileName) throws TextFileReaderException;
}
