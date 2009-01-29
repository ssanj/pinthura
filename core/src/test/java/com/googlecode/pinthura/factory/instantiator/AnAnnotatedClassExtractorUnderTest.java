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
import com.googlecode.pinthura.boundary.java.lang.ClassBoundary;
import com.googlecode.pinthura.boundary.java.lang.ClassBoundaryImpl;
import com.googlecode.pinthura.boundary.java.lang.reflect.MethodBoundary;
import com.googlecode.pinthura.data.SquareImpl;
import com.googlecode.pinthura.data.UrlBoundaryImpl;
import com.googlecode.pinthura.factory.Implementation;
import com.googlecode.pinthura.factory.MethodParam;
import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;
import org.junit.Before;
import org.junit.Test;

public final class AnAnnotatedClassExtractorUnderTest {

    private final IMocksControl mockControl = EasyMock.createControl();
    private AnnotatedClassExtractor extractor;
    private AnnotationFinder mockAnnotationFinder;
    private MethodParam mockMethodParam;

    @Before
    public void setup() {
        mockAnnotationFinder = mockControl.createMock(AnnotationFinder.class);
        mockMethodParam = mockControl.createMock(MethodParam.class);
        extractor = new AnnotatedClassExtractorImpl(mockAnnotationFinder);
    }

    @Test
    public void shouldRetreiveASetClass() {
        expectClassSet(UrlBoundaryImpl.class);
    }

    @Test
    public void shouldRetreiveAnotherSetClass() {
        expectClassSet(SquareImpl.class);
    }

    private <T> void expectClassSet(final Class<T> value) {
        MethodBoundary mockMethodBoundary = mockControl.createMock(MethodBoundary.class);
        EasyMock.expect(mockMethodParam.getMethod()).andReturn(mockMethodBoundary);
        Implementation mockImplementation = mockControl.createMock(Implementation.class);
        ClassBoundary<Implementation> annotationClass = createAnnotationClass();
        EasyMock.expect(mockAnnotationFinder.find(mockMethodBoundary, annotationClass)).andReturn(mockImplementation);
        EasyMock.expect(mockImplementation.value());
        EasyMock.expectLastCall().andReturn(value);
        mockControl.replay();

        ClassBoundary<T> result = extractor.extract(mockMethodParam);
        assertThat((result.equals(new ClassBoundaryImpl<T>(value))), equalTo(true));

        mockControl.verify();
    }

    private ClassBoundary<Implementation> createAnnotationClass() {
        return new ClassBoundaryImpl<Implementation>(Implementation.class);
    }
}
