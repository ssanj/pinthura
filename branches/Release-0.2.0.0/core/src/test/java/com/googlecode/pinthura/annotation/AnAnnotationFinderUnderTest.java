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
import com.googlecode.pinthura.boundary.java.lang.ClassBoundaryImpl;
import com.googlecode.pinthura.boundary.java.lang.reflect.MethodBoundary;
import com.googlecode.pinthura.factory.Implementation;
import static junit.framework.Assert.assertTrue;
import static junit.framework.Assert.fail;
import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import static org.hamcrest.core.IsSame.sameInstance;
import static org.junit.Assert.assertThat;
import org.junit.Before;
import org.junit.Test;

public final class AnAnnotationFinderUnderTest {

    private final IMocksControl mockControl = EasyMock.createControl();
    private AnnotationFinder finder;
    private MethodBoundary mockMethodBoundary;
    private Implementation mockImplementation;


    @Before
    public void setup() {
        mockImplementation = mockControl.createMock(Implementation.class);
        mockMethodBoundary = mockControl.createMock(MethodBoundary.class);

        finder = new AnnotationFinderImpl();
    }

    @Test
    public void shouldFindAnnotationsOnASpecifiedMethod() throws NoSuchMethodException {
        EasyMock.expect(mockMethodBoundary.getAnnotation(createAnnotationClass())).andReturn(mockImplementation);
        mockControl.replay();

        Implementation result = finder.find(mockMethodBoundary, createAnnotationClass());
        assertThat(result, sameInstance(mockImplementation));

        mockControl.verify();
    }

    @SuppressWarnings({ "ThrowableInstanceNeverThrown" })
    @Test
    public void shouldThrowAnExceptionIfTheAnnotationIsNotFound() throws NoSuchMethodException {
        EasyMock.expect(mockMethodBoundary.getAnnotation(createAnnotationClass())).andReturn(null);
        mockControl.replay();

        try {
            finder.find(mockMethodBoundary, createAnnotationClass());
            fail();
        } catch (AnnotationNotFoundException e) {
              assertTrue(true);
        }
    }

    private ClassBoundary<Implementation> createAnnotationClass() {
        return new ClassBoundaryImpl<Implementation>(Implementation.class);
    }
}
