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
import com.googlecode.pinthura.util.builder.RandomDataChooserBuilder;
import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;
import org.junit.Before;
import org.junit.Test;

public final class ARandomDataChooserUnderTest {

    private IMocksControl mockControl;
    private MathBoundary mockMathBoundary;
    private RandomDataChooser randomDataChooser;

    @Before
    public void setup() {
        mockControl = EasyMock.createControl();
        mockMathBoundary = mockControl.createMock(MathBoundary.class);
        randomDataChooser = new RandomDataChooserBuilder().withMathBoundary(mockMathBoundary).build();
    }

    @Test
    public void shouldChooseARandomString() {
        EasyMock.expect(mockMathBoundary.random()).andReturn(0.1);
        mockControl.replay();

        String result = randomDataChooser.chooseOneOf("one", "two");
        assertThat(result, equalTo("one"));

        mockControl.verify();
    }

    @Test
    public void shouldChooseARandomInt() {
        EasyMock.expect(mockMathBoundary.random()).andReturn(0.6);
        mockControl.replay();

        int result = randomDataChooser.chooseOneOf(1, 2, 3, 4, 5);
        assertThat(result, equalTo(4));

        mockControl.verify();
    }
}
