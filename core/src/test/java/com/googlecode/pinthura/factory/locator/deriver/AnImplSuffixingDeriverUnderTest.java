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

import com.googlecode.pinthura.factory.boundary.ClassBoundary;
import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;
import org.junit.Before;
import org.junit.Test;

public final class AnImplSuffixingDeriverUnderTest {

    private final IMocksControl mockControl = EasyMock.createControl();

    private ClassNameDeriver locator;
    private ClassBoundary mockClassBoundary;

    @Before
    public void setup() {
        locator = new ImplSuffixingDeriver();
        mockClassBoundary = mockControl.createMock(ClassBoundary.class);
    }

    @Test
    public void shouldReturnAnInterfaceNameSuffixedWithAnImpl() {
        expectImplementation("com.googlecode.pinthura.data.UrlBoundary.Blah", "com.googlecode.pinthura.data.UrlBoundary.BlahImpl");
    }

    @Test
    public void shouldReturnAnotherInfaceNameSuffixedWithAnImpl() {
        expectImplementation("java.lang.Comparable", "java.lang.ComparableImpl");
    }

    private void expectImplementation(final String interfaceClass, final String implementationClass) {
        EasyMock.expect(mockClassBoundary.getName()).andReturn(interfaceClass);
        mockControl.replay();

        assertThat(locator.derive(mockClassBoundary), equalTo(implementationClass));

        mockControl.verify();
    }
}
