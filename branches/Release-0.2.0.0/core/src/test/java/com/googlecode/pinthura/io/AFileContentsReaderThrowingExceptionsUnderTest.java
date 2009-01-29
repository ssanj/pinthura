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
import com.googlecode.pinthura.util.RandomDataCreator;
import com.googlecode.pinthura.util.builder.RandomDataCreatorBuilder;
import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import org.junit.Before;
import org.junit.Test;

public final class AFileContentsReaderThrowingExceptionsUnderTest {

    private final IMocksControl mockControl = EasyMock.createControl();
    private TextFileReader reader;
    private FileReaderFactory mockFileReaderFactory;
    private ReaderBoundary mockReaderBoundary;
    private String fileNameRandom;

    @Before
    public void setup() {
        mockFileReaderFactory = mockControl.createMock(FileReaderFactory.class);
        RandomDataCreator randomDataCreator = new RandomDataCreatorBuilder().build();
        mockReaderBoundary = mockControl.createMock(ReaderBoundary.class);
        reader = new FileContentsReader(mockFileReaderFactory);
        fileNameRandom = randomDataCreator.createFileName(15);
    }

    @SuppressWarnings("ThrowableInstanceNeverThrown")
    @Test(expected = TextFileReaderException.class)
    public void shouldRethrowExceptionsIfReaderBoundaryCreationFails() {
        EasyMock.expect(mockFileReaderFactory.create(fileNameRandom)).andThrow(new RuntimeException());
        mockControl.replay();

        reader.read(fileNameRandom);
    }

    @SuppressWarnings("ThrowableInstanceNeverThrown")
    @Test(expected = TextFileReaderException.class)
    public void shouldCloseReaderBoundary() {
        EasyMock.expect(mockFileReaderFactory.create(fileNameRandom)).andReturn(mockReaderBoundary);
        EasyMock.expect(mockReaderBoundary.read()).andThrow(new RuntimeException());
        mockReaderBoundary.close();
        mockControl.replay();

        reader.read(fileNameRandom);

        mockControl.verify();
    }
}
