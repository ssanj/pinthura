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
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;
import org.junit.Before;
import org.junit.Test;

public final class AFileContentsReaderUnderTest {

    private IMocksControl mockControl;
    private TextFileReader reader;
    private FileReaderFactory mockFileReaderFactory;
    private ReaderBoundary mockReaderBoundary;
    private String fileNameRandom;
    private String randomStringData;

    @Before
    public void setup() {
        mockControl = EasyMock.createControl();
        mockFileReaderFactory = mockControl.createMock(FileReaderFactory.class);
        RandomDataCreator randomDataCreator = new RandomDataCreatorBuilder().build();
        randomStringData = randomDataCreator.createString(randomDataCreator.createBoundedNumber(25, 100));
        mockReaderBoundary = mockControl.createMock(ReaderBoundary.class);
        reader = new FileContentsReader(mockFileReaderFactory);
        fileNameRandom = randomDataCreator.createFileName(15);
    }

    @Test
    public void shouldReadAGivenFile() {
        expectReadContent(randomStringData);
    }

    private void expectReadContent(final String content) {
        expectContent(content);
        assertContent(content);
    }


    private void assertContent(final String content) {
        mockControl.replay();
        assertThat(reader.read(fileNameRandom), equalTo(content));
        mockControl.verify();
    }

    private void expectContent(final String content) {
        for (char character : content.toCharArray()) {
            EasyMock.expect(mockReaderBoundary.read()).andReturn((int) character);
        }

        EasyMock.expect(mockReaderBoundary.read()).andReturn(-1);
        EasyMock.expect(mockFileReaderFactory.create(fileNameRandom)).andReturn(mockReaderBoundary);
        mockReaderBoundary.close();
    }
}
