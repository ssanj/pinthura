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
import com.googlecode.pinthura.util.RandomDataChooser;
import com.googlecode.pinthura.util.builder.RandomDataChooserBuilder;
import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

public final class AFileTextWriterUnderTest {

    private IMocksControl mockControl;
    private FileTextWriter fileTextWriter;
    private FileWriterFactory mockFileWriterFactory;
    private String randomFileName;
    private String randomLine1;
    private String randomLine2;

    @Before
    public void setup() {
        mockControl = EasyMock.createControl();
        mockFileWriterFactory = mockControl.createMock(FileWriterFactory.class);
        fileTextWriter = new FileTextWriterImpl(mockFileWriterFactory);

        RandomDataChooser randomDataChooser = new RandomDataChooserBuilder().build();
        randomFileName = randomDataChooser.chooseOneOf("one.txt", "two.xml", "ARandomFile");
        randomLine1 = randomDataChooser.chooseOneOf("Line1", "Line2", "Line3");
        randomLine2 = randomDataChooser.chooseOneOf("Line4", "Line5", "Line6");
    }

    @Test
    public void shouldWriteTheSuppliedText() {
        assertWriteOrAppend(false);
        fileTextWriter.write(randomFileName, Arrays.asList(randomLine1, randomLine2));
        mockControl.verify();
    }

    @Test
    public void shouldAppendTheSuppliedText() {
        assertWriteOrAppend(true);
        fileTextWriter.append(randomFileName, Arrays.asList(randomLine1, randomLine2));
        mockControl.verify();
    }

    private void assertWriteOrAppend(final boolean append) {
        WriterBoundary mockWriterBoundary = mockControl.createMock(WriterBoundary.class);
        EasyMock.expect(mockFileWriterFactory.create(randomFileName, append)).andReturn(mockWriterBoundary);
        mockWriterBoundary.write(randomLine1);
        mockWriterBoundary.write(randomLine2);
        mockWriterBoundary.flush();
        mockWriterBoundary.close();
        mockControl.replay();
    }
}
