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

import com.googlecode.pinthura.data.UrlBoundaryImpl;
import com.googlecode.pinthura.util.RandomDataChooser;
import com.googlecode.pinthura.util.builder.RandomDataChooserBuilder;
import org.junit.Before;
import org.junit.Test;

public final class AMockInjectorUnderTest {

    private Object instance;
    private RandomDataChooser randomDataChooser;
    private AMockInjectorIncubator incubator;

    @Before
    public void setup() {
        randomDataChooser = new RandomDataChooserBuilder().build();
        incubator = new AMockInjectorIncubator();
        instance = getRandomValue();
    }

    @Test
    public void shouldInjectMocksEvenWhenTheMockControlMatchesTheMockPrefix() {
        incubator.supplyPrefix("mock")
                    .supplyInstance(instance)
                    .supplyMockControl("mockControl")
                    .supplyField("mockBlah")
                    .supplyField("mockControl")
                    .supplyField("mockBloo")
                    .supplyMockField("mockBlah", getRandomValue())
                    .supplyMockField("mockBloo", getRandomValue())
                    .performInject()
                    .observe().noErrors().areReturned()
                    .done();
    }

    @Test
    public void shouldThrowAnExceptionIfMocksCantBeInjected() {
        incubator.supplyInstance(instance)
                    .supplyExceptionalCondition()
                    .performInject()
                    .observe().exception().isThrown()
                    .done();

    }

    @Test
    public void shouldInjectMocks() {
        incubator.supplyPrefix("mock")
                    .supplyInstance(instance)
                    .supplyMockControl("mockControl")
                    .supplyField("mockMoo")
                    .supplyField("mockPlay")
                    .supplyMockField("mockMoo", getRandomValue())
                    .supplyMockField("mockPlay", getRandomValue())
                    .performInject()
                    .observe().noErrors().areReturned()
                    .done();
    }

    private Object getRandomValue() {
        return randomDataChooser.chooseOneOf("this could be any instance", 111, "magic", 0.07f, new UrlBoundaryImpl());
    }
}
