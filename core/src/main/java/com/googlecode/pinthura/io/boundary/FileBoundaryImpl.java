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

import java.io.File;

public final class FileBoundaryImpl implements FileBoundary {

    private final File file;

    public FileBoundaryImpl(final File file) {
        this.file = file;
    }

    public FileBoundaryImpl(final String fileName) {
        file = new File(fileName);
    }

    public FileBoundary getParentFile() {
        return new FileBoundaryImpl(file.getParentFile());
    }

    public boolean exists() {
        return file.exists();
    }

    public boolean mkdirs() {
        return file.mkdirs();
    }

    public File getFile() {
        return file;
    }

    @Override
    public boolean equals(final Object object) {
        return this == object || (object != null && object instanceof FileBoundary && file.equals(((FileBoundary) object).getFile()));
    }

    @Override
    public int hashCode() {
        return file.hashCode();
    }
}
