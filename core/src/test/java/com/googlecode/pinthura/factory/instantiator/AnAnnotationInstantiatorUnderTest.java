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

import com.googlecode.pinthura.boundary.java.lang.ClassBoundary;
import com.googlecode.pinthura.boundary.java.lang.reflect.ConstructorBoundary;
import com.googlecode.pinthura.data.UrlBoundary;
import com.googlecode.pinthura.factory.MethodParam;
import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsSame.sameInstance;
import static org.junit.Assert.assertThat;
import org.junit.Before;
import org.junit.Test;

public final class AnAnnotationInstantiatorUnderTest {

    private final IMocksControl mockControl = EasyMock.createControl();
    private InstantiationStrategy instantiator;
    private MethodParam mockMethodParam;
    private AnnotatedClassExtractor mockAnnotatedClassExtractor;
    private ConstructorLocator mockConstructorLocator;
    private ConstructorInstantiator mockConstructorInstantiator;

    @Before
    public void setup() {
        mockAnnotatedClassExtractor = mockControl.createMock(AnnotatedClassExtractor.class);
        mockConstructorLocator = mockControl.createMock(ConstructorLocator.class);
        mockConstructorInstantiator = mockControl.createMock(ConstructorInstantiator.class);
        mockMethodParam = mockControl.createMock(MethodParam.class);

        instantiator = new AnnotationInstantiator(mockAnnotatedClassExtractor, mockConstructorLocator, mockConstructorInstantiator);
    }

    @SuppressWarnings({ "unchecked" })
    @Test
    public void shouldInstantiateAGivenClass() {
        ClassBoundary mockTargetClass = mockControl.createMock(ClassBoundary.class);
        ConstructorBoundary mockTargetConstructor = mockControl.createMock(ConstructorBoundary.class);
        UrlBoundary mockInstance = mockControl.createMock(UrlBoundary.class);
        EasyMock.expect(mockAnnotatedClassExtractor.extract(mockMethodParam)).andReturn(mockTargetClass);
        EasyMock.expect(mockConstructorLocator.locate(mockMethodParam, mockTargetClass)).andReturn(mockTargetConstructor);
        EasyMock.expect(mockConstructorInstantiator.instantiate(mockMethodParam, mockTargetConstructor)).andReturn(mockInstance);
        mockControl.replay();

        UrlBoundary result = (UrlBoundary) instantiator.filter(mockMethodParam);
        assertThat(result, sameInstance(mockInstance));

        mockControl.verify();
    }

    @Test
    public void shouldReturnItsName() {
        mockControl.replay();

        assertThat("Annotation Instantiator", equalTo(instantiator.getFilterName()));

        mockControl.verify();
    }
}
