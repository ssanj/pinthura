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
package com.googlecode.pinthura.injection;

import com.googlecode.pinthura.boundary.java.lang.ClassBoundary;
import com.googlecode.pinthura.boundary.java.lang.ClassBoundaryImpl;
import com.googlecode.pinthura.data.UrlBoundary;
import org.easymock.IMocksControl;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNot.not;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.hamcrest.core.IsSame.sameInstance;
import static org.junit.Assert.assertThat;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Proxy;

public final class AnEasyMockWrapperUnderTest {

    private EasyMockWrapper easyMock;

    @Before
    public void setup() {
        easyMock = new EasyMockWrapperImpl();
    }

    @Test
    public void shouldCreateAMockControl() {
        IMocksControl mocksControl1 = easyMock.createControl();
        IMocksControl mocksControl2 = easyMock.createControl();

        assertThat(mocksControl1, notNullValue());
        assertThat(mocksControl2, notNullValue());
        assertThat(mocksControl1, not(sameInstance(mocksControl2)));
    }

    @Test
    public void shouldCreateAMockOfASuppliedInterface() {
        assertMock(new ClassBoundaryImpl<IMocksControl>(IMocksControl.class));
    }

    @Test
    public void shouldCreateAMockOfAnotherSuppliedInterface() {
        assertMock(new ClassBoundaryImpl<UrlBoundary>(UrlBoundary.class));
    }

    private <T> void assertMock(final ClassBoundary<T> clazz) {
        T mock = easyMock.createMock(easyMock.createControl(), clazz);
        assertThat(Proxy.isProxyClass(mock.getClass()), equalTo(true));
    }

}
