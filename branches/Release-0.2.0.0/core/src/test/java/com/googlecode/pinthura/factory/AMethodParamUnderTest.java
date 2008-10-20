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

import com.googlecode.pinthura.data.UrlBoundaryFactory;
import com.googlecode.pinthura.data.WidgetFactory;
import com.googlecode.pinthura.factory.locator.MethodParamBuilder;
import static org.hamcrest.core.IsEqual.equalTo;
import org.hamcrest.core.IsNot;
import static org.hamcrest.core.IsNot.*;
import static org.junit.Assert.assertThat;
import org.junit.Test;

public final class AMethodParamUnderTest {

    private static final String FACTORY_METHOD_1                    = "createUrlBoundary";
    private static final Class<UrlBoundaryFactory> FACTORY_CLASS_1  = UrlBoundaryFactory.class;
    private static final String URL                                 = "http://someurl";

    private static final String FACTORY_METHOD_2                    = "createWidget";
    private static final Class<WidgetFactory> FACTORY_CLASS_2       = WidgetFactory.class;

    @SuppressWarnings({ "unchecked" })
    @Test
    public void shouldEquateASetOfIdenticalValues() {
        assertThat(createMethodParam1(), equalTo(createMethodParam1()));
    }

    @Test
    public void shouldEquateAnotherSetOfIdenticalValues() {
        assertThat(createMethodParam2(), equalTo(createMethodParam2()));
    }

    @Test
    public void shouldNotEquateDifferentValues() {
        assertThat(createMethodParam2(), not(equalTo(createMethodParam1())));

    }

    @SuppressWarnings({ "unchecked" })
    private MethodParam createMethodParam1() {
        return new MethodParamBuilder().forInterface(FACTORY_CLASS_1).havingMethod( FACTORY_METHOD_1, String.class).withArgument(URL).
                build();
    }

    @SuppressWarnings({ "unchecked" })
    private MethodParam createMethodParam2() {
        return new MethodParamBuilder().forInterface(FACTORY_CLASS_2).havingMethod(FACTORY_METHOD_2).build();
    }
}
