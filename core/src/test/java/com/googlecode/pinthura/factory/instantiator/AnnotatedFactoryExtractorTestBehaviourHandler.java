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

import com.googlecode.pinthura.annotation.AnnotationFinder;
import com.googlecode.pinthura.factory.Factory;
import com.googlecode.pinthura.factory.InjectedFactory;
import com.googlecode.pinthura.factory.MethodParam;
import com.googlecode.pinthura.factory.boundary.ClassBoundary;
import com.googlecode.pinthura.factory.boundary.ClassBoundaryImpl;
import com.googlecode.pinthura.factory.boundary.MethodBoundary;
import com.googlecode.pinthura.util.Arrayz;
import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.List;

public final class AnnotatedFactoryExtractorTestBehaviourHandler {

    private final IMocksControl mockControl;
    private List<Factory> factories;
    private MethodParam mockMethodParam;
    private AnnotationFinder mockAnnotationFinder;

    public AnnotatedFactoryExtractorTestBehaviourHandler() {
        mockControl = EasyMock.createControl();
        mockMethodParam = mockControl.createMock(MethodParam.class);
        mockAnnotationFinder = mockControl.createMock(AnnotationFinder.class);

        this.factories = new ArrayList<Factory>();
    }

    AnnotatedFactoryExtractorTestBehaviourHandler createFactory() {
        factories.add(mockControl.createMock(Factory.class));
        return this;
    }

    @SuppressWarnings({ "unchecked" })
    void replay() {
        MethodBoundary mockMethodBoundary = mockControl.createMock(MethodBoundary.class);
        ClassBoundary classBoundary = new ClassBoundaryImpl<InjectedFactory>(InjectedFactory.class);
        InjectedFactory mockInjectedFactory = mockControl.createMock(InjectedFactory.class);
        EasyMock.expect(mockMethodParam.getMethod()).andReturn(mockMethodBoundary);
        EasyMock.expect(mockAnnotationFinder.find(mockMethodBoundary, classBoundary)).andReturn(mockInjectedFactory);
        EasyMock.expect(mockInjectedFactory.value()).andReturn(asArray());

        mockControl.replay();
    }

    public InjectedFactory extract() {
        return new AnnotatedFactoryExtractorImpl(mockAnnotationFinder).extract(mockMethodParam);
    }

    public Factory[] extractFactories() {
        return new AnnotatedFactoryExtractorImpl(mockAnnotationFinder).extractFactories(mockMethodParam);
    }

    public void expectAnnotatedFactoriesAreReturned(final InjectedFactory injectedFactory) {
        assertThat(injectedFactory.value(), equalTo(asArray()));
    }

    public void expectAnnotatedFactoriesAreReturned(final Factory[] result) {
        assertThat(result, equalTo(asArray()));
    }

    void verify() {
        mockControl.verify();
    }

    private Factory[] asArray() {
        return Arrayz.fromCollection(factories, Factory.class);
    }
}
