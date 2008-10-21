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
import com.googlecode.pinthura.factory.boundary.ClassBoundaryImpl;
import com.googlecode.pinthura.factory.boundary.ClassBoundary;
import com.googlecode.pinthura.factory.MethodParam;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;
import org.junit.Before;
import org.junit.Test;
import org.easymock.EasyMock;
import org.easymock.IMocksControl;

public final class AnImplSuffixingDeriverUnderTest {

    private final IMocksControl mockControl = EasyMock.createControl();
    private ClassNameDeriver locator;
    private MethodParam mockMethodParam;

    @Before
    public void setup() {
        mockMethodParam = mockControl.createMock(MethodParam.class);
        locator = new ImplSuffixingDeriver();
    }

    @Test
    public void shouldReturnAnInterfaceNameSuffixedWithAnImpl() {
        expectImplementation(new ClassBoundaryImpl<UrlBoundary>(UrlBoundary.class), "com.googlecode.pinthura.data.UrlBoundaryImpl");
    }

    @Test
    public void shouldReturnAnotherInfaceNameSuffixedWithAnImpl() {
        expectImplementation(new ClassBoundaryImpl<Square>(Square.class), "com.googlecode.pinthura.data.SquareImpl");
    }

    private <T> void expectImplementation(final ClassBoundary<T> interfaceClass, final String implementationClass) {
        EasyMock.expect(mockMethodParam.getReturnType());
        EasyMock.expectLastCall().andReturn(interfaceClass);
        mockControl.replay();

        assertThat(locator.derive(mockMethodParam), equalTo(implementationClass));

        mockControl.verify();
    }
}
