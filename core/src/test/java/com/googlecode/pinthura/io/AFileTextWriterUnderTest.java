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

import com.googlecode.pinthura.boundary.java.lang.MathBoundaryImpl;
import com.googlecode.pinthura.io.boundary.WriterBoundary;
import com.googlecode.pinthura.util.RandomDataChooser;
import com.googlecode.pinthura.util.RandomDataChooserImpl;
import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

public final class AFileTextWriterUnderTest {

    private final IMocksControl mockControl = EasyMock.createControl();
    private FileTextWriter fileTextWriter;
    private FileWriterFactory mockFileWriterFactory;
    private RandomDataChooser randomDataChooser;

    @Before
    public void setup() {
        mockFileWriterFactory = mockControl.createMock(FileWriterFactory.class);
        randomDataChooser = new RandomDataChooserImpl(new MathBoundaryImpl());
        fileTextWriter = new FileTextWriterImpl(mockFileWriterFactory);
    }

    @Test
    public void shouldWriteTheSuppliedText() {
        String fileName = randomDataChooser.chooseOneOf("one.txt", "two.xml", "ARandomFile");
        String line1    = randomDataChooser.chooseOneOf("Line1", "Line2", "Line3");
        String line2    = randomDataChooser.chooseOneOf("Line4", "Line5", "Line6");

        WriterBoundary mockWriterBoundary = mockControl.createMock(WriterBoundary.class);
        EasyMock.expect(mockFileWriterFactory.create(fileName, false)).andReturn(mockWriterBoundary);
        mockWriterBoundary.write(line1);
        mockWriterBoundary.write(line2);
        mockWriterBoundary.flush();
        mockWriterBoundary.close();
        mockControl.replay();

        fileTextWriter.write(fileName, Arrays.asList(line1, line2));

        mockControl.verify();
    }

    //TODO: Write a test for append.
}
