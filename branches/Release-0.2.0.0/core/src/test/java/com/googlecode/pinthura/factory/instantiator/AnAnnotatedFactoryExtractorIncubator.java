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
import com.googlecode.pinthura.annotation.SuppressionReason;
import com.googlecode.pinthura.boundary.java.lang.ClassBoundary;
import com.googlecode.pinthura.boundary.java.lang.ClassBoundaryFactory;
import com.googlecode.pinthura.boundary.java.lang.reflect.MethodBoundary;
import com.googlecode.pinthura.factory.Factory;
import com.googlecode.pinthura.factory.InjectedFactory;
import com.googlecode.pinthura.factory.MethodParam;
import com.googlecode.pinthura.util.Arrayz;
import com.googlecode.pinthura.util.ArrayzImpl;
import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.List;

public final class AnAnnotatedFactoryExtractorIncubator {

    private final IMocksControl mockControl;
    private List<Factory> factories;
    private MethodParam mockMethodParam;
    private AnnotationFinder mockAnnotationFinder;
    private final Arrayz arrayz;
    private InjectedFactory returnedInjectedFactory;
    private Factory[] returnedFactories;
    private MethodBoundary mockMethodBoundary;
    private ClassBoundary mockClassBoundary;
    private InjectedFactory mockInjectedFactory;
    private ClassBoundaryFactory mockClassBoundaryFactory;

    public AnAnnotatedFactoryExtractorIncubator() {
        mockControl = EasyMock.createControl();
        mockMethodParam = mockControl.createMock(MethodParam.class);
        mockAnnotationFinder = mockControl.createMock(AnnotationFinder.class);
        mockClassBoundaryFactory = mockControl.createMock(ClassBoundaryFactory.class);
        arrayz = new ArrayzImpl();

        factories = new ArrayList<Factory>();
    }

    public AnAnnotatedFactoryExtractorIncubator supplyAFactory() {
        factories.add(mockControl.createMock(Factory.class));
        return this;
    }

    public AnAnnotatedFactoryExtractorIncubator performExtract() {
        preparePerform();
        mockControl.replay();

        returnedInjectedFactory = new AnnotatedFactoryExtractorImpl(mockAnnotationFinder, mockClassBoundaryFactory).
                extract(mockMethodParam);
        return this;
    }

    public AnAnnotatedFactoryExtractorIncubator performExtractFactories() {
        preparePerform();
        mockControl.replay();
        
        returnedFactories = new AnnotatedFactoryExtractorImpl(mockAnnotationFinder, mockClassBoundaryFactory).
                extractFactories(mockMethodParam);
        return this;
    }

    @SuppressWarnings("unchecked")
    @SuppressionReason(SuppressionReason.Reason.CANT_INFER_GENERICS_ON_MOCKS)
    private void preparePerform() {
        mockMethodBoundary = mockControl.createMock(MethodBoundary.class);
        mockClassBoundary = mockControl.createMock(ClassBoundary.class);
        mockInjectedFactory = mockControl.createMock(InjectedFactory.class);
        EasyMock.expect(mockMethodParam.getMethod()).andReturn(mockMethodBoundary);
        EasyMock.expect(mockAnnotationFinder.find(mockMethodBoundary, mockClassBoundary)).andReturn(mockInjectedFactory);
        EasyMock.expect(mockClassBoundaryFactory.create(InjectedFactory.class)).andReturn(mockClassBoundary);
        EasyMock.expect(mockInjectedFactory.value()).andReturn(getFactories());
    }

    public AnAnnotatedFactoryExtractorIncubator injectedFactory() {
        assertThat(returnedInjectedFactory.value(), equalTo(getFactories()));
        return this;
    }

    public AnAnnotatedFactoryExtractorIncubator factory() {
        assertThat(returnedFactories, equalTo(getFactories()));
        return this;
    }

    public AnAnnotatedFactoryExtractorIncubator observe() {
        return this;
    }

    public AnAnnotatedFactoryExtractorIncubator isReturned() {
        return this;
    }

    public void done() {
        mockControl.verify();
    }

    private Factory[] getFactories() {
        return arrayz.fromCollection(factories, Factory.class);
    }
}
