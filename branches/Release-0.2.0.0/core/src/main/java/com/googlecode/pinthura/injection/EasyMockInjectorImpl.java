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
import com.googlecode.pinthura.boundary.java.lang.reflect.FieldBoundary;
import com.googlecode.pinthura.injection.filters.ItemFilter;
import com.googlecode.pinthura.reflection.FieldFinder;
import com.googlecode.pinthura.reflection.FieldSetter;
import com.googlecode.pinthura.util.Arrayz;
import org.easymock.IMocksControl;

import java.util.List;

/**
 * Injects EasyMock mocks into mockable fields.
 *
 * @see <a href="http://www.easymock.org">EasyMock</a>
 */
public final class EasyMockInjectorImpl implements MockInjector {

    private final MockConfigurer mockConfigurer;
    private final FieldFinder fieldFinder;
    private final FieldSetter fieldSetter;
    private final EasyMockWrapper easyMockWrapper;
    private final Arrayz arrayz;

    /**
     * Constructor.
     *
     * @param mockConfigurer specifies information about mockable fields.
     * @param fieldFinder Strategy for finding fields.
     * @param fieldSetter Strategy for setting fields.
     * @param easyMockWrapper Wrapper EasyMock methods.
     * @param arrayz Utility class for handling arrays. 
     */
    //TODO: There are too many parameters here. Introduce a parameter object.
    public EasyMockInjectorImpl(final MockConfigurer mockConfigurer, final FieldFinder fieldFinder, final FieldSetter fieldSetter,
                                final EasyMockWrapper easyMockWrapper, final Arrayz arrayz) {
        this.mockConfigurer = mockConfigurer;
        this.fieldFinder = fieldFinder;
        this.fieldSetter = fieldSetter;
        this.easyMockWrapper = easyMockWrapper;
        this.arrayz = arrayz;
    }

    /**
     * Injects mocks into the specified instance.
     *
     * @param instance The instance whose fields are to be mocked.
     * @param <I> The type of the instance.
     * @return The instance with its fields mocked out.
     */
    @SuppressWarnings("unchecked")
    @SuppressionReason(SuppressionReason.Reason.CANT_INFER_GENERICS)
    public <I> I inject(final I instance) {
        try {
            FieldBoundary<IMocksControl> mockControlField = fieldFinder.findByName(mockConfigurer.getMockControlName(), instance);
            fieldSetter.setValue(mockControlField, instance, easyMockWrapper.createControl());
            List<FieldBoundary<?>> fields = filterMockControl(fieldFinder.findByPrefix(mockConfigurer.getMockPrefix(), instance),
                    mockConfigurer.getMockControlName());

            for (FieldBoundary field : fields) {
                fieldSetter.setValue(field, instance, easyMockWrapper.createMock(mockControlField.get(instance), field.getType()));
            }
        } catch (Exception e) {
            throw new MockInjectionException(e);
        }

        return instance;
    }

    /**
     * Given a list of fields, and the name of the mock control field, filters out the mock control field if it exists
     * in the list of fields supplied.
     *
     * @param fieldsByPrefix The list of fields matching a certain prefix.
     * @param mockControlName The name of the field for the mock control.
     * @return The list of fields without the mock control field.
     */
    private List<FieldBoundary<?>> filterMockControl(final List<FieldBoundary<?>> fieldsByPrefix, final String mockControlName) {
        return arrayz.filter(fieldsByPrefix, new RemoveMockControlFromPrefixListFilter(mockControlName));
    }

    /**
     * Removes the mock control field from from a list of supplied fields.
     */
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
