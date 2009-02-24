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

import com.googlecode.pinthura.annotation.SuppressionReason;
import com.googlecode.pinthura.boundary.java.io.WriterBoundary;
import com.googlecode.pinthura.boundary.java.io.WriterBoundaryImpl;
import com.googlecode.pinthura.util.RandomDataCreator;
import com.googlecode.pinthura.util.builder.RandomDataCreatorBuilder;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;
import org.junit.Before;
import org.junit.Test;

import java.io.StringWriter;
import java.util.Arrays;
import java.util.List;

public final class AFileTextWriterUnderIntTest {

    private FileTextWriter writer;
    @SuppressWarnings("InstanceVariableOfConcreteClass")
    @SuppressionReason(SuppressionReason.Reason.TEST_TYPE)
    private StringWriterFactory stringWriterFactory;
    private String randomFileName;
    private List<String> randomDataList;
    private String expectedData;

    @Before
    public void setup() {
        stringWriterFactory = new StringWriterFactory();
        writer = new FileTextWriterImpl(stringWriterFactory);
        RandomDataCreator randomDataCreator = new RandomDataCreatorBuilder().build();
        randomFileName = randomDataCreator.createFileName(15);
        String randomLine1 = randomDataCreator.createString(randomDataCreator.createNumber(30));
        String randomLine2 = randomDataCreator.createString(randomDataCreator.createNumber(30));
        String randomLine3 = randomDataCreator.createString(randomDataCreator.createNumber(30));

        randomDataList = Arrays.asList(randomLine1, randomLine2, randomLine3);
        expectedData = new StringBuilder().append(randomLine1).append(randomLine2).append(randomLine3).toString();
    }

    @Test
    public void shouldWriteSuppliedSources() {
        writer.write(randomFileName, randomDataList);
        assertThat(stringWriterFactory.getWriter().toString(), equalTo(expectedData));
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
