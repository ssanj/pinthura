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

import com.googlecode.pinthura.annotation.SuppressionReason;
import org.junit.Before;
import org.junit.Test;


public final class ARandomDataCreatorCreatingBoundedNumbersUnderTest {

    @SuppressWarnings("InstanceVariableOfConcreteClass")
    @SuppressionReason(SuppressionReason.Reason.INCUBATOR)
    private ARandomDataCreatorCreatingBoundedNumbersIncubator incubator;

    @Before
    public void setup() {
        incubator = new ARandomDataCreatorCreatingBoundedNumbersIncubator();
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
        incubator.supplyRandomValue(randomVal)
                 .supplyParameterMinimumValue(minVal)
                 .supplyParameterUpperLimit(upperBoundary)
                 .performCreateBoundedNumber()
                 .observeNumber(expectedVal).isReturned()
                 .done();
    }
}
