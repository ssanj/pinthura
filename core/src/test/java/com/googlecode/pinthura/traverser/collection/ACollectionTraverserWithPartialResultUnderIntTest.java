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
package com.googlecode.pinthura.traverser.collection;

import com.googlecode.pinthura.traverser.CollectionTraverser;
import com.googlecode.pinthura.bean.PathEvaluatorImpl;
import com.googlecode.pinthura.bean.PropertyFinderImpl;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collection;

public final class ACollectionTraverserWithPartialResultUnderIntTest {

    private CollectionTraverser traverser;

    @Before
    public void setup() {
        traverser = new CollectionTraverserImpl(new PathEvaluatorImpl(new PropertyFinderImpl()));
    }

    @Test
    public void shouldSumAListOfNumbers() {
        //CHECKSTYLE_OFF
        Collection<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);
        //CHECKSTYLE_ON

        Integer result = traverser.forEach(numbers, new TotalHandlerWithPartialResult(), 0);

        //CHECKSTYLE_OFF
        assertThat(result, equalTo(15));
        //CHECKSTYLE_ON
    }
}
