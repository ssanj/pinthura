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
public final class ARandomDataCreatorCreatingNumbersTBH {

    private final IMocksControl mockControl;
    private MathBoundary mockMathBoundary;
    private RandomDataCreator creator;
    private int number;

    public ARandomDataCreatorCreatingNumbersTBH() {
        mockControl = EasyMock.createControl();
        mockMathBoundary = mockControl.createMock(MathBoundary.class);
        creator = new RandomDataCreatorBuilder().withMathBoundary(mockMathBoundary).build();
    }

    public ARandomDataCreatorCreatingNumbersTBH replay() {
        mockControl.replay();
        return this;
    }

    public ARandomDataCreatorCreatingNumbersTBH createNumber(int value) {
        number = creator.createNumber(value);
        return this;
    }

    public ARandomDataCreatorCreatingNumbersTBH createNumber(int minVal, int upperBoundary) {
        number = creator.createNumber(minVal, upperBoundary);
        return this;
    }

    public ARandomDataCreatorCreatingNumbersTBH expectRandomValue(double randomVal) {
        EasyMock.expect(mockMathBoundary.random()).andReturn(randomVal);
        return this;
    }

    public ARandomDataCreatorCreatingNumbersTBH expectFlooredValue(double intermediateValue, double flooredValue) {
        EasyMock.expect(mockMathBoundary.floor(intermediateValue)).andReturn(flooredValue);
        return this;
    }

    public ARandomDataCreatorCreatingNumbersTBH verify() {
        mockControl.verify();
        return this;
    }

    public ARandomDataCreatorCreatingNumbersTBH assertNumbersAreEqual(int expectedVal) {
        assertThat(number, equalTo(expectedVal));
        return this;
    }
}


