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

public final class ARandomDataCreatorCreatingNumbersUnderTest {

    @SuppressWarnings("InstanceVariableOfConcreteClass")
    @SuppressionReason(SuppressionReason.Reason.TEST_BEHAVIOUR_HANDLER)
    private ARandomDataCreatorCreatingNumbersTBH handler;

    @Before
    public void setup() {
        handler = new ARandomDataCreatorCreatingNumbersTBH();
    }

    @Test
    public void shouldReturnZero() {
        expectNumber(0.001, 255, 0);
    }

    @Test
    public void shouldReturnAPositiveNumber() {
        expectNumber(0.98, 1000, 980);
    }

    @Test
    public void shouldReturnANegativeNumber() {
        expectNumber(0.5, -500, -250);
    }

    @Test
    public void shouldNeverReturnTheSuppliedValue() {
        expectNumber(0.9999, 50, 49);
    }

    private void expectNumber(final double randomValue, final int value, final int expectedVal) {
        handler.expectRandomValue(randomValue);
        handler.replay();

        handler.createNumber(value);
        handler.assertNumbersAreEqual(expectedVal);

        handler.verify();
    }
}
