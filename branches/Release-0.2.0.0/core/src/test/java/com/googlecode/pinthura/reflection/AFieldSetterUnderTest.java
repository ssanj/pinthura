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

import com.googlecode.pinthura.annotation.SuppressionReason;
import com.googlecode.pinthura.boundary.java.lang.reflect.FieldBoundary;
import com.googlecode.pinthura.data.SquareImpl;
import com.googlecode.pinthura.util.ArrayzImpl;
import com.googlecode.pinthura.util.RandomDataChooser;
import com.googlecode.pinthura.util.builder.RandomDataChooserBuilder;
import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.fail;
import static org.junit.Assert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

public final class AFieldSetterUnderTest {

    private IMocksControl mockControl;
    private FieldSetter fieldSetter;
    private FieldBoundary mockFieldBoundary;
    private Object instance;
    private Object value;

    @Before
    public void setup() {
        mockControl = EasyMock.createControl();
        mockFieldBoundary = mockControl.createMock(FieldBoundary.class);

        RandomDataChooser random = new RandomDataChooserBuilder().build();
        Object[] objects = new ArrayzImpl().fromObjects("blah", 500, new SquareImpl(6));
        instance = random.chooseOneOf(objects);
        value = random.chooseOneOf(objects);

        fieldSetter = new FieldSetterImpl();
    }

    @SuppressWarnings({"unchecked"})
    @SuppressionReason(SuppressionReason.Reason.CANT_INFER_GENERICS_ON_MOCKS)
    @Test
    public void shouldSetAGivenFieldOnAnGivenInstance() {
        mockFieldBoundary.setAccessible(true);
        mockFieldBoundary.set(instance, value);
        mockControl.replay();

        fieldSetter.setValue(mockFieldBoundary, instance, value);

        mockControl.verify();
    }

    @SuppressWarnings({"unchecked", "ThrowableInstanceNeverThrown"})
    @SuppressionReason({SuppressionReason.Reason.CANT_INFER_GENERICS_ON_MOCKS, SuppressionReason.Reason.TEST_VALUE})
    @Test
    public void shouldThrowABoundaryExceptionWhenAFieldCantBeSet() {
        mockFieldBoundary.setAccessible(true);
        mockFieldBoundary.set(instance, value);
        EasyMock.expectLastCall().andThrow(new IllegalArgumentException("blah"));
        mockControl.replay();

        try {
            fieldSetter.setValue(mockFieldBoundary, instance, value);
            fail();
        } catch (FieldSetterException e) {
            assertThat((Class<IllegalArgumentException>) e.getCause().getClass(),
                    equalTo(IllegalArgumentException.class));
            assertThat(e.getCause().getMessage(), equalTo("blah"));
        }
    }
}
