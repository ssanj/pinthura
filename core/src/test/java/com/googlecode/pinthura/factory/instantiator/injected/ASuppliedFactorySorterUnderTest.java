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

import com.googlecode.pinthura.annotation.SuppressionReason;
import org.junit.Before;
import org.junit.Test;

public final class ASuppliedFactorySorterUnderTest {

    @SuppressWarnings({"InstanceVariableOfConcreteClass"})
    @SuppressionReason(SuppressionReason.Reason.INCUBATOR)
    private ASuppliedFactorySorterIncubator incubator;

    @Before
    public void setup() {
        incubator = new ASuppliedFactorySorterIncubator();
    }

    @SuppressWarnings("unchecked")
    @Test
    public void shouldPlaceDynamicInstancesInTheMiddle() {
        //[*][][*]
        incubator.supplyClassInstaceArrayOfSize(3).
                supplyInstance().atIndex(0).
                supplyInstance().atIndex(2).
                performSort().
                observe().index(0).isUnchanged().
                observe().index(1).isCreated().dynamically().
                observe().index(2).isUnchanged().
                done();
    }

    @SuppressWarnings("unchecked")
    @Test
    public void shouldPlaceDynamicInstancesAtTheEnds() {
        //[][][*][]
        incubator.supplyClassInstaceArrayOfSize(4).
                supplyInstance().atIndex(2).
                performSort().
                observe().index(0).isCreated().dynamically().
                observe().index(1).isCreated().dynamically().
                observe().index(2).isUnchanged().
                observe().index(3).isCreated().dynamically().
                done();
    }

    @Test
    public void shouldPlaceDynamicInstancesThroughout() {
        //[][][]
        incubator.supplyClassInstaceArrayOfSize(3).
                performSort().
                observe().index(0).isCreated().dynamically().
                observe().index(1).isCreated().dynamically().
                observe().index(2).isCreated().dynamically().
                done();
    }

    @Test
    public void shouldPlaceDynamicInstacesInTheGaps() {
        //[*][][*][][*]
        incubator.supplyClassInstaceArrayOfSize(5).
                supplyInstance().atIndex(0).
                supplyInstance().atIndex(2).
                supplyInstance().atIndex(4).
                performSort().
                observe().index(0).isUnchanged().
                observe().index(1).isCreated().dynamically().
                observe().index(2).isUnchanged().
                observe().index(3).isCreated().dynamically().
                observe().index(4).isUnchanged().
                done();

    }
}
