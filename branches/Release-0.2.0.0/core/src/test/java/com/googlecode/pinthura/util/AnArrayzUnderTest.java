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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public final class AnArrayzUnderTest {

    private Arrayz arrayz;

    @Before
    public void setup() {
        arrayz = new ArrayzImpl();
    }

    @Test
    public void shouldCreateAnArrayFromASingleObject() {
        String[] array = arrayz.fromObjects("one");
        assertThat(array, equalTo(new String[] { "one" }));
    }

    @Test
    public void shouldCreateAnArrayFromMultipleObjects() {
        Integer[] numbers = arrayz.fromObjects(1, 2, 3, 4, 5, 6);
        assertThat(numbers, equalTo(new Integer[] {1, 2, 3, 4, 5, 6}));
    }

    @Test
    public void shouldReturnAnEmptyArrayIfNoObectsAreSupplied() {
        assertThat(arrayz.<String>fromObjects().length, equalTo(0));
    }

    @Test
    public void shouldReturnAnArrayOfACollectionWithOneItem() {
        Set<Double> set = new HashSet<Double>();
        set.add(5.2d);
        assertThat(arrayz.fromCollection(set, Double.class), equalTo(new Double[] { 5.2 }));
    }

    @Test
    public void shouldReturnAnArrayOfAListWithMultipleItems() {
        String[] words = arrayz.fromCollection(Arrays.asList("what", "will", "this", "be"), String.class);
        assertThat(words, equalTo(new String[] { "what", "will", "this", "be" }));
    }

    @Test
    public void shouldReturnAnEmptyArrayForAnEmptyList() {
        assertThat(arrayz.fromCollection(new ArrayList<Integer>(), Integer.class).length, equalTo(0));
    }

    //TODO: Add tests for filter.
}
