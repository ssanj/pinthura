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
import com.googlecode.pinthura.boundary.java.io.ReaderBoundaryImpl;
import com.googlecode.pinthura.util.RandomDataCreator;
import com.googlecode.pinthura.util.builder.RandomDataCreatorBuilder;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;
import org.junit.Before;
import org.junit.Test;

import java.io.StringReader;

public final class AFileContentsReaderUnderIntTest {

    private TextFileReader reader;
    private String randomContent;
    private String randomFileName;

    @Before
    public void setup() {
        RandomDataCreator randomDataCreator = new RandomDataCreatorBuilder().build();
        randomFileName = randomDataCreator.createFileName(10);
        randomContent = randomDataCreator.createString(25);
        reader = new FileContentsReader(new StringReaderFactory(randomContent));
    }

    @Test
    public void shouldReadASuppliedFile() {
        String result = reader.read(randomFileName);
        assertThat(result, equalTo(randomContent));
    }

    private static final class StringReaderFactory implements FileReaderFactory  {

        private final String contents;

        private StringReaderFactory(final String contents) {
            this.contents = contents;
        }

        public ReaderBoundary create(final String fileName) throws FileReaderFactoryException {
            return new ReaderBoundaryImpl(new StringReader(contents));
        }
    }
}
