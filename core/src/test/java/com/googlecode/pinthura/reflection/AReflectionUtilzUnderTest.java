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
import com.googlecode.pinthura.factory.boundary.ClassBoundary;
import com.googlecode.pinthura.factory.boundary.FieldBoundary;
import com.googlecode.pinthura.factory.instantiator.ClassInstance;
import com.googlecode.pinthura.util.Arrayz;
import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;
import org.junit.Before;
import org.junit.Test;

public final class AReflectionUtilzUnderTest {

    private IMocksControl mockControl;
    private ReflectionUtilz utilz;
    private ClassInstance mockClassInstance;

    @Before
    public void setup() {
        mockControl = EasyMock.createControl();
        mockClassInstance = mockControl.createMock(ClassInstance.class);

        utilz = new ReflectionUtilzImpl();
    }

    @SuppressWarnings({"unchecked"})
    @SuppressionReason(SuppressionReason.Reason.CANT_INFER_GENERICS)
    @Test
    public void shouldFindFieldsThatMatchTheSuppliedPrefix() {
        ClassBoundary mockClassBoundary = mockControl.createMock(ClassBoundary.class);
        EasyMock.expect(mockClassInstance.getClazz()).andReturn(mockClassBoundary);
        FieldBoundary mockFieldBoundary1 = mockControl.createMock(FieldBoundary.class);
        FieldBoundary mockFieldBoundary2 = mockControl.createMock(FieldBoundary.class);
        EasyMock.expect(mockFieldBoundary1.getName()).andReturn("blahMathBoundary");
        EasyMock.expect(mockFieldBoundary2.getName()).andReturn("blahClassInstanceFactory");
        EasyMock.expect(mockClassBoundary.getDeclaredFields()).andReturn(Arrayz.fromObjects(mockFieldBoundary1, mockFieldBoundary2));
        mockControl.replay();

        FieldBoundary[] fieldBoundaries = utilz.findFields(mockClassInstance, "blah");

        assertThat(fieldBoundaries.length, equalTo(2));
        assertThat(fieldBoundaries[0], equalTo(mockFieldBoundary1));
        assertThat(fieldBoundaries[1], equalTo(mockFieldBoundary2));

        mockControl.verify();
    }
}
