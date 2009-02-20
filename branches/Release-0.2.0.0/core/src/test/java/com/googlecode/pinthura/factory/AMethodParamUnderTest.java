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

import com.googlecode.pinthura.data.UrlBoundary;
import com.googlecode.pinthura.data.UrlBoundaryFactory;
import com.googlecode.pinthura.data.Widget;
import com.googlecode.pinthura.data.WidgetFactory;
import com.googlecode.pinthura.factory.locator.MethodParamIncubator;
import com.googlecode.pinthura.util.RandomDataChooser;
import com.googlecode.pinthura.util.builder.RandomDataChooserBuilder;
import org.junit.Before;
import org.junit.Test;

public final class AMethodParamUnderTest {

    private RandomDataChooser randomDataChooser;

    @Before
    public void setup() {
        randomDataChooser = new RandomDataChooserBuilder().build();
    }

    @SuppressWarnings("unchecked")
    @Test
    public void shouldWrapAMethodAndItsParameters() {
        MethodWrapper methodWrapper = randomDataChooser.chooseOneOf(
                new MethodWrapper<UrlBoundaryFactory, UrlBoundary>("createUrlBoundary",
                        UrlBoundaryFactory.class, "http://someurl", UrlBoundary.class),
                new MethodWrapper<WidgetFactory, Widget>("createWidget", WidgetFactory.class, 1, Widget.class));

        new MethodParamIncubator<UrlBoundaryFactory, UrlBoundary>().
                supplyTargetClass(methodWrapper.targetClass).
                supplyMethodName(methodWrapper.methodName).
                supplyMethodArgument(methodWrapper.argument).
                supplyReturnType(methodWrapper.returnType).
                performGetMethod().
                observe().methodName().isReturned().
                observe().parameterTypes().areReturned().
                observe().parameterValues().areReturned().
                observe().methodBoundary().areReturned().
                observe().returnType().isReturned().
                done();
    }

    @SuppressWarnings("unchecked")
    @Test
    public void shouldNotReturnParametersWhenArgumentsAreNotSupplied() {
        MethodWrapper methodWrapper = randomDataChooser.chooseOneOf(
                new MethodWrapper<String, String>("trim",
                    String.class, String.class),
                new MethodWrapper<WidgetFactory, Widget>("createWidget", WidgetFactory.class, Widget.class));

        new MethodParamIncubator<String, String>().
                supplyTargetClass(methodWrapper.targetClass).
                supplyMethodName(methodWrapper.methodName).
                supplyNoParameters().
                supplyReturnType(methodWrapper.returnType).
                performGetMethod().
                observe().methodName().isReturned().
                observe().parameterValues().areEmpty().
                observe().methodBoundary().areReturned().
                observe().returnType().isReturned().
                done();
    }

    private static class MethodWrapper<TARGET_CLASS, RETURN_TYPE> {
        private final String methodName;
        private final Class<TARGET_CLASS> targetClass;
        private final Object argument;
        private final Class<RETURN_TYPE> returnType;

        private MethodWrapper(final String methodName, final Class<TARGET_CLASS> targetClass, final Object argument,
                              final Class<RETURN_TYPE> returnType) {
            this.methodName = methodName;
            this.targetClass = targetClass;
            this.argument = argument;
            this.returnType = returnType;
        }

        private MethodWrapper(final String methodName, final Class<TARGET_CLASS> targetClass, final Class<RETURN_TYPE> returnType) {
            this(methodName, targetClass, null, returnType);
        }
    }
}
