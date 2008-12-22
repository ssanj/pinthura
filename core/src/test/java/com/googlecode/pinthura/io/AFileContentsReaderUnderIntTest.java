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
import com.googlecode.pinthura.util.RandomDataCreator;
import com.googlecode.pinthura.util.builder.RandomDataCreatorBuilder;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;
import org.junit.Before;
import org.junit.Test;

import java.io.StringReader;

public final class AFileContentsReaderUnderIntTest {

    private static final String CONTENTS = "this is a short String";

    private TextFileReader reader;
    private RandomDataCreator randomDataCreator;

    @Before
    public void setup() {
        randomDataCreator = new RandomDataCreatorBuilder().build();
        reader = new FileContentsReader(new StringReaderFactory());
    }

    @Test
    public void shouldReadASuppliedFile() {
        String result = reader.read(randomDataCreator.createFileName(10));
        assertThat(result, equalTo(CONTENTS));
    }

    private static final class StringReaderFactory implements FileReaderFactory  {

        public ReaderBoundary create(final String fileName) throws FileReaderFactoryException {
            return new ReaderBoundaryImpl(new StringReader(CONTENTS));
        }
    }
}
