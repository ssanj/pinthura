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
package com.googlecode.pinthura.factory.locator;

import com.googlecode.pinthura.data.UrlBoundary;
import com.googlecode.pinthura.factory.MethodParam;
import com.googlecode.pinthura.factory.boundary.ClassBoundary;
import com.googlecode.pinthura.factory.locator.deriver.ClassNameDeriver;
import com.googlecode.pinthura.filter.MatchNotFoundException;
import static junit.framework.Assert.fail;
import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;
import org.junit.Before;
import org.junit.Test;

import java.util.Collection;

public final class ASimpleImplementationLocatorUnderTest {

    private static final String URL_BOUNDARY_IMPL   = "com.googlecode.pinthura.data.UrlBoundaryImpl";
    private static final String COLLECTION_IMPL     = "java.util.CollectionImpl";

    private final IMocksControl mockControl = EasyMock.createControl();

    private MethodParam mockMethodParam;
    private ClassNameDeriver mockClassNameDeriver;

    @Before
    public void setup() {
        mockMethodParam = mockControl.createMock(MethodParam.class);
        mockClassNameDeriver = mockControl.createMock(ClassNameDeriver.class);
    }

    @Test
    public void shouldLocateAnImplementationThatExists() {
        EasyMock.expect(mockMethodParam.getReturnType());
        EasyMock.expectLastCall().andReturn(UrlBoundary.class);
        EasyMock.expect(mockClassNameDeriver.derive(EasyMock.isA(ClassBoundary.class))).
                andReturn(URL_BOUNDARY_IMPL);
        mockControl.replay();

        assertThat(getImplementationClass().getName(), equalTo(URL_BOUNDARY_IMPL));

        mockControl.verify();
    }

    @Test
    public void shouldThrowAMatchNotFoundExceptionForAnImplementationThatDoesNotExist() {
        EasyMock.expect(mockMethodParam.getReturnType());
        EasyMock.expectLastCall().andReturn(Collection.class);
        EasyMock.expect(mockClassNameDeriver.derive(EasyMock.isA(ClassBoundary.class))).
                andReturn(COLLECTION_IMPL);
        mockControl.replay();

        try {
            getImplementationClass();
            fail();
        } catch (MatchNotFoundException e) {
            assertThat(e.getMessage(), equalTo("Could not load implementation for class: java.util.CollectionImpl"));
        }
    }

    @Test
    public void shouldReturnItsName() {
        assertThat(new SimpleImplementationLocator(mockClassNameDeriver).getFilterName(), equalTo("Simple Implementation Locator"));
    }

    private Class<?> getImplementationClass() {
        return new SimpleImplementationLocator(mockClassNameDeriver).filter(mockMethodParam);
    }
}
