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

public final class ARandomDataCreatorCreatingBoundedNegativeNumbersUnderTest {

    @SuppressWarnings("InstanceVariableOfConcreteClass")
    @SuppressionReason(SuppressionReason.Reason.INCUBATOR)
    private ARandomDataCreatorCreatingNumbersIncubator incubator;

    @Before
    public void setup() {
        incubator = new ARandomDataCreatorCreatingNumbersIncubator();
    }

    @Test
    public void shouldReturnTheMinValue() {
        incubator.supplyRandomValue(0.001)
                 .supplyValueToBeFloored(-9.995)
                 .supplyFlooredValue(-10)
                 .supplyMinimumValue(-10)
                 .supplyUpperLimit(-5)
                 .performCreateBoundedNegativeNumber()
                 .observeNumber(-10).isReturned()
                 .execute();
    }

    @Test
    public void shouldNotReturnTheUpperBoundary() {
        incubator.supplyRandomValue(0.999999)
                 .supplyValueToBeFloored(-100.0001)
                 .supplyFlooredValue(-101)
                 .supplyMinimumValue(-200)
                 .supplyUpperLimit(-100)
                 .performCreateBoundedNegativeNumber()
                 .observeNumber(-101).isReturned()
                 .execute();
    }

    @Test
    public void shouldReturnANumberBetweenNegativeAndPositveBounds() {
        incubator.supplyRandomValue(0.4)
                 .supplyValueToBeFloored(-1)
                 .supplyFlooredValue(-1)
                 .supplyMinimumValue(-5)
                 .supplyUpperLimit(5)
                 .performCreateBoundedNegativeNumber()
                 .observeNumber(-1).isReturned()
                 .execute();
    }
}
