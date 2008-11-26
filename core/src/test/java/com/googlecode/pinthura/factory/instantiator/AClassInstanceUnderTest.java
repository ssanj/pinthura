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

import com.googlecode.pinthura.data.UrlBoundary;
import com.googlecode.pinthura.data.UrlBoundaryImpl;
import com.googlecode.pinthura.factory.boundary.ClassBoundaryImpl;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public final class AClassInstanceUnderTest {

    @Test
    public void shouldWrapAClassAndAnInstance() {
        expectClassAndInstance(UrlBoundary.class, new UrlBoundaryImpl());
    }

    @Test
    public void shouldWrapAnotherClassAndAnotherInstance() {
        expectClassAndInstance(Collection.class, new ArrayList());
    }

    @SuppressWarnings({ "unchecked" })
    @Test
    public void shouldWrapAClassBoundaryAndAnInstance() {
        Set instance = new HashSet();
        ClassInstance classInstance = new ClassInstanceImpl(new ClassBoundaryImpl(Set.class), instance);
        expectClassInstance(classInstance, Set.class, instance);
    }

    private <T> void expectClassAndInstance(final Class<T> clazz, final T instance) {
        ClassInstance classInstance = new ClassInstanceImpl(clazz, instance);
        expectClassInstance(classInstance, clazz, instance);
    }

    @SuppressWarnings({ "unchecked" })
    private <T> void expectClassInstance(final ClassInstance classInstance, final Class<T> clazz, final T instance) {
        assertThat((ClassBoundaryImpl<T>) classInstance.getClazz(), equalTo(new ClassBoundaryImpl<T>(clazz)));
        assertThat(clazz.cast(classInstance.getInstance()), equalTo(instance));
    }
}
