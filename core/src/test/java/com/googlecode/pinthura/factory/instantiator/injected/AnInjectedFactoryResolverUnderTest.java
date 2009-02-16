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
import com.googlecode.pinthura.factory.Factory;
import com.googlecode.pinthura.factory.MethodParam;
import com.googlecode.pinthura.factory.instantiator.AnnotatedFactoryExtractor;
import com.googlecode.pinthura.factory.instantiator.ClassInstance;
import com.googlecode.pinthura.factory.instantiator.ClassInstanceFactory;
import com.googlecode.pinthura.util.Arrayz;
import com.googlecode.pinthura.util.ArrayzImpl;
import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertThat;
import static org.hamcrest.core.IsSame.sameInstance;

import java.util.Arrays;
import java.util.List;

//TODO: Move to TestBehaviourHandler
public final class AnInjectedFactoryResolverUnderTest {

    private static final int NUMBER_OF_PARAMETERS_1 = 2;
    private static final int NUMBER_OF_PARAMETERS_2 = 5;

    private final IMocksControl mockControl = EasyMock.createControl();
    private AnnotatedFactoryExtractor mockAnnotatedFactoryExtractor;
    private ClassInstanceFactory mockClassInstanceFactory;
    private InjectedInstanceSorterFactory mockInjectedInstanceSorterFactory;
    private InjectedFactoryResolver resolver;
    private MethodParam mockMethodParam;
    private ConstructorParamFactory mockConstructorParamFactory;
    private Arrayz arrayz;


    @Before
    public void setup() {
        mockAnnotatedFactoryExtractor = mockControl.createMock(AnnotatedFactoryExtractor.class);
        mockClassInstanceFactory = mockControl.createMock(ClassInstanceFactory.class);
        mockInjectedInstanceSorterFactory = mockControl.createMock(InjectedInstanceSorterFactory.class);
        mockConstructorParamFactory = mockControl.createMock(ConstructorParamFactory.class);
        mockMethodParam = mockControl.createMock(MethodParam.class);
        arrayz = new ArrayzImpl();

        resolver = new InjectedFactoryResolverImpl(mockAnnotatedFactoryExtractor, mockClassInstanceFactory,
                mockInjectedInstanceSorterFactory, mockConstructorParamFactory);
    }

    @SuppressWarnings({ "unchecked" })
    @Test
    public void shouldResolveAGivenMethodParam() {
        Factory mockFactory = mockControl.createMock(Factory.class);
        List<ClassBoundary> supplied = Arrays.asList(mockControl.createMock(ClassBoundary.class));
        resolveMethodParam(arrayz.fromObjects(mockFactory), supplied, NUMBER_OF_PARAMETERS_1);
    }

    @Test
    public void shouldResolvedAnotherMethodParam() {
        Factory mockFactory1 = mockControl.createMock(Factory.class);
        Factory mockFactory2 = mockControl.createMock(Factory.class);
        Factory mockFactory3 = mockControl.createMock(Factory.class);
        ClassBoundary mockClassBoundary1 = mockControl.createMock(ClassBoundary.class);
        ClassBoundary mockClassBoundary2 = mockControl.createMock(ClassBoundary.class);
        List<ClassBoundary> supplied = Arrays.asList(mockClassBoundary1, mockClassBoundary2);
        resolveMethodParam(arrayz.fromObjects(mockFactory1,  mockFactory2, mockFactory3), supplied, NUMBER_OF_PARAMETERS_2);
    }

    private void resolveMethodParam(final Factory[] factories, final List<ClassBoundary> supplied, final int numberOfParameters) {
        EasyMock.expect(mockAnnotatedFactoryExtractor.extractFactories(mockMethodParam)).andReturn(factories);
        EasyMock.expect(mockMethodParam.getParameterTypes());
        EasyMock.expectLastCall().andReturn(supplied);
        ClassInstance[] classInstanceArray = new ClassInstance[numberOfParameters];
        EasyMock.expect(mockClassInstanceFactory.createClassInstanceArray(numberOfParameters)).andReturn(classInstanceArray);

        ResolvedFactorySorter mockResolvedFactorySorter = mockControl.createMock(ResolvedFactorySorter.class);
        SuppliedFactorySorter mockSuppliedFactorySorter = mockControl.createMock(SuppliedFactorySorter.class);
        EasyMock.expect(mockInjectedInstanceSorterFactory.createResolvedSorter()).andReturn(mockResolvedFactorySorter);
        EasyMock.expect(mockInjectedInstanceSorterFactory.createSuppliedSorter()).andReturn(mockSuppliedFactorySorter);
        mockResolvedFactorySorter.sort(factories, classInstanceArray);
        mockSuppliedFactorySorter.sort(mockMethodParam, classInstanceArray);

        ConstructorParam mockConstructorParam = mockControl.createMock(ConstructorParam.class);
        EasyMock.expect(mockConstructorParamFactory.createInjectedFactoryValues(classInstanceArray)).
                andReturn(mockConstructorParam);
        mockControl.replay();

        ConstructorParam constructorParam = resolver.resolve(mockMethodParam);
        assertThat(constructorParam, sameInstance(mockConstructorParam));

        mockControl.verify();
    }
}
