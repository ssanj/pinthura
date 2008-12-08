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
import com.googlecode.pinthura.util.RandomDataCreator;
import com.googlecode.pinthura.util.RandomDataCreatorImpl;
import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

public final class AFileTextWriterThrowingExceptionsWhenWritingUnderTest {

    private final IMocksControl mockControl = EasyMock.createControl();
    private FileTextWriter writer;
    private FileWriterFactory mockFileWriterFactory;
    private RandomDataCreator randomDataCreator;

    @Before
    public void setup() {
        mockFileWriterFactory = mockControl.createMock(FileWriterFactory.class);
        randomDataCreator = new RandomDataCreatorImpl(new MathBoundaryImpl());
        writer = new FileTextWriterImpl(mockFileWriterFactory);
    }

    @Test(expected = FileTextWriterException.class)
    public void shouldThrowAnExceptionIfIfFileWriterFactoryThrowsAnExceptionWhenWriting() {
        String fileName = expectFileFactoryThrowsException(false);
        writer.write(fileName, Arrays.asList(randomDataCreator.createString(8)));
    }

    @Test(expected = FileTextWriterException.class)
    public void shouldThrowAnExceptionIfFileWriterFactoryReturnsNullWhenWriting() {
        String fileName = expectFileFactoryReturnsNull(false);
        writer.write(fileName, Arrays.asList(randomDataCreator.createString(20)));
    }

    @Test(expected = FileTextWriterException.class)
    public void shouldThrowAnExceptionIfFileWriterFactoryThrowsAnExceptionWhenAppending() {
        String fileName = expectFileFactoryThrowsException(true);
        writer.append(fileName, Arrays.asList(randomDataCreator.createString(4)));
    }

    @Test(expected = FileTextWriterException.class)
    public void shouldThrowAnExceptionIfFileWriterFactoryReturnsNullWhenAppending() {
        String fileName = expectFileFactoryReturnsNull(true);
        writer.append(fileName, Arrays.asList(randomDataCreator.createString(16)));
    }

    private String expectFileFactoryReturnsNull(boolean append) {
        String fileName = "foo.bar.fib";
        EasyMock.expect(mockFileWriterFactory.create(fileName, append)).andReturn(null);
        mockControl.replay();
        return fileName;
    }

    @SuppressWarnings({ "ThrowableInstanceNeverThrown" })
    private String expectFileFactoryThrowsException(boolean append) {
        String fileName = "foo.bar.fib";
        EasyMock.expect(mockFileWriterFactory.create(fileName, append)).andThrow(new RuntimeException());
        mockControl.replay();
        return fileName;
    }
}
