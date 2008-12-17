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

import com.googlecode.pinthura.io.boundary.ReaderBoundary;
import com.googlecode.pinthura.io.boundary.ReaderBoundaryImpl;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

public final class FileReaderFactoryImpl implements FileReaderFactory {

    public ReaderBoundary create(final String fileName) {
        try {
            return new ReaderBoundaryImpl(new BufferedReader(new FileReader(fileName)));
        } catch (FileNotFoundException e) {
            throw new FileReaderFactoryException(e);
        }
    }
}
