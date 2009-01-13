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
import com.googlecode.pinthura.boundary.java.lang.MathBoundary;
import com.googlecode.pinthura.util.builder.RandomDataCreatorBuilder;
import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

@SuppressWarnings({"MethodReturnOfConcreteClass"})
@SuppressionReason(SuppressionReason.Reason.METHOD_CHAIN)
public final class ARandomDataCreatorCreatingBoundedNumbersIncubator {

    private final IMocksControl mockControl;
    private MathBoundary mockMathBoundary;
    private RandomDataCreator creator;
    private int number;
    private int minVal;
    private int upperBoundary;
    private Double valueToBeFloored;
    private Double flooredValue;

    public ARandomDataCreatorCreatingBoundedNumbersIncubator() {
        mockControl = EasyMock.createControl();
        mockMathBoundary = mockControl.createMock(MathBoundary.class);
        creator = new RandomDataCreatorBuilder().withMathBoundary(mockMathBoundary).build();
    }

    public ARandomDataCreatorCreatingBoundedNumbersIncubator performCreateBoundedNumber() {
        if (isNegativeValue()) {
            EasyMock.expect(mockMathBoundary.floor(valueToBeFloored)).andReturn(flooredValue);
        }

        mockControl.replay();
        number = creator.createBoundedNumber(minVal, upperBoundary);
        return this;
    }

    public ARandomDataCreatorCreatingBoundedNumbersIncubator supplyRandomValue(double randomVal) {
        EasyMock.expect(mockMathBoundary.random()).andReturn(randomVal);
        return this;
    }

    public ARandomDataCreatorCreatingBoundedNumbersIncubator observeNumber(int expectedVal) {
        assertThat(number, equalTo(expectedVal));
        return this;
    }

    public ARandomDataCreatorCreatingBoundedNumbersIncubator supplyParameterMinimumValue(int minVal) {
        this.minVal = minVal;
        return this;
    }

    public ARandomDataCreatorCreatingBoundedNumbersIncubator supplyParameterUpperLimit(int upperBoundary) {
        this.upperBoundary = upperBoundary;
        return this;
    }

    public ARandomDataCreatorCreatingBoundedNumbersIncubator supplyValueToBeFloored(double valueToBeFloored) {
        this.valueToBeFloored = valueToBeFloored;
        return this;
    }

    public ARandomDataCreatorCreatingBoundedNumbersIncubator supplyFlooredValue(double flooredValue) {
        this.flooredValue = flooredValue;
        return this;
    }

    public ARandomDataCreatorCreatingBoundedNumbersIncubator isReturned() {
        return this;
    }

    public void done() {
        mockControl.verify();
    }

    private boolean isNegativeValue() {
        return valueToBeFloored != null && flooredValue != null;
    }
}


