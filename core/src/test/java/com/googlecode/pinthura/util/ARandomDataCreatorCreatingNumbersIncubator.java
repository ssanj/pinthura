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
@SuppressionReason(SuppressionReason.Reason.BUILDER_PATTERN)
public final class ARandomDataCreatorCreatingNumbersIncubator {

    private final IMocksControl mockControl;
    private MathBoundary mockMathBoundary;
    private RandomDataCreator creator;
    private int number;
    private int minVal;
    private int upperBoundary;
    private double valueToBeFloored;
    private double flooredValue;

    public ARandomDataCreatorCreatingNumbersIncubator() {
        mockControl = EasyMock.createControl();
        mockMathBoundary = mockControl.createMock(MathBoundary.class);
        creator = new RandomDataCreatorBuilder().withMathBoundary(mockMathBoundary).build();
    }

    public ARandomDataCreatorCreatingNumbersIncubator performCreateNumber() {
        mockControl.replay();
        number = creator.createNumber(upperBoundary);
        return this;
    }

    public ARandomDataCreatorCreatingNumbersIncubator performCreateBoundedNumber() {
        mockControl.replay();
        number = creator.createBoundedNumber(minVal, upperBoundary);
        return this;
    }

    public ARandomDataCreatorCreatingNumbersIncubator performCreateBoundedNegativeNumber() {                
        EasyMock.expect(mockMathBoundary.floor(valueToBeFloored)).andReturn(flooredValue);
        mockControl.replay();
        number = creator.createBoundedNumber(minVal, upperBoundary);
        return this;
    }

    public ARandomDataCreatorCreatingNumbersIncubator supplyRandomValue(double randomVal) {
        EasyMock.expect(mockMathBoundary.random()).andReturn(randomVal);
        return this;
    }

    public ARandomDataCreatorCreatingNumbersIncubator observeNumber(int expectedVal) {
        assertThat(number, equalTo(expectedVal));
        return this;
    }

    public ARandomDataCreatorCreatingNumbersIncubator supplyMinimumValue(final int minVal) {
        this.minVal = minVal;
        return this;
    }

    public ARandomDataCreatorCreatingNumbersIncubator supplyUpperLimit(final int upperBoundary) {
        this.upperBoundary = upperBoundary;
        return this;
    }

    public ARandomDataCreatorCreatingNumbersIncubator supplyValueToBeFloored(final double valueToBeFloored) {
        this.valueToBeFloored = valueToBeFloored;
        return this;
    }

    public ARandomDataCreatorCreatingNumbersIncubator supplyFlooredValue(final double flooredValue) {
        this.flooredValue = flooredValue;
        return this;
    }

    public ARandomDataCreatorCreatingNumbersIncubator isReturned() {
        return this;
    }

    public void execute() {
        mockControl.verify();
    }
}


