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

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;
import org.junit.Before;
import org.junit.Test;

import java.awt.Color;
import java.util.Arrays;
import java.util.List;

public final class AnArrayzWithFiltersUnderTest {

    private Arrayz arrayz;

    @Before
    public void setup() {
        arrayz = new ArrayzImpl();
    }

    @Test
    public void shouldFilterAListOfNamesByStartingLetter() {
        List<String> names = Arrays.asList("sunny", "annie", "Sandra", "bob");
        List<String> filteredNames = arrayz.filter(names, new StartsWithSFilter());
        assertThat(filteredNames, equalTo(Arrays.asList("sunny", "Sandra")));
    }

    @Test
    public void shouldFilterAListOfNumberByValue() {
        List<Integer> numbers = Arrays.asList(5, 10, 15, 20, 25);
        List<Integer> filteredNumbers = arrayz.filter(numbers, new TenAndBelowFilter());
        assertThat(filteredNumbers, equalTo(Arrays.asList(5, 10)));
    }

    @Test
    public void shouldReturnAllElementsIfThereIsNoFilter() {
        List<Color> colours = Arrays.asList(Color.BLUE, Color.YELLOW, Color.BLACK);
        assertThat(colours, equalTo(arrayz.filter(colours, new AllColoursFilter())));
    }

    private static final class StartsWithSFilter implements ItemFilter<String> {

        public boolean include(final String item) {
            return item.startsWith("s") || item.startsWith("S");
        }
    }

    private static final class TenAndBelowFilter implements ItemFilter<Integer> {

        public boolean include(final Integer item) {
            return item <= 10;
        }
    }

    private static final class AllColoursFilter implements ItemFilter<Color> {

        public boolean include(final Color item) {
            return true;
        }
    }
}
