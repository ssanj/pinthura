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

public final class ARandomDataCreatorCreatingBoundedNumbersUnderTest {

    private final IMocksControl mockControl;
    private MathBoundary mockMathBoundary;
    private RandomDataCreator creator;

    public ARandomDataCreatorCreatingBoundedNumbersUnderTest() {
        mockControl = EasyMock.createControl();
    }

    @Before
    public void setup() {
        mockMathBoundary = mockControl.createMock(MathBoundary.class);
        creator = new RandomDataCreatorBuilder().withMathBoundary(mockMathBoundary).build();
    }

    @Test
    public void shouldReturnABoundedNumber() {
        expectBoundedNumber(0.5, 10, 20, 15);
    }

    @Test
    public void shouldReturnTheMinValue() {
        expectBoundedNumber(0.0001, 300, 555, 300);
    }

    @Test
    public void shouldNotReturnTheUpperBoundary() {
        expectBoundedNumber(0.9999, 50, 100, 99);
    }

    private void expectBoundedNumber(final double randomVal, final int minVal, final int upperBoundary, final int expectedVal) {
        EasyMock.expect(mockMathBoundary.random()).andReturn(randomVal);
        mockControl.replay();

        int number = creator.createNumber(minVal, upperBoundary);
        assertThat(number, equalTo(expectedVal));

        mockControl.verify();
    }
}
