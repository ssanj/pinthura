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

import com.googlecode.pinthura.data.UrlBoundary;
import com.googlecode.pinthura.data.UrlBoundaryFactory;
import com.googlecode.pinthura.data.UrlBoundaryImpl;
import com.googlecode.pinthura.factory.MethodParam;
import com.googlecode.pinthura.factory.instantiator.builder.SimpleInstantiatorBuilder;
import com.googlecode.pinthura.factory.locator.MethodParamBuilder;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;
import org.junit.Test;

public final class ASimpleInstantiatorUnderIntTest {

    private static final String URL = "http://testing123.com";

    @Test
    public void shouldCreateAnInstanceOfTheRequiredClass() {
        Object result = new SimpleInstantiatorBuilder().build().filter(createMethodParam());

        assertThat(result, notNullValue());
        assertThat(result.getClass() == UrlBoundaryImpl.class, equalTo(true));
        assertThat(URL, equalTo(((UrlBoundary) result).getUrlAsString()));
    }

    @SuppressWarnings("unchecked")
    private MethodParam createMethodParam() {
        return new MethodParamBuilder<String, UrlBoundaryFactory>().
                forInterface(UrlBoundaryFactory.class).
                havingMethod("createUrlBoundary", String.class).
                withArgument(URL).
                build();
    }
}
