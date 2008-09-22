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
package com.googlecode.pinthura.annotation;

import com.googlecode.pinthura.data.UrlBoundaryFactory;
import com.googlecode.pinthura.data.UrlBoundaryImpl;
import com.googlecode.pinthura.filter.annotation.InterfaceImpl;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Method;

public final class AnAnnotationFinderUnderTest {

    private com.googlecode.pinthura.annotation.AnnotationFinder finder;

    @Before
    public void setup() {
        finder = new com.googlecode.pinthura.annotation.AnnotationFinderImpl();
    }

    @Test
    public void shouldFindAnnotationsOnASpecifiedMethod() throws NoSuchMethodException {
        Method method = UrlBoundaryFactory.class.getMethod("createUrlBoundary");
        InterfaceImpl annotation = finder.find(method, InterfaceImpl.class);

        Class<?> implementationClass = annotation.value();
        assertThat(UrlBoundaryImpl.class == implementationClass, equalTo(true));
    }

    @Test(expected = com.googlecode.pinthura.annotation.AnnotationNotFoundException.class)
    public void shouldThrowAnExceptionIfTheAnnotationIsNotFound() throws NoSuchMethodException {
        Method method = UrlBoundaryFactory.class.getMethod("createUrlBoundary", String.class);
        finder.find(method, InterfaceImpl.class);
    }
}
