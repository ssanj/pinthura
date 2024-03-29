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
package com.googlecode.pinthura.factory.locator.deriver;

import com.googlecode.pinthura.data.Square;
import com.googlecode.pinthura.data.UrlBoundary;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;
import org.junit.Before;
import org.junit.Test;

public final class AnImplSuffixingDeriverUnderTest {

    private ClassNameDeriver locator;

    @Before
    public void setup() {
        locator = new ImplSuffixingDeriver();
    }

    @Test
    public void shouldReturnAnInterfaceNameSuffixedWithAnImpl() {
        expectImplementation(UrlBoundary.class, "com.googlecode.pinthura.data.UrlBoundaryImpl");
    }

    @Test
    public void shouldReturnAnotherInfaceNameSuffixedWithAnImpl() {
        expectImplementation(Square.class, "com.googlecode.pinthura.data.SquareImpl");
    }

    private void expectImplementation(final Class<?> interfaceClass, final String implementationClass) {
        assertThat(locator.derive(interfaceClass), equalTo(implementationClass));

    }
}
