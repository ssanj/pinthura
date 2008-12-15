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
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;
import org.junit.Before;
import org.junit.Test;

import java.io.StringWriter;
import java.util.Arrays;

public final class AFileTextWriterUnderIntTest {

    private FileTextWriter writer;
    @SuppressWarnings("InstanceVariableOfConcreteClass")
    private StringWriterFactory stringWriterFactory;

    @Before
    public void setup() {
        stringWriterFactory = new StringWriterFactory();
        writer = new FileTextWriterImpl(stringWriterFactory);
    }

    @Test
    public void shouldWriteSuppliedSources() {
        writer.write("", Arrays.asList("one", " two ", "three"));
        assertThat(stringWriterFactory.getWriter().toString(), equalTo("one two three"));
    }

    private static class StringWriterFactory implements FileWriterFactory {

        private StringWriter writer;

        public WriterBoundary create(final String fileName, final boolean append) {
            writer = new StringWriter();
            return new WriterBoundaryImpl(writer);
        }

        public StringWriter getWriter() {
            return writer;
        }
    }
}
