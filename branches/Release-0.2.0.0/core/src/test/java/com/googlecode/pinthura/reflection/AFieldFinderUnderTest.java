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
package com.googlecode.pinthura.reflection;

import com.googlecode.pinthura.boundary.java.lang.MathBoundary;
import com.googlecode.pinthura.factory.boundary.ClassBoundaryImpl;
import com.googlecode.pinthura.factory.boundary.FieldBoundary;
import com.googlecode.pinthura.injection.data.RandomIntegralValueIncubator;
import org.easymock.IMocksControl;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public final class AFieldFinderUnderTest {

    private FieldFinder fieldFinder;

    @Before
    public void setup() {
        fieldFinder = new FieldFinderImpl();
    }

    @Test
    public void shouldFindFieldsByPrefix() {
        List<FieldBoundary<?>> fields = fieldFinder.findByPrefix("mock", new RandomIntegralValueIncubator());

        assertThat(fields.size(), equalTo(3));
        assertContainsField("mockControl", IMocksControl.class, fields);
        assertContainsField("mockCounter", RandomIntegralValueIncubator.Counter.class, fields);
        assertContainsField("mockMathBoundary", MathBoundary.class, fields);
    }

    @Test
    public void shouldFindOtherFieldsByPrefix() {
        List<FieldBoundary<?>> fields = fieldFinder.findByPrefix("retur", new RandomIntegralValueIncubator());

        assertThat(fields.size(), equalTo(1));
        assertContainsField("returnedRandomValue", int.class, fields);
    }

    @Test
    public void shouldReturnAnEmptyListIfThePrefixIsNotFound() {
        assertThat(fieldFinder.findByPrefix("blah", new RandomIntegralValueIncubator()).isEmpty(), equalTo(true));        
    }

    private <T> void assertContainsField(final String varName, Class<T> clazz, final List<FieldBoundary<?>> fields) {
        for (FieldBoundary<?> field : fields) {
            if (field.getName().equals(varName) && field.getType().equals(new ClassBoundaryImpl<T>(clazz))) {
                return;
            }
        }

        fail();
    }
}
