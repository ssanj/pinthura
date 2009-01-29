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
package com.googlecode.pinthura.factory.instantiator.injected;

import com.googlecode.pinthura.boundary.java.lang.ClassBoundary;
import com.googlecode.pinthura.boundary.java.lang.reflect.ConstructorBoundary;
import com.googlecode.pinthura.data.Shape;
import com.googlecode.pinthura.data.UrlBoundary;
import com.googlecode.pinthura.factory.MethodParam;
import com.googlecode.pinthura.factory.instantiator.AnnotatedClassExtractor;
import com.googlecode.pinthura.factory.instantiator.InstantiationStrategy;
import com.googlecode.pinthura.filter.MatchNotFoundException;
import com.googlecode.pinthura.util.Arrayz;
import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsSame.sameInstance;
import static org.junit.Assert.assertThat;
import org.junit.Before;
import org.junit.Test;

public final class ADynamicFactoryInstantiatorUnderTest {

    private final IMocksControl mockControl = EasyMock.createControl();
    private AnnotatedClassExtractor mockAnnotatedClassExtractor;
    private InjectedFactoryResolver mockInjectedFactoryResolver;
    private InstantiationStrategy instantiator;
    private MethodParam mockMethodParam;

    @Before
    public void setup() {
        mockAnnotatedClassExtractor = mockControl.createMock(AnnotatedClassExtractor.class);
        mockInjectedFactoryResolver = mockControl.createMock(InjectedFactoryResolver.class);
        mockMethodParam = mockControl.createMock(MethodParam.class);

        instantiator = new DynamicFactoryInstantiator(mockAnnotatedClassExtractor, mockInjectedFactoryResolver);
    }

    @SuppressWarnings({ "unchecked" })
    @Test
    public void shouldCreateAnInstanceOfTheRequestedFactory() {
        ClassBoundary mockImplementation = mockControl.createMock(ClassBoundary.class);
        ClassBoundary mockConstructorArg = mockControl.createMock(ClassBoundary.class);
        UrlBoundary mockUrlBoundary = mockControl.createMock(UrlBoundary.class);
        Shape mockShape = mockControl.createMock(Shape.class);
        InjectedFactoryValues mockInjectedFactoryValues = mockControl.createMock(InjectedFactoryValues.class);
        EasyMock.expect(mockAnnotatedClassExtractor.extract(mockMethodParam)).andReturn(mockImplementation);
        EasyMock.expect(mockInjectedFactoryResolver.resolve(mockMethodParam)).andReturn(mockInjectedFactoryValues);

        ClassBoundary[] constructorTypes = Arrayz.fromObjects(mockConstructorArg);
        Object[] arguments = Arrayz.fromObjects(mockUrlBoundary);
        EasyMock.expect(mockInjectedFactoryValues.getConstructorTypes()).andReturn(constructorTypes);
        EasyMock.expect(mockInjectedFactoryValues.getConstructorArguments()).andReturn(arguments);
        ConstructorBoundary mockConstructorBoundary = mockControl.createMock(ConstructorBoundary.class);
        EasyMock.expect(mockImplementation.getConstructor(constructorTypes)).andReturn(mockConstructorBoundary);
        EasyMock.expect(mockConstructorBoundary.newInstance(arguments)).andReturn(mockShape);
        mockControl.replay();

        Shape result = (Shape) instantiator.filter(mockMethodParam);
        assertThat(result, sameInstance(mockShape));

        mockControl.verify();
    }

    @Test
    public void shouldReturnItsName() {
        assertThat("Dynamic Parameter Instantiator", equalTo(instantiator.getFilterName()));
    }

    @SuppressWarnings({ "unchecked", "ThrowableInstanceNeverThrown" })
    @Test
    public void shouldThrowAMatchNotFoundExceptionIfAnExceptionIsThrown() {
        EasyMock.expect(mockAnnotatedClassExtractor.extract(mockMethodParam)).andThrow(new IllegalArgumentException());
        mockControl.replay();

        try {
            instantiator.filter(mockMethodParam);
        } catch (MatchNotFoundException e) {
            mockControl.verify();
            assertThat((Class<IllegalArgumentException>) e.getCause().getClass(), equalTo(IllegalArgumentException.class));
        }
    }
}
