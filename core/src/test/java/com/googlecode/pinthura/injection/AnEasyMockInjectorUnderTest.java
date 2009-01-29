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
import com.googlecode.pinthura.util.RandomDataCreator;
import com.googlecode.pinthura.util.builder.RandomDataChooserBuilder;
import com.googlecode.pinthura.util.builder.RandomDataCreatorBuilder;
import org.junit.Before;
import org.junit.Test;

public final class AnEasyMockInjectorUnderTest {

    private Object randomInstance;
    private RandomDataChooser randomDataChooser;
    private AnEasyMockInjectorIncubator incubator;
    private RandomDataCreator randomDataCreator;
    private String randomPrefix;

    @Before
    public void setup() {
        randomDataChooser = new RandomDataChooserBuilder().build();
        randomDataCreator = new RandomDataCreatorBuilder().build();
        incubator = new AnEasyMockInjectorIncubator();
        randomPrefix = randomDataCreator.createString(4);
        randomInstance = getRandomValue();
    }

    @Test
    public void shouldInjectMocksEvenWhenTheMockControlMatchesTheMockPrefix() {
        incubator.supplyPrefix("mock")
                    .supplyInstance(randomInstance)
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
        incubator.supplyExceptionalCondition()
                    .performInject()
                    .observe().exception().isThrown()
                    .done();

    }

    @Test
    public void shouldInjectMocks() {
        String randomFieldName1 = randomPrefix + randomDataCreator.createString(10);
        String randomFieldName2 = randomPrefix + randomDataCreator.createString(5);
        String randomControlName = randomDataCreator.createString(7);

        incubator.supplyPrefix(randomPrefix)
                    .supplyInstance(randomInstance)
                    .supplyMockControl(randomControlName)
                    .supplyField(randomFieldName1)
                    .supplyField(randomFieldName2)
                    .supplyMockField(randomFieldName1, getRandomValue())
                    .supplyMockField(randomFieldName2, getRandomValue())
                    .performInject()
                    .observe().noErrors().areReturned()
                    .done();
    }

    private Object getRandomValue() {
        return randomDataChooser.chooseOneOf("this could be any instance", 111, "magic", 0.07f, new UrlBoundaryImpl());
    }
}
