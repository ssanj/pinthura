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

import com.googlecode.pinthura.boundary.java.lang.ClassBoundaryImpl;
import com.googlecode.pinthura.boundary.java.lang.MathBoundary;
import com.googlecode.pinthura.boundary.java.lang.reflect.FieldBoundary;
import com.googlecode.pinthura.data.SquareImpl;
import com.googlecode.pinthura.injection.data.RandomIntegralValueIncubator;
import com.googlecode.pinthura.util.ArrayzImpl;
import org.easymock.IMocksControl;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public final class AFieldFinderUnderIntTest {

    private FieldFinder fieldFinder;

    @Before
    public void setup() {
        fieldFinder = new FieldFinderImpl(new ArrayzImpl());
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
        List<FieldBoundary<?>> fields = fieldFinder.findByPrefix("len", new SquareImpl(5));

        assertThat(fields.size(), equalTo(1));
        assertContainsField("length", int.class, fields);
    }

    @Test
    public void shouldThrowAnExceptionIfThePrefixIsNotFound() {
        try {
            fieldFinder.findByPrefix("blah", new RandomIntegralValueIncubator());
            fail();
        } catch (FieldFinderException e) {
            assertThat(e.getMessage(), equalTo("Could not find any fields prefixed with [blah] " +
                                               "on class [com.googlecode.pinthura.injection.data.RandomIntegralValueIncubator]"));
        }
    }

    @Test
    public void shouldFindANamedField() {
        assertFindByName("mockControl");
    }

    @Test
    public void shouldFindAnotherNamedField() {
        assertFindByName("cut");
    }

    @Test
    public void shouldThrowAnExceptionIfTheNamedFieldIsNotFound() {
        try {
            fieldFinder.findByName("bluu", new SquareImpl(2));
            fail();
        } catch (FieldFinderException e) {
            assertThat(e.getMessage(), equalTo("Could not find field [bluu] on class [com.googlecode.pinthura.data.SquareImpl]"));
        }
    }

    private void assertFindByName(final String varName) {
        FieldBoundary field = fieldFinder.findByName(varName, new RandomIntegralValueIncubator());
        assertThat(field, notNullValue());
        assertThat(field.getName(), equalTo(varName));
    }

    private <T> void assertContainsField(final String varName, final Class<T> clazz, final List<FieldBoundary<?>> fields) {
        for (FieldBoundary<?> field : fields) {
            if (field.getName().equals(varName) && field.getType().equals(new ClassBoundaryImpl<T>(clazz))) {
                return;
            }
        }

        fail();
    }
}
