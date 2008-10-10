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

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Method;import static junit.framework.Assert.fail;

public final class APropertyFinderUnderTest {

    private PropertyFinder propertyFinder;

    @Before
    public void setup() {
        propertyFinder = new PropertyFinderImpl();
    }

    @Test
    public void shouldReturnAProperty() throws NoSuchMethodException {
        Method result = propertyFinder.executeMethod("package", Class.class);
        assertThat(result, equalTo(Class.class.getMethod("getPackage")));
    }

    @Test
    public void shouldReturnABooleanProperty() throws NoSuchMethodException {
        Method result = propertyFinder.executeMethod("sealed", Package.class);
        assertThat(result, equalTo(Package.class.getMethod("isSealed")));
    }

    @Test
    public void shouldThrownAnExceptionIfThePropertyIsNotFound() {
        try {
            propertyFinder.executeMethod("blah", String.class);
            fail();
        } catch (PropertyFinderException e) {
            assertThat(e.getMessage(), equalTo("Could not find property: blah on class java.lang.String"));
        }
    }
}
