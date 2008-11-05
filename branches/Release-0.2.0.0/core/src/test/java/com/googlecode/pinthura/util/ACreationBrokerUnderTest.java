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

import com.googlecode.pinthura.data.Shape;
import com.googlecode.pinthura.data.SquareImpl;
import com.googlecode.pinthura.data.UrlBoundary;
import com.googlecode.pinthura.data.UrlBoundaryImpl;
import static junit.framework.Assert.fail;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;
import org.junit.Before;
import org.junit.Test;

public final class ACreationBrokerUnderTest {

    private CreationBroker creationBroker;

    @Before
    public void setup() {
        creationBroker = new CreationBrokerImpl();
    }

    @Test
    public void shouldStoreAndRetrieveAType() {
        expectInstance(Shape.class, new SquareImpl(1));
    }

    @Test
    public void shouldStoreAndRetrieveAnotherType() {
        expectInstance(UrlBoundary.class, new UrlBoundaryImpl());
    }

    @Test
    public void shouldThrowAnExceptionForWhenRetrievingAnUnstoredType() {
        try {
            creationBroker.getInstanceFor(String.class);
            fail();
        } catch (CreationBrokerException e) {
             assertThat(e.getMessage(), equalTo("Could not find instance for class java.lang.String"));
        }
    }

    @SuppressWarnings({ "unchecked" })
    private <T> void expectInstance(final Class<T> clazz, final T instance) {
        creationBroker.setInstance(clazz, instance);
        assertThat(creationBroker.getInstanceFor(clazz), equalTo(instance));
    }
}
