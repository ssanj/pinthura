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
package com.googlecode.pinthura.annotation;

import com.googlecode.pinthura.boundary.java.lang.ClassBoundary;
import com.googlecode.pinthura.boundary.java.lang.reflect.MethodBoundary;
import com.googlecode.pinthura.factory.Factory;
import com.googlecode.pinthura.factory.Implementation;
import com.googlecode.pinthura.util.RandomDataChooser;
import com.googlecode.pinthura.util.builder.RandomDataChooserBuilder;
import static junit.framework.Assert.assertTrue;
import static junit.framework.Assert.fail;
import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import static org.hamcrest.core.IsSame.sameInstance;
import static org.junit.Assert.assertThat;
import org.junit.Before;
import org.junit.Test;

import java.lang.annotation.Annotation;

public final class AnAnnotationFinderUnderTest {

    private final IMocksControl mockControl = EasyMock.createControl();
    private AnnotationFinder finder;
    private MethodBoundary mockMethodBoundary;
    private Annotation mockRandomImplementation;
    private ClassBoundary mockClassBoundary;


    @Before
    public void setup() {
        RandomDataChooser randomDataChooser = new RandomDataChooserBuilder().build();
        mockRandomImplementation = randomDataChooser.chooseOneOf(mockControl.createMock(Annotation.class),
                mockControl.createMock(Implementation.class), mockControl.createMock(Factory.class));
        mockMethodBoundary = mockControl.createMock(MethodBoundary.class);
        mockClassBoundary = mockControl.createMock(ClassBoundary.class);

        finder = new AnnotationFinderImpl();
    }

    @SuppressWarnings({"unchecked"})
    @SuppressionReason(SuppressionReason.Reason.CANT_INFER_GENERICS_ON_MOCKS)
    @Test
    public void shouldFindAnnotationsOnASpecifiedMethod() throws NoSuchMethodException {
        EasyMock.expect(mockMethodBoundary.getAnnotation(mockClassBoundary)).andReturn(mockRandomImplementation);
        mockControl.replay();

        Annotation result = finder.find(mockMethodBoundary, mockClassBoundary);
        assertThat(result, sameInstance(mockRandomImplementation));

        mockControl.verify();
    }

    @SuppressWarnings("unchecked")
    @SuppressionReason(SuppressionReason.Reason.CANT_INFER_GENERICS_ON_MOCKS)
    @Test
    public void shouldThrowAnExceptionIfTheAnnotationIsNotFound() throws NoSuchMethodException {
        EasyMock.expect(mockMethodBoundary.getAnnotation(mockClassBoundary)).andReturn(null);
        mockControl.replay();

        try {
            finder.find(mockMethodBoundary, mockClassBoundary);
            fail();
        } catch (AnnotationNotFoundException e) {
            assertTrue(true);
        }
    }
}
