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
package com.googlecode.pinthura.injection;

import com.googlecode.pinthura.annotation.SuppressionReason;
import com.googlecode.pinthura.injection.data.RandomIntegralValueIncubator;
import org.junit.Before;
import org.junit.Test;

public final class AMockInjectorUnderTest {

    @SuppressWarnings({"InstanceVariableOfConcreteClass"})
    @SuppressionReason(SuppressionReason.Reason.INCUBATOR)
    private RandomIntegralValueIncubator incubator;

    @Before
    public void setup() {
        incubator = new MockInjectorImpl().inject(new RandomIntegralValueIncubator());
    }

    @Test
    public void shouldInjectMocksIntoSuppliedInstance() {
        incubator.createRandomIntegralValue()
                 .supplyRandomSeed(0.5)
                 .performGetRandomValue()
                 .observeValue(100)
                 .isReturned()
                 .done();
    }
}
