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
package com.googlecode.pinthura.factory;

import com.googlecode.pinthura.data.UrlBoundaryFactory;
import com.googlecode.pinthura.io.FileReaderFactory;
import com.googlecode.pinthura.util.builder.RandomDataChooserBuilder;
import com.googlecode.pinthura.annotation.SuppressionReason;
import org.junit.Before;
import org.junit.Test;

public final class AFactoryCreatorUnderTest {

    private Class<?> randomFactoryClass;
    @SuppressWarnings({"InstanceVariableOfConcreteClass"})
    @SuppressionReason(SuppressionReason.Reason.INCUBATOR)
    private AFactoryCreatorIncubator incubator;

    @Before
    @SuppressWarnings("unchecked")
    @SuppressionReason(SuppressionReason.Reason.CANT_INFER_GENERICS)
    public void setup() {
        randomFactoryClass = new RandomDataChooserBuilder().build().chooseOneOf(
                                UrlBoundaryFactory.class,
                                InvocationFactory.class,
                                FileReaderFactory.class);
        incubator = new AFactoryCreatorIncubator();
    }

    @Test
    public void shouldCreateAnInstanceForAGivenFactoryInterface() {
        incubator.supplyParameterFactoryClass(randomFactoryClass).
                    performCreate().
                    observe().expectedInstance().isCreated().
                    done();
    }

    @Test
    public void shouldCacheCreatedInstances() {
        incubator.supplyParameterFactoryClass(randomFactoryClass).
                    performCreate().
                    observe().expectedInstance().isCached().
                    done();
    }
}
