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

import com.googlecode.pinthura.annotation.AnnotationFinder;
import com.googlecode.pinthura.data.UrlBoundaryFactory;
import com.googlecode.pinthura.data.UrlBoundaryImpl;
import com.googlecode.pinthura.data.WidgetFactory;
import com.googlecode.pinthura.factory.MethodParam;
import com.googlecode.pinthura.filter.FilterLink;
import com.googlecode.pinthura.filter.MatchNotFoundException;
import com.googlecode.pinthura.filter.annotation.InterfaceImpl;
import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Method;

public final class AnAnnotationLocatorUnderTest {

    private FilterLink<MethodParam, Class<?>> locator;

    private final IMocksControl mockControl = EasyMock.createControl();

    private AnnotationFinder mockAnnotationFinder;
    private InterfaceImpl mockInterfaceImpl;

    @Before
    public void setup() {
        mockAnnotationFinder = mockControl.createMock(AnnotationFinder.class);
        mockInterfaceImpl = mockControl.createMock(InterfaceImpl.class);

        locator = new AnnotationLocatorImpl(mockAnnotationFinder);
    }

    @Test
    public void shouldLocateAnImplementationByAnnotations() {
        EasyMock.expect(mockAnnotationFinder.find(EasyMock.isA(Method.class), EasyMock.eq(InterfaceImpl.class))).
                andReturn(mockInterfaceImpl);
        EasyMock.expect(mockInterfaceImpl.value());
        EasyMock.expectLastCall().andReturn(UrlBoundaryImpl.class);
        mockControl.replay();

        Class<?> implClass  = locator.filter(createAnnotatedMethodParam());
        assertThat(UrlBoundaryImpl.class == implClass, equalTo(true));

        mockControl.verify();
    }

    @SuppressWarnings({ "ThrowableInstanceNeverThrown" })
    @Test (expected = MatchNotFoundException.class)
    public void shouldThrowAnExceptionIfTheAnnotationIsNotFound() {
        EasyMock.expect(mockAnnotationFinder.find(EasyMock.isA(Method.class), EasyMock.eq(InterfaceImpl.class))).
                andThrow(new MatchNotFoundException());
        mockControl.replay();

        locator.filter(createUnAnnotatedMethodParam());
    }

    @Test
    public void shouldReturnItsName() {
        assertThat(locator.getFilterName(), equalTo("An Annotation Filter"));
    }

    @SuppressWarnings({ "unchecked" })
    private MethodParam createUnAnnotatedMethodParam() {
        return new MethodParamBuilder().forInterface(WidgetFactory.class).havingMethod("createWidget").build();
    }

    @SuppressWarnings({ "unchecked" })
    private MethodParam createAnnotatedMethodParam() {
        return new MethodParamBuilder().forInterface(UrlBoundaryFactory.class).havingMethod("createUrlBoundary").build();
    }
}
