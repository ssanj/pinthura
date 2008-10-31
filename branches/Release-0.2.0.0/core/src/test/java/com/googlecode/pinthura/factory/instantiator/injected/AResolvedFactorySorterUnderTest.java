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

    private ResolvedFactorySorterTestBehaviourHandler behaviourHandler;

    @Before
    public void setup() {
        behaviourHandler = new ResolvedFactorySorterTestBehaviourHandler();
    }

    @Test
    public void shouldSortFactoriesInTheMiddle() {
        //CHECKSTYLE_OFF
        expectSortedInstances(1, 3, 5);
        //CHECKSTYLE_ON
    }

    @Test
    public void shouldSortFactoriesAtTheEdges() {
        //CHECKSTYLE_OFF
        expectSortedInstances(0, 4, 5);
        //CHECKSTYLE_ON
    }

    @Test
    public void shouldSortFactoriesWhenThereAreNoGaps() {
        expectSortedInstances(0, 1, 2);
    }

    @Test(expected = ArrayIndexOutOfBoundsException.class)
    public void shouldThrowExceptionIfTheIndecesAreOutOfBounds() {
        //CHECKSTYLE_OFF
        expectSortedInstances(1, 11, 2);
        //CHECKSTYLE_ON
    }

    @SuppressWarnings({ "unchecked" })
    private void expectSortedInstances(final int firstIndex, final int secondIndex, final int noOfParameters) {
        ClassInstance[] classInstances = new ClassInstance[noOfParameters];

        behaviourHandler.addFactory(ShapeFactory.class, firstIndex);
        behaviourHandler.addFactory(UrlBoundaryFactory.class, secondIndex);
        behaviourHandler.replay();

        behaviourHandler.sort(classInstances);
        behaviourHandler.expectFirstClassInstanceIsEqual(classInstances[firstIndex]);
        behaviourHandler.expectSecondClassInstanceIsEqual(classInstances[secondIndex]);

        behaviourHandler.verify();
    }
}
