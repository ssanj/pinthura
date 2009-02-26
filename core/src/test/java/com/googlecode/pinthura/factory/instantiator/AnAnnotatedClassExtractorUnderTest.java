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
import com.googlecode.pinthura.data.Square;
import com.googlecode.pinthura.data.UrlBoundary;
import com.googlecode.pinthura.factory.Implementation;
import com.googlecode.pinthura.factory.MethodParam;
import com.googlecode.pinthura.test.types.Deux;
import com.googlecode.pinthura.test.types.Tres;
import com.googlecode.pinthura.util.builder.RandomDataChooserBuilder;
import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import static org.hamcrest.core.IsSame.sameInstance;
import static org.junit.Assert.assertThat;
import org.junit.Before;
import org.junit.Test;

public final class AnAnnotatedClassExtractorUnderTest {

    private IMocksControl mockControl;
    private AnnotationFinder mockAnnotationFinder;
    private MethodParam mockMethodParam;
    private ClassBoundaryFactory mockClassBoundaryFactory;
    private Class<?> randomClass;

    @Before
    public void setup() {
        mockControl = EasyMock.createControl();
        mockAnnotationFinder = mockControl.createMock(AnnotationFinder.class);
        mockMethodParam = mockControl.createMock(MethodParam.class);
        mockClassBoundaryFactory = mockControl.createMock(ClassBoundaryFactory.class);
        randomClass = new RandomDataChooserBuilder().build().<Class>chooseOneOf(UrlBoundary.class, Square.class, Deux.class, Tres.class);
    }

    @Test
    public void shouldRetreiveASetClass() {
        expectClassSet(randomClass);
    }

    @SuppressWarnings("unchecked")
    @SuppressionReason(SuppressionReason.Reason.CANT_INFER_GENERICS_ON_MOCKS)
    private <T> void expectClassSet(final Class<T> value) {
        ClassBoundary<Implementation> mockImplementationClassBoundary = mockControl.createMock(ClassBoundary.class);
        EasyMock.expect(mockClassBoundaryFactory.create(Implementation.class)).andReturn(mockImplementationClassBoundary);

        MethodBoundary mockMethodBoundary = mockControl.createMock(MethodBoundary.class);
        EasyMock.expect(mockMethodParam.getMethod()).andReturn(mockMethodBoundary);

        Implementation mockImplementationAnnotation = mockControl.createMock(Implementation.class);
        EasyMock.expect(mockAnnotationFinder.find(mockMethodBoundary, mockImplementationClassBoundary)).andReturn(mockImplementationAnnotation);
        EasyMock.expect(mockImplementationAnnotation.value());
        EasyMock.expectLastCall().andReturn(value);

        ClassBoundary mockSuppliedClassBoundary = mockControl.createMock(ClassBoundary.class);
        EasyMock.expect(mockClassBoundaryFactory.create(value)).andReturn(mockSuppliedClassBoundary);
        mockControl.replay();

        AnnotatedClassExtractor extractor = new AnnotatedClassExtractorImpl(mockAnnotationFinder, mockClassBoundaryFactory);
        ClassBoundary<T> result = extractor.extract(mockMethodParam);
        assertThat(result, sameInstance(mockSuppliedClassBoundary));

        mockControl.verify();
    }
}
