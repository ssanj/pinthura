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
import com.googlecode.pinthura.util.RandomDataChooser;
import com.googlecode.pinthura.util.builder.RandomDataChooserBuilder;
import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

public final class AFileTextWriterUnderTest {

    private final IMocksControl mockControl = EasyMock.createControl();
    private FileTextWriter fileTextWriter;
    private FileWriterFactory mockFileWriterFactory;
    private String fileName;
    private String line1;
    private String line2;

    @Before
    public void setup() {
        mockFileWriterFactory = mockControl.createMock(FileWriterFactory.class);
        fileTextWriter = new FileTextWriterImpl(mockFileWriterFactory);

        RandomDataChooser randomDataChooser = new RandomDataChooserBuilder().build();
        fileName = randomDataChooser.chooseOneOf("one.txt", "two.xml", "ARandomFile");
        line1    = randomDataChooser.chooseOneOf("Line1", "Line2", "Line3");
        line2    = randomDataChooser.chooseOneOf("Line4", "Line5", "Line6");
    }

    @Test
    public void shouldWriteTheSuppliedText() {
        assertWriteOrAppend(false);
        fileTextWriter.write(fileName, Arrays.asList(line1, line2));
        mockControl.verify();
    }

    @Test
    public void shouldAppendTheSuppliedText() {
        assertWriteOrAppend(true);
        fileTextWriter.append(fileName, Arrays.asList(line1, line2));
        mockControl.verify();
    }

    private void assertWriteOrAppend(final boolean append) {
        WriterBoundary mockWriterBoundary = mockControl.createMock(WriterBoundary.class);
        EasyMock.expect(mockFileWriterFactory.create(fileName, append)).andReturn(mockWriterBoundary);
        mockWriterBoundary.write(line1);
        mockWriterBoundary.write(line2);
        mockWriterBoundary.flush();
        mockWriterBoundary.close();
        mockControl.replay();
    }
}
