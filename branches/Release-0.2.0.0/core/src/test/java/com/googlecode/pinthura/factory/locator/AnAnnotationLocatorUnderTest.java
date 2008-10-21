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

//TODO: Test
public final class AnAnnotationLocatorUnderTest {

//    private FilterLink<MethodParam, Class<?>> locator;
//
//    private final IMocksControl mockControl = EasyMock.createControl();
//
//    private AnnotationFinder mockAnnotationFinder;
//    private Implementation mockInterfaceImpl;
//
//    @Before
//    public void setup() {
//        mockAnnotationFinder = mockControl.createMock(AnnotationFinder.class);
//        mockInterfaceImpl = mockControl.createMock(Implementation.class);
//
//        locator = new AnnotationLocatorImpl(mockAnnotationFinder);
//    }
//
//    @Test
//    public void shouldLocateAnImplementationByAnnotations() {
//        EasyMock.expect(mockAnnotationFinder.find(EasyMock.isA(Method.class), EasyMock.eq(Implementation.class))).
//                andReturn(mockInterfaceImpl);
//        EasyMock.expect(mockInterfaceImpl.value());
//        EasyMock.expectLastCall().andReturn(UrlBoundaryImpl.class);
//        mockControl.replay();
//
//        Class<?> implClass  = locator.filter(createAnnotatedMethodParam());
//        assertThat(UrlBoundaryImpl.class == implClass, equalTo(true));
//
//        mockControl.verify();
//    }
//
//    @SuppressWarnings({ "ThrowableInstanceNeverThrown" })
//    @Test (expected = MatchNotFoundException.class)
//    public void shouldThrowAnExceptionIfTheAnnotationIsNotFound() {
//        EasyMock.expect(mockAnnotationFinder.find(EasyMock.isA(Method.class), EasyMock.eq(Implementation.class))).
//                andThrow(new MatchNotFoundException());
//        mockControl.replay();
//
//        locator.filter(createUnAnnotatedMethodParam());
//    }
//
//    @Test
//    public void shouldReturnItsName() {
//        assertThat(locator.getFilterName(), equalTo("An Annotation Filter"));
//    }
//
//    @SuppressWarnings({ "unchecked" })
//    private MethodParam createUnAnnotatedMethodParam() {
//        return new MethodParamBuilder().forInterface(WidgetFactory.class).havingMethod("createWidget").build();
//    }
//
//    @SuppressWarnings({ "unchecked" })
//    private MethodParam createAnnotatedMethodParam() {
//        return new MethodParamBuilder().forInterface(UrlBoundaryFactory.class).havingMethod("createUrlBoundary").build();
//    }
}
