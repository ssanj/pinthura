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
package com.googlecode.pinthura.injection;

import com.googlecode.pinthura.annotation.SuppressionReason;
import com.googlecode.pinthura.data.UrlBoundaryImpl;
import com.googlecode.pinthura.factory.boundary.ClassBoundary;
import com.googlecode.pinthura.factory.boundary.FieldBoundary;
import com.googlecode.pinthura.reflection.FieldFinder;
import com.googlecode.pinthura.reflection.FieldSetter;
import com.googlecode.pinthura.util.RandomDataChooser;
import com.googlecode.pinthura.util.builder.RandomDataChooserBuilder;
import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public final class AMockInjectorUnderTest {

    private IMocksControl mockControl;
    private MockInjector mockInjector;
    private FieldFinder mockFieldFinder;
    private FieldSetter mockFieldSetter;
    private EasyMockWrapper mockEasyMockWrapper;
    private String instance;
    private RandomDataChooser randomDataChooser;
    private Deux<FieldBoundary,ClassBoundary> fieldClassBoundary1;
    private Deux<FieldBoundary,ClassBoundary> fieldClassBoundary2;
    private Deux<FieldBoundary,IMocksControl> fieldMockControl;
    private List<FieldBoundary<?>> fieldsPrefixedWithMock;

    @Before
    public void setup() {
        mockControl = EasyMock.createControl();
        mockFieldFinder = mockControl.createMock(FieldFinder.class);
        mockFieldSetter = mockControl.createMock(FieldSetter.class);
        mockEasyMockWrapper = mockControl.createMock(EasyMockWrapper.class);

        fieldClassBoundary1 = createFieldClassBoundaryDeux();
        fieldClassBoundary2 = createFieldClassBoundaryDeux();
        fieldMockControl = createFieldMockControlDeux();

        randomDataChooser = new RandomDataChooserBuilder().build();
        fieldsPrefixedWithMock = Arrays.<FieldBoundary<?>>asList(fieldClassBoundary1.getOne(), fieldMockControl.getOne(),
                fieldClassBoundary2.getOne());

        mockInjector = new MockInjectorBuilder().
                            withFieldFinder(mockFieldFinder).
                            withFieldSetter(mockFieldSetter).
                            withEasyMockWrapper(mockEasyMockWrapper).
                            build();

        instance = "this could be any instance";
    }

    @Test
    public void shouldInjectMocksIntoTheInstanceSupplied() {
        expectMockControl();
        expectPrefixedFields();
        expectInjectedField(fieldClassBoundary1, getRandomValue());
        expectInjectedField(fieldClassBoundary2, getRandomValue());
        mockControl.replay();

        mockInjector.inject(instance);

        mockControl.verify();
    }

    private Object getRandomValue() {
        return randomDataChooser.chooseOneOf(5, "test", 2.6f, new UrlBoundaryImpl());
    }

    private Deux<FieldBoundary, IMocksControl> createFieldMockControlDeux() {
        return new DeuxImpl<FieldBoundary, IMocksControl>(mockControl.createMock(FieldBoundary.class),
                mockControl.createMock(IMocksControl.class));
    }

    private Deux<FieldBoundary, ClassBoundary> createFieldClassBoundaryDeux() {
        return new DeuxImpl<FieldBoundary, ClassBoundary>(mockControl.createMock(FieldBoundary.class),
                mockControl.createMock(ClassBoundary.class));
    }

    private void expectPrefixedFields() {
        EasyMock.expect(mockFieldFinder.findByPrefix("mock", instance)).andReturn(fieldsPrefixedWithMock);
        EasyMock.expect(fieldClassBoundary1.getOne().getName()).andReturn("mockBlah");
        EasyMock.expect(fieldMockControl.getOne().getName()).andReturn("mockControl");
        EasyMock.expect(fieldClassBoundary2.getOne().getName()).andReturn("mockToodaloo");
    }

    @SuppressWarnings({"unchecked"})
    @SuppressionReason(SuppressionReason.Reason.CANT_INFER_GENERICS_ON_MOCKS)
    private <T> void expectInjectedField(final Deux<FieldBoundary, ClassBoundary> fieldAndClassBoundary, T value) {
        EasyMock.expect(fieldMockControl.getOne().get(instance)).andReturn(fieldMockControl.getTwo());
        EasyMock.expect(fieldAndClassBoundary.getOne().getType()).andReturn(fieldAndClassBoundary.getTwo());
        EasyMock.expect(mockEasyMockWrapper.createMock(fieldMockControl.getTwo(), fieldAndClassBoundary.getTwo())).andReturn(value);
        mockFieldSetter.setValue(fieldAndClassBoundary.getOne(),  instance, value);
    }

    @SuppressWarnings({"unchecked"})
    @SuppressionReason(SuppressionReason.Reason.CANT_INFER_GENERICS_ON_MOCKS)
    private void expectMockControl() {
        EasyMock.expect(mockFieldFinder.findByName("mockControl", instance)).andReturn(fieldMockControl.getOne());
        EasyMock.expect(mockEasyMockWrapper.createControl()).andReturn(fieldMockControl.getTwo());
        mockFieldSetter.setValue(fieldMockControl.getOne(), instance, fieldMockControl.getTwo());
    }

    private interface Deux<O,T> {

        O getOne();
        T getTwo();
    }

    private static class DeuxImpl<O,T> implements Deux<O,T> {
        private final O one;
        private final T two;

        private DeuxImpl(final O one, final T two) {
            this.one = one;
            this.two = two;
        }

        public O getOne() {
            return one;
        }

        public T getTwo() {
            return two;
        }
    }
}
