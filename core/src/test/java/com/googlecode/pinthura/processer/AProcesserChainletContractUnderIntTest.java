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
package com.googlecode.pinthura.processer;

import com.googlecode.pinthura.data.UrlBoundaryFactory;
import com.googlecode.pinthura.data.WidgetFactory;
import com.googlecode.pinthura.factory.AnnotationInstantiatorBuilder;
import com.googlecode.pinthura.factory.MethodParam;
import com.googlecode.pinthura.factory.MethodParamImpl;
import com.googlecode.pinthura.factory.SimpleInstantiatorBuilder;
import com.googlecode.pinthura.factory.locator.MethodParamBuilder;
import org.junit.Test;

public final class AProcesserChainletContractUnderIntTest {

    private static final int WIDGET_ID = 100;

    @SuppressWarnings("unchecked")
    @Test
    public void shouldBeHonouredBySimpleImplementationLocator() throws NoSuchMethodException {
    ProcesserChainletContractChecker.checkContract(
                new SimpleInstantiatorBuilder().build(), buildValidSimpleMethodParam(), buildInvalidSimpleMethodParam());
}
    @Test
    public void shouldBeHonouredByAnnotationLocator() {
        ProcesserChainletContractChecker.checkContract(
                new AnnotationInstantiatorBuilder().build(), buildValidAnnotationMethodParam(), buildInvalidAnnotationMethodParam());
    }

    @SuppressWarnings("unchecked")
    private MethodParam buildInvalidAnnotationMethodParam() {
        return new MethodParamBuilder().forInterface(WidgetFactory.class).havingMethod("createWidget").build();
    }

    @SuppressWarnings("unchecked")
    private MethodParam buildValidAnnotationMethodParam() {
        return new MethodParamBuilder().forInterface(WidgetFactory.class).havingMethod("createWidget", Integer.class).
                withArgument(WIDGET_ID).build();
    }

    @SuppressWarnings("unchecked")
    private MethodParam buildValidSimpleMethodParam() {
        return new MethodParamBuilder().forInterface(UrlBoundaryFactory.class).havingMethod("createUrlBoundary").build();
    }

    private MethodParam buildInvalidSimpleMethodParam() throws NoSuchMethodException {
        return new MethodParamImpl(Object.class.getMethod("toString"), new Object[] {});
    }
}
