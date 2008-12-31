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
    @SuppressionReason(SuppressionReason.Reason.TEST_BEHAVIOUR_HANDLER)
    private ARandomDataCreatorCreatingNumbersTBH handler;

    @Before
    public void setup() {
        handler = new ARandomDataCreatorCreatingNumbersTBH();
    }

    @Test
    public void shouldReturnTheMinValue() {
        handler.expectRandomValue(0.001).expectFlooredValue(-9.995, -10).replay();
        handler.createNumber(-10, -5).assertNumbersAreEqual(-10).verify();
    }

    @Test
    public void shouldNotReturnTheUpperBoundary() {
        handler.expectRandomValue(0.999999).expectFlooredValue(-100.0001, -101).replay();
        handler.createNumber(-200, -100).assertNumbersAreEqual(-101).verify();
    }

    @Test
    public void shouldReturnANumberBetweenNegativeAndPositveBounds() {
        handler.expectRandomValue(0.4).expectFlooredValue(-1, -1).replay();
        handler.createNumber(-5, 5).assertNumbersAreEqual(-1).verify();
    }
}
