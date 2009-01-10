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
import static org.hamcrest.core.IsSame.sameInstance;
import static org.junit.Assert.assertThat;
import org.junit.Before;
import org.junit.Test;

import java.lang.annotation.ElementType;

public final class ARadnomDataCreatorCreatingRandomTypesUnderTest {

    private final IMocksControl mockControl;
    private MathBoundary mockMathBoundary;
    private RandomDataCreator creator;

    public ARadnomDataCreatorCreatingRandomTypesUnderTest() {
        mockControl = EasyMock.createControl();
    }

    @Before
    public void setup() {
        mockMathBoundary = mockControl.createMock(MathBoundary.class);
        creator = new RandomDataCreatorBuilder().withMathBoundary(mockMathBoundary).build();
    }

    @Test
    public void shouldReturnAnElementFromElementType() {
        assertEnumeration(0.005, ElementType.class, ElementType.TYPE);
    }

    @Test
    public void shouldReturnAnotherElementFromElementType() {
        assertEnumeration(0.4, ElementType.class, ElementType.PARAMETER);
    }

    @Test
    public void shouldReturnAnElementFromState() {
        assertEnumeration(0.5, Thread.State.class, Thread.State.WAITING);
    }

    @Test
    public void shouldReturnAnotherElementFromState() {
        assertEnumeration(0.99, Thread.State.class, Thread.State.TERMINATED);
    }

    private <T extends Enum> void assertEnumeration(final double randomVal, final Class<T> typeClass, final T typeInstance) {
        EasyMock.expect(mockMathBoundary.random()).andReturn(randomVal);
        mockControl.replay();

        T type = creator.createType(typeClass);
        assertThat(type, sameInstance(typeInstance));

        mockControl.verify();
    }
}
