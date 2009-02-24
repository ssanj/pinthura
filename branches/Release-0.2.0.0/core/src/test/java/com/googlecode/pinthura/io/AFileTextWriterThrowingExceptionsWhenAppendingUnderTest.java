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
import com.googlecode.pinthura.util.RandomDataCreator;
import com.googlecode.pinthura.util.builder.RandomDataCreatorBuilder;
import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public final class AFileTextWriterThrowingExceptionsWhenAppendingUnderTest {

    private IMocksControl mockControl;
    private FileTextWriter writer;
    private FileWriterFactory mockFileWriterFactory;
    private List<String> randomDataList;
    private String randomFileName;

    @Before
    public void setup() {
        mockControl = EasyMock.createControl();
        mockFileWriterFactory = mockControl.createMock(FileWriterFactory.class);
        RandomDataCreator randomDataCreator = new RandomDataCreatorBuilder().build();
        writer = new FileTextWriterImpl(mockFileWriterFactory);
        randomFileName = randomDataCreator.createFileName(15);
        randomDataList = Arrays.asList(randomDataCreator.createString(randomDataCreator.createNumber(25)));
    }

    @SuppressWarnings("ThrowableInstanceNeverThrown")
    @SuppressionReason(SuppressionReason.Reason.TEST_VALUE)
    @Test(expected = FileTextWriterException.class)
    public void shouldThrowAnExceptionIfFileWriterFactoryThrowsAnException() {
        EasyMock.expect(mockFileWriterFactory.create(randomFileName, true)).andThrow(new RuntimeException());
        mockControl.replay();

        writer.append(randomFileName, randomDataList);
    }

    @Test(expected = FileTextWriterException.class)
    public void shouldThrowAnExceptionIfFileWriterFactoryReturnsNull() {
        EasyMock.expect(mockFileWriterFactory.create(randomFileName, true)).andReturn(null);
        mockControl.replay();

        writer.append(randomFileName, randomDataList);
    }
}
