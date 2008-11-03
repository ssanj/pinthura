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
package com.googlecode.pinthura.factory.instantiator;

import com.googlecode.pinthura.factory.Factory;
import com.googlecode.pinthura.factory.InjectedFactory;
import org.junit.Before;
import org.junit.Test;

public final class AnAnnotatedFactoryExtractorUnderTest {

    private AnnotatedFactoryExtractorTestBehaviourHandler behaviour;

    @Before
    public void setup() {
        behaviour = new AnnotatedFactoryExtractorTestBehaviourHandler();
    }

    @Test
    public void shouldExtractFactoryInjectedFactoryAnnotations() {
        behaviour.createFactory().createFactory().replay();

        InjectedFactory result = behaviour.extract();
        behaviour.expectAnnotatedFactoriesAreReturned(result);

        behaviour.verify();
    }

    @Test
    public void shouldExtractFactoryAnnotations() {
        behaviour.createFactory().replay();

        Factory[] factories = behaviour.extractFactories();
        behaviour.expectAnnotatedFactoriesAreReturned(factories);

        behaviour.verify();
    }
}
