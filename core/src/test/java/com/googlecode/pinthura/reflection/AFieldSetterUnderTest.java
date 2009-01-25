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
import com.googlecode.pinthura.data.SquareImpl;
import com.googlecode.pinthura.factory.boundary.FieldBoundary;
import com.googlecode.pinthura.util.Arrayz;
import com.googlecode.pinthura.util.RandomDataChooser;
import com.googlecode.pinthura.util.builder.RandomDataChooserBuilder;
import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import org.junit.Before;
import org.junit.Test;

public final class AFieldSetterUnderTest {

    private IMocksControl mockControl;
    private FieldSetter fieldSetter;
    private FieldBoundary mockFieldBoundary;
    private RandomDataChooser random;

    @Before
    public void setup() {
        mockControl = EasyMock.createControl();
        random = new RandomDataChooserBuilder().build();
        mockFieldBoundary = mockControl.createMock(FieldBoundary.class);
        fieldSetter = new FieldSetterImpl();
    }

    @SuppressWarnings({"unchecked"})
    @SuppressionReason(SuppressionReason.Reason.CANT_INFER_GENERICS_ON_MOCKS)
    @Test
    public void shouldSetAGivenFieldOnAnGivenInstance() {
        Object[] objects = Arrayz.fromObjects("blah", 500, new SquareImpl(6));
        Object instance = random.chooseOneOf(objects);
        Object value = random.chooseOneOf(objects);
        mockFieldBoundary.setAccessible(true);
        mockFieldBoundary.set(instance, value);
        mockControl.replay();

        fieldSetter.setValue(mockFieldBoundary, instance, value);

        mockControl.verify();
    }
}
