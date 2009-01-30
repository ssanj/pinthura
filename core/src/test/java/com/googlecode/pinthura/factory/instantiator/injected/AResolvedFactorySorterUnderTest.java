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
package com.googlecode.pinthura.factory.instantiator.injected;

import com.googlecode.pinthura.data.ShapeFactory;
import com.googlecode.pinthura.data.UrlBoundaryFactory;
import com.googlecode.pinthura.factory.instantiator.ClassInstance;
import org.junit.Before;
import org.junit.Test;

public final class AResolvedFactorySorterUnderTest {

    private ResolvedFactorySorterIncubator incubator;

    @Before
    public void setup() {
        incubator = new ResolvedFactorySorterIncubator();
    }

    @Test
    public void shouldSortFactoriesInTheMiddle() {
        expectSortedInstances(1, 3, 5);
    }

    @Test
    public void shouldSortFactoriesAtTheEdges() {
        expectSortedInstances(0, 4, 5);
    }

    @Test
    public void shouldSortFactoriesWhenThereAreNoGaps() {
        expectSortedInstances(0, 1, 2);
    }

    @Test(expected = ArrayIndexOutOfBoundsException.class)
    public void shouldThrowExceptionIfTheIndecesAreOutOfBounds() {
        expectSortedInstances(1, 11, 2);
    }

    @SuppressWarnings({ "unchecked" })
    private void expectSortedInstances(final int firstIndex, final int secondIndex, final int noOfParameters) {
        ClassInstance[] classInstances = new ClassInstance[noOfParameters];

        incubator.supplyFactory(ShapeFactory.class).forIndex(firstIndex)
                    .supplyFactory(UrlBoundaryFactory.class).forIndex(secondIndex)
                    .supplyParameterClassInstances(classInstances)
                    .performSort()
                    .observe().instanceAt(firstIndex).is().classInstance(classInstances[firstIndex])
                    .observe().instanceAt(secondIndex).is().classInstance(classInstances[secondIndex])
                    .observe().other().instances().are().untouched()
                    .done();
    }
}
