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
package com.googlecode.pinthura.traverser.list;

import com.googlecode.pinthura.data.UrlBoundary;
import com.googlecode.pinthura.traverser.collection.CollectionTraverserImpl;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;
import org.junit.Test;

import java.sql.Connection;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

//Checkstyle has been turned off for "Magic Numbers".
public final class ACollectionTraverserUnderIntTest {

    private static final int NO_OF_PACKAGES = 4;

    @Test
    public void shouldSumAListOfNumbers() {
        //CHECKSTYLE_OFF
        Collection<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);
        //CHECKSTYLE_ON

        Integer result = new CollectionTraverserImpl<Integer, Integer>().forEach(numbers, new TotalHandler());
        //CHECKSTYLE_OFF
        assertThat(result, equalTo(15));
        //CHECKSTYLE_ON
    }

    @Test
    public void shouldDisplayAListOfFormattedCharacters() {
        Collection<Character> characters = Arrays.asList('A', 'B', 'C', 'D', 'E');

        String result = new CollectionTraverserImpl<Character, String>().forEach(characters, new CharacterFormatter());
        assertThat(result, equalTo("[A, B, C, D, E]"));
    }

    @Test
    public void shouldDisplayAListOfPackageNames() {
        List<Class<?>> classes = Arrays.asList(UrlBoundary.class, Connection.class, String.class, Arrays.class);

        List<String> result = new CollectionTraverserImpl<Class<?>, List<String>>().forEach(classes, new PackageNameRetreiver());

        assertThat(result.size(), equalTo(NO_OF_PACKAGES));
        assertThat(result.get(0), equalTo("com.googlecode.pinthura.data"));
        assertThat(result.get(1), equalTo("java.sql"));
        //CHECKSTYLE_OFF
        assertThat(result.get(2), equalTo("java.lang"));
        assertThat(result.get(3), equalTo("java.util"));
        //CHECKSTYLE_ON
    }
}
