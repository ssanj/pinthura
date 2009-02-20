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
import com.googlecode.pinthura.factory.SimpleInstantiatorBuilder;
import com.googlecode.pinthura.factory.locator.MethodParamBuilder;
import com.googlecode.pinthura.util.builder.RandomDataChooserBuilder;
import com.googlecode.pinthura.util.builder.RandomDataCreatorBuilder;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;
import org.junit.Before;
import org.junit.Test;
//TODO: Add more tests.
public final class ASimpleInstantiatorUnderIntTest {

    private String url;

    @Before
    public void setup() {
        String domain = new RandomDataCreatorBuilder().build().createString(7);
        String suffix = new RandomDataChooserBuilder().build().chooseOneOf("com", "co.uk", "com.au", "net");
        url = new StringBuilder().append("http://").append(domain).append(".").append(suffix).toString();
    }

    @Test
    public void shouldCreateAnInstanceOfTheRequiredClass() {
        Object result = new SimpleInstantiatorBuilder().build().process(createMethodParam());

        assertThat("SimpleInstantiator.process() returned null.", result, notNullValue());
        assertThat(result.getClass() == UrlBoundaryImpl.class, equalTo(true));
        assertThat(url, equalTo(((UrlBoundary) result).getUrlAsString()));
    }

    private MethodParam createMethodParam() {
        return new MethodParamBuilder().
                findMethod().
                onClass(UrlBoundaryFactory.class).
                havingMethodNameOf("createUrlBoundary").
                withParameter(url).
                build();
    }
}
