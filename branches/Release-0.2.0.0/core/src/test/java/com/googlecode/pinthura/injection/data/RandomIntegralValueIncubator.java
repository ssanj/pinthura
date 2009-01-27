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
package com.googlecode.pinthura.injection.data;

import com.googlecode.pinthura.annotation.SuppressionReason;
import com.googlecode.pinthura.boundary.java.lang.MathBoundary;
import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

@SuppressWarnings({"MethodReturnOfConcreteClass"})
@SuppressionReason(SuppressionReason.Reason.INCUBATOR)
public final class RandomIntegralValueIncubator {

    @SuppressWarnings({"InstanceVariableOfConcreteClass"})
    @SuppressionReason(SuppressionReason.Reason.TEST_VALUE)
    private RandomIntegralValue cut;

    private MathBoundary mockMathBoundary;
    private IMocksControl mockControl;
    private Counter mockCounter;

    private int returnedRandomValue;


    public RandomIntegralValueIncubator createRandomIntegralValue() {
        cut = new RandomIntegralValue(mockMathBoundary, mockCounter);
        return this;
    }

    public RandomIntegralValueIncubator supplyRandomSeed(double seed) {
        EasyMock.expect(mockMathBoundary.random()).andReturn(seed);
        return this;
    }


    public RandomIntegralValueIncubator performGetRandomValue() {
        mockCounter.inc();
        mockControl.replay();

        returnedRandomValue = cut.getRandomValue();
        return this;
    }

    public RandomIntegralValueIncubator observeValue(int expectedValue) {
        assertThat(returnedRandomValue, equalTo(expectedValue));
        return this;
    }

    public RandomIntegralValueIncubator isReturned() {
        return this;
    }

    public void done() {
        mockControl.verify();
    }

    public static final class RandomIntegralValue {

        private MathBoundary mathBoundary;
        private final Counter counter;

        private RandomIntegralValue(final MathBoundary mathBoundary, final Counter counter) {
            this.mathBoundary = mathBoundary;
            this.counter = counter;
        }

        public int getRandomValue() {
            double rand = mathBoundary.random();
            counter.inc();
            return (int) (rand* 200);
        }
    }

    public interface Counter {

        void inc();
    }

}
