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
package com.googlecode.pinthura.factory;

import com.googlecode.pinthura.data.UrlBoundaryFactory;
import com.googlecode.pinthura.factory.boundary.ClassBoundary;
import com.googlecode.pinthura.factory.instantiator.AnnotatedFactoryExtractor;
import com.googlecode.pinthura.factory.instantiator.ClassInstance;
import com.googlecode.pinthura.factory.instantiator.FactoryCreationEvent;
import com.googlecode.pinthura.factory.instantiator.FactoryCreationMonitor;
import com.googlecode.pinthura.factory.instantiator.InjectedFactoryResolver;
import com.googlecode.pinthura.factory.instantiator.InjectedFactoryResolverImpl;
import com.googlecode.pinthura.factory.instantiator.InjectedFactoryValues;
import com.googlecode.pinthura.factory.instantiator.ResolverObjectFactory;
import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import static org.hamcrest.core.IsSame.sameInstance;
import static org.junit.Assert.assertThat;
import org.junit.Before;
import org.junit.Test;

public final class AnInjectedFactoryResolverUnderTest {

    private static final String STRING_PARAM = "testing";
    private static final int INTEGER_PARAM = 50;

    private final IMocksControl mockControl = EasyMock.createControl();
    private MethodParam mockMethodParam;
    private AnnotatedFactoryExtractor mockAnnotatedFactoryExtractor;
    private ResolverObjectFactory mockResolverObjectFactory;
    private FactoryCreationMonitor mockFactoryCreationMonitor;
    private Factory mockFactory;
    private UrlBoundaryFactory mockUrlBoundaryFactory;
    private ClassInstance mockResolvedClassInstance;
    private FactoryCreator mockFactoryCreator;
    private ClassBoundary mockStringBoundary;
    private ClassBoundary mockIntegerBoundary;
    private ClassInstance mockSuppliedStringInstance;
    private ClassInstance mockSuppliedIntegerInstance;
    private InjectedFactoryValues mockInjectedFactoryValues;
    private FactoryCreationEvent mockFactoryCreationEvent;

    @Before
    public void setup() {
        mockMethodParam = mockControl.createMock(MethodParam.class);
        mockAnnotatedFactoryExtractor = mockControl.createMock(AnnotatedFactoryExtractor.class);
        mockResolverObjectFactory = mockControl.createMock(ResolverObjectFactory.class);
        mockFactoryCreationMonitor = mockControl.createMock(FactoryCreationMonitor.class);

        mockFactory = mockControl.createMock(Factory.class);
        mockUrlBoundaryFactory = mockControl.createMock(UrlBoundaryFactory.class);
        mockResolvedClassInstance = mockControl.createMock(ClassInstance.class);
        mockFactoryCreator = mockControl.createMock(FactoryCreator.class);
        mockStringBoundary = mockControl.createMock(ClassBoundary.class);
        mockIntegerBoundary = mockControl.createMock(ClassBoundary.class);
        mockSuppliedStringInstance = mockControl.createMock(ClassInstance.class);
        mockSuppliedIntegerInstance = mockControl.createMock(ClassInstance.class);
        mockInjectedFactoryValues = mockControl.createMock(InjectedFactoryValues.class);
        mockFactoryCreationEvent = mockControl.createMock(FactoryCreationEvent.class);
    }

    @SuppressWarnings({ "unchecked" })
    @Test
    public void shouldResolveAGivenFactory() {
        ClassInstance[] classInstanceArray = new ClassInstance[3];

        expectFactoryCreator(mockFactoryCreator, mockFactoryCreationEvent);
        expectFactory(mockFactory);
        expectSuppliedClass();
        expectClassInstanceArray(classInstanceArray);
        expectResolvedClassInstance();
        expectSuppliedClassInstance(classInstanceArray);
        mockControl.replay();

        InjectedFactoryValues injectedFactoryValues = resolve();

        verifyExpectations(classInstanceArray, injectedFactoryValues);
    }

    private InjectedFactoryValues resolve() {
        InjectedFactoryResolver resolver =
                new InjectedFactoryResolverImpl(mockAnnotatedFactoryExtractor, mockResolverObjectFactory, mockFactoryCreationMonitor);
        resolver.factoryCreated(mockFactoryCreationEvent);
        return resolver.resolve(mockMethodParam);
    }

    private void expectSuppliedClass() {
        EasyMock.expect(mockMethodParam.getParameterTypes()).andReturn(createArray(mockStringBoundary, mockIntegerBoundary));
    }

    private void expectClassInstanceArray(final ClassInstance[] classInstanceArray) {
        EasyMock.expect(mockResolverObjectFactory.createClassInstanceArray(3)).andReturn(classInstanceArray);
    }

    @SuppressWarnings({ "unchecked" })
    private void expectSuppliedClassInstance(final ClassInstance[] classInstanceArray) {
        EasyMock.expect(mockMethodParam.getArguments()).andReturn(createArray(STRING_PARAM, INTEGER_PARAM));
        EasyMock.expect(mockResolverObjectFactory.createClassInstance(mockStringBoundary, STRING_PARAM)).
                andReturn(mockSuppliedStringInstance);
        EasyMock.expect(mockResolverObjectFactory.createClassInstance(mockIntegerBoundary, INTEGER_PARAM)).
                andReturn(mockSuppliedIntegerInstance);
        EasyMock.expect(mockResolverObjectFactory.createInjectedFactoryValues(classInstanceArray)).andReturn(mockInjectedFactoryValues);
    }

    private void expectResolvedClassInstance() {
        EasyMock.expect(mockFactoryCreator.create(UrlBoundaryFactory.class)).andReturn(mockUrlBoundaryFactory);
        EasyMock.expect(mockResolverObjectFactory.createClassInstance(UrlBoundaryFactory.class, mockUrlBoundaryFactory)).
                andReturn(mockResolvedClassInstance);
    }

    @SuppressWarnings({ "unchecked" })
    private void verifyExpectations(final ClassInstance[] classInstanceArray, final InjectedFactoryValues injectedFactoryValues) {
        assertThat(classInstanceArray[0], sameInstance(mockSuppliedStringInstance));
        assertThat(classInstanceArray[1], sameInstance(mockResolvedClassInstance));
        assertThat(classInstanceArray[2], sameInstance(mockSuppliedIntegerInstance));
        assertThat(injectedFactoryValues, sameInstance(mockInjectedFactoryValues));

        mockControl.verify();
    }

    private void expectFactoryCreator(final FactoryCreator mockFactoryCreator, final FactoryCreationEvent mockFactoryCreationEvent) {
        mockFactoryCreationMonitor.registerInterest(EasyMock.isA(InjectedFactoryResolver.class));
        EasyMock.expect(mockFactoryCreationEvent.getInstance()).andReturn(mockFactoryCreator);
    }

    private void expectFactory(final Factory mockFactory) {
        EasyMock.expect(mockFactory.factoryClass());
        EasyMock.expectLastCall().andReturn(UrlBoundaryFactory.class).times(2);
        EasyMock.expect(mockFactory.index()).andReturn(1);
        EasyMock.expect(mockAnnotatedFactoryExtractor.extractFactories(mockMethodParam)).andReturn(createArray(mockFactory));
    }

    private <T> T[] createArray(T... objects) {
        return objects;
    }
}
