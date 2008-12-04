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
import java.util.List;

public final class FileTextWriterTest {

    private final IMocksControl mockControl = EasyMock.createControl();
    private FileTextWriter writer;
    private RandomDataChooser randomDataChooser;
    private FileWriterFactory mockFileWriterFactory;
    private WriterBoundary mockWriterBoundary;

    @Before
    public void setup() {
        mockFileWriterFactory = mockControl.createMock(FileWriterFactory.class);
        randomDataChooser = new RandomDataChooserBuilder().build();
        mockWriterBoundary = mockControl.createMock(WriterBoundary.class);

        writer = new FileTextWriterImpl(mockFileWriterFactory);
    }

    @SuppressWarnings({ "unchecked" })
    @Test
    public void shouldWriteSuppliedSources() {
        String fileName = randomDataChooser.chooseOneOf("blah.txt", "foo.bar.fib");
        List<String> sources = randomDataChooser.chooseOneOf(Arrays.asList("one", " two", " three"), Arrays.asList("5", " 4"));
        expectWrite(fileName, sources);

        writer.write(fileName, sources);

        mockControl.verify();
    }

    @SuppressWarnings({ "unchecked" })
    @Test
    public void shouldAppendSuppliedSources() {
        String fileName = randomDataChooser.chooseOneOf("Stargate.xml", "Dictionary.dic", "abc.def");
        List<String> sources = randomDataChooser.chooseOneOf(Arrays.asList("testing123"), Arrays.asList("uno", "dos", "tres", "quatro"));
        expectAppend(fileName, sources);

        writer.append(fileName, sources);

        mockControl.verify();
    }

    private void expectWrite(final String fileName, final List<String> sources) {
        writeAndReplay(fileName, sources, false);
    }

    private void expectAppend(final String fileName, final List<String> sources) {
        writeAndReplay(fileName, sources, true);
    }

    private void writeAndReplay(final String fileName, final List<String> sources, final boolean append) {
        EasyMock.expect(mockFileWriterFactory.create(fileName, append)).andReturn(mockWriterBoundary);

        for (String source : sources) {
            mockWriterBoundary.write(source);
        }

        mockWriterBoundary.flush();
        mockWriterBoundary.close();
        mockControl.replay();
    }
}
