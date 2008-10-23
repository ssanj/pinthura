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

import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;
import org.junit.Before;
import org.junit.Test;
import com.googlecode.pinthura.factory.instantiator.InjectedFactoryResolver;
import com.googlecode.pinthura.factory.instantiator.InjectedFactoryResolverImpl;
import com.googlecode.pinthura.factory.instantiator.InjectedFactoryValues;
import com.googlecode.pinthura.factory.instantiator.AnnotatedFactoryExtractor;
import com.googlecode.pinthura.factory.boundary.ClassBoundaryImpl;
import com.googlecode.pinthura.factory.boundary.ClassBoundary;
import com.googlecode.pinthura.data.UrlBoundaryFactory;

public final class AInjectedFactoryResolverUnderTest {

    private final IMocksControl mockControl = EasyMock.createControl();
    private InjectedFactoryResolver resolver;
    private MethodParam mockMethodParam;
    private AnnotatedFactoryExtractor mockAnnotatedFactoryExtractor;

    @Before
    public void setup() {
        mockMethodParam = mockControl.createMock(MethodParam.class);
        mockAnnotatedFactoryExtractor = mockControl.createMock(AnnotatedFactoryExtractor.class);
        resolver = new InjectedFactoryResolverImpl(mockAnnotatedFactoryExtractor);
    }

    @Test
    public void shouldResolveAGivenFactory() {
        InjectedFactory mockInjectedFactory = mockControl.createMock(InjectedFactory.class);
        Factory mockFactory = mockControl.createMock(Factory.class);
        EasyMock.expect(mockMethodParam.getParameterTypes()).andReturn(getParameterTypes());
        EasyMock.expect(mockAnnotatedFactoryExtractor.extract(mockMethodParam)).andReturn(mockInjectedFactory);
        expectFactory(mockInjectedFactory, mockFactory);

        mockControl.replay();

        InjectedFactoryValues injectedFactoryValues = resolver.resolve(mockMethodParam);
        assertThat(injectedFactoryValues.getConstructorTypes(), equalTo(getResolvedClasses()));

        mockControl.verify();
    }

    private void expectFactory(final InjectedFactory mockInjectedFactory, final Factory mockFactory) {
        EasyMock.expect(mockFactory.factoryClass());
        EasyMock.expectLastCall().andReturn(UrlBoundaryFactory.class);
        EasyMock.expect(mockFactory.index()).andReturn(1);
        EasyMock.expect(mockInjectedFactory.value()).andReturn(new Factory[] {mockFactory});
    }

    @SuppressWarnings({ "unchecked" })
    private ClassBoundary<?>[] getResolvedClasses() {
        return new ClassBoundary[] {new ClassBoundaryImpl(String.class), new ClassBoundaryImpl(UrlBoundaryFactory.class)};
    }

    @SuppressWarnings({ "unchecked" })
    private ClassBoundary<?>[] getParameterTypes() {
        return new ClassBoundary[] {new ClassBoundaryImpl(String.class)};
    }
}
