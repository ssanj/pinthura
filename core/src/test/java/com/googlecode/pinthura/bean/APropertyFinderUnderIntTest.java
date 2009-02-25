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
package com.googlecode.pinthura.bean;

import com.googlecode.pinthura.annotation.SuppressionReason;
import com.googlecode.pinthura.boundary.java.lang.ClassBoundaryImpl;
import com.googlecode.pinthura.boundary.java.lang.reflect.MethodBoundary;
import com.googlecode.pinthura.boundary.java.lang.reflect.MethodBoundaryImpl;
import com.googlecode.pinthura.test.ExceptionAsserter;
import com.googlecode.pinthura.test.ExceptionAsserterImpl;
import com.googlecode.pinthura.test.ExceptionInfoImpl;
import com.googlecode.pinthura.test.Exceptional;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;
import org.junit.Before;
import org.junit.Test;

public final class APropertyFinderUnderIntTest {

    private PropertyFinder propertyFinder;
    private ExceptionAsserter asserter;

    @Before
    public void setup() {
        propertyFinder = new PropertyFinderImpl();
        asserter = new ExceptionAsserterImpl();
    }

    @Test
    public void shouldReturnAGetterProperty() throws NoSuchMethodException {
        expectProperty("package", Class.class, "getPackage");
    }

    @Test
    public void shouldReturnABooleanProperty() throws NoSuchMethodException {
        expectProperty("sealed", Package.class, "isSealed");
    }

    @Test
    public void shouldReturnANamedProperty() throws NoSuchMethodException {
        expectProperty("toString", Integer.class, "toString");
    }

    @Test
    public void shouldThrownAPropertyFinderExceptionIfThePropertyIsNotFound() {
        asserter.runAndAssertException(new ExceptionInfoImpl(PropertyFinderException.class,
                                        "Could not find property: blah on ClassBoundaryImpl[class java.lang.String]"),
                new Exceptional() {@Override public void run() {
                    propertyFinder.findMethodFor("blah", new ClassBoundaryImpl<String>(String.class));
                }});
    }

    @SuppressWarnings("unchecked")
    @SuppressionReason(SuppressionReason.Reason.CANT_INFER_GENERICS)
    private void expectProperty(final String property, final Class<?> parentClass, final String methodName)
            throws NoSuchMethodException {
        MethodBoundary result = propertyFinder.findMethodFor(property, new ClassBoundaryImpl(parentClass));
        assertThat(result, equalTo((MethodBoundary) new MethodBoundaryImpl(parentClass.getMethod(methodName))));
    }
}
