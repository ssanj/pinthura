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
import com.googlecode.pinthura.factory.boundary.FieldBoundary;
import com.googlecode.pinthura.reflection.FieldFinder;
import com.googlecode.pinthura.reflection.FieldSetter;
import com.googlecode.pinthura.util.Arrayz;
import com.googlecode.pinthura.util.ItemFilter;
import org.easymock.IMocksControl;

import java.util.List;

public final class EasyMockInjectorImpl implements MockInjector {

    private final MockConfigurer mockConfigurer;
    private final FieldFinder fieldFinder;
    private final FieldSetter fieldSetter;
    private final EasyMockWrapper easyMockWrapper;

    public EasyMockInjectorImpl(final MockConfigurer mockConfigurer, final FieldFinder fieldFinder,
                            final FieldSetter fieldSetter, final EasyMockWrapper easyMockWrapper) {
        this.mockConfigurer = mockConfigurer;
        this.fieldFinder = fieldFinder;
        this.fieldSetter = fieldSetter;
        this.easyMockWrapper = easyMockWrapper;
    }

    @SuppressWarnings({"unchecked"})
    @SuppressionReason(SuppressionReason.Reason.CANT_INFER_GENERICS)
    public <I> I inject(final I instance) {
        FieldBoundary<IMocksControl> mockControlField = fieldFinder.findByName(mockConfigurer.getMockControlName(), instance);
        fieldSetter.setValue(mockControlField, instance, easyMockWrapper.createControl());
        List<FieldBoundary<?>> fields = filterMockControl(fieldFinder.findByPrefix(mockConfigurer.getMockPrefix(), instance),
                mockConfigurer.getMockControlName());

        for (FieldBoundary field : fields) {
            fieldSetter.setValue(field, instance, easyMockWrapper.createMock(mockControlField.get(instance), field.getType()));
        }

        return instance;
    }

    private List<FieldBoundary<?>> filterMockControl(final List<FieldBoundary<?>> fieldsByPrefix, final String mockControlName) {
        return Arrayz.filter(fieldsByPrefix, new RemoveMockControlFromPrefixListFilter(mockControlName));
    }

    private class RemoveMockControlFromPrefixListFilter implements ItemFilter<FieldBoundary<?>> {

        private final String mockControlName;

        public RemoveMockControlFromPrefixListFilter(final String mockControlName) {
            this.mockControlName = mockControlName;
        }

        public boolean include(final FieldBoundary<?> item) {
            return !item.getName().equals(mockControlName);
        }
    }
}
