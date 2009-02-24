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
package com.googlecode.pinthura.util;

import com.googlecode.pinthura.boundary.java.lang.MathBoundary;
import com.googlecode.pinthura.util.builder.RandomDataCreatorBuilder;
import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;
import org.junit.Before;
import org.junit.Test;

public final class ARandomDataCreatorCreatingStringsUnderTest {

    private IMocksControl mockControl;
    private MathBoundary mockMathBoundary;
    private RandomDataCreator randomDataCreator;

    @Before
    public void setup() {
        mockControl = EasyMock.createControl();
        mockMathBoundary = mockControl.createMock(MathBoundary.class);
        randomDataCreator = new RandomDataCreatorBuilder().withMathBoundary(mockMathBoundary).build();
    }

    @Test
    public void shouldCreateARandomStringOfAGivenLength() {
        EasyMock.expect(mockMathBoundary.random()).andReturn(0.2);
        EasyMock.expect(mockMathBoundary.random()).andReturn(0.6);
        EasyMock.expect(mockMathBoundary.random()).andReturn(0.7);
        EasyMock.expect(mockMathBoundary.random()).andReturn(0.4);
        EasyMock.expect(mockMathBoundary.random()).andReturn(0.5);
        mockControl.replay();

        String result = randomDataCreator.createString(5);
        assertThat(result.length(), equalTo(5));
        assertThat(result, equalTo("KfkUa"));

        mockControl.verify();
    }

    @Test
    public void shouldCreateAnotherRandomStringOfAGivenLength() {
        EasyMock.expect(mockMathBoundary.random()).andReturn(0.8);
        EasyMock.expect(mockMathBoundary.random()).andReturn(0.3);
        mockControl.replay();

        String result = randomDataCreator.createString(2);
        assertThat(result.length(), equalTo(2));
        assertThat(result, equalTo("pP"));

        mockControl.verify();
    }
}
