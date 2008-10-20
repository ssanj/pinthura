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
package com.googlecode.pinthura.factory.creator;

import com.googlecode.pinthura.data.UrlBoundary;
import com.googlecode.pinthura.data.UrlBoundaryImpl;
import com.googlecode.pinthura.factory.MethodParam;
import com.googlecode.pinthura.factory.boundary.ClassBoundary;
import com.googlecode.pinthura.factory.locator.deriver.ClassNameDeriver;
import com.googlecode.pinthura.filter.MatchNotFoundException;
import static junit.framework.Assert.fail;
import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import static org.hamcrest.core.IsEqual.equalTo;
import org.hamcrest.core.IsSame;
import static org.junit.Assert.assertThat;
import org.junit.Before;
import org.junit.Test;

public final class ASimpleInstantiatorUnderTest {

    private static final String URL_BOUNDARY_IMPL   = "com.googlecode.pinthura.data.UrlBoundaryImpl";
    private static final String COLLECTION_IMPL     = "java.util.CollectionImpl";

    private final IMocksControl mockControl = EasyMock.createControl();

    private MethodParam mockMethodParam;
    private ClassNameDeriver mockClassNameDeriver;
    private SimpleInstantiator instantiator;

    @Before
    public void setup() {
        mockMethodParam = mockControl.createMock(MethodParam.class);
        mockClassNameDeriver = mockControl.createMock(ClassNameDeriver.class);

        instantiator = new SimpleInstantiator(mockClassNameDeriver);
    }

    @SuppressWarnings({ "unchecked" })
    @Test
    public void shouldCreateAnInstanceOfAnExistingClass() {
        ClassBoundary mockClassBoundary = mockControl.createMock(ClassBoundary.class);
        EasyMock.expect(mockMethodParam.getReturnType()).andReturn(mockClassBoundary);
        EasyMock.expect(mockClassNameDeriver.derive(mockClassBoundary)).andReturn(URL_BOUNDARY_IMPL);

        ClassBoundary mockLoadedClass = mockControl.createMock(ClassBoundary.class);
        EasyMock.expect(mockClassBoundary.forName(URL_BOUNDARY_IMPL)).andReturn(mockLoadedClass);

        UrlBoundary urlBoundary = new UrlBoundaryImpl();
        EasyMock.expect(mockLoadedClass.newInstance()).andReturn(urlBoundary);
        mockControl.replay();

        UrlBoundary result = (UrlBoundary) instantiator.filter(mockMethodParam);
        assertThat(result, IsSame.sameInstance(urlBoundary));

        mockControl.verify();
    }

    @SuppressWarnings({ "unchecked", "ThrowableInstanceNeverThrown" })
    @Test
    public void shouldThrowAnExceptionIfTheInstanceCantBeCreated() {
        ClassBoundary mockClassBoundary = mockControl.createMock(ClassBoundary.class);
        EasyMock.expect(mockMethodParam.getReturnType()).andReturn(mockClassBoundary);
        EasyMock.expect(mockClassNameDeriver.derive(EasyMock.isA(ClassBoundary.class))).andReturn(COLLECTION_IMPL);
        EasyMock.expect(mockClassBoundary.forName(COLLECTION_IMPL)).andThrow(new IllegalStateException());
        mockControl.replay();

        try {
            instantiator.filter(mockMethodParam);
            fail();
        } catch (MatchNotFoundException e) {
            assertThat(e.getCause().getClass() == IllegalStateException.class, equalTo(true));
            assertThat(e.getMessage(), equalTo("Could not load implementation for class: java.util.CollectionImpl"));
        }
    }

    @Test
    public void shouldReturnItsName() {
        assertThat(instantiator.getFilterName(), equalTo("Simple Instantiator"));
    }
}
