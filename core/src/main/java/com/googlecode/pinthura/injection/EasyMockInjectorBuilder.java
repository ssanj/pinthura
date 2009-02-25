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
import com.googlecode.pinthura.boundary.java.lang.ClassBoundaryFactoryImpl;
import com.googlecode.pinthura.reflection.FieldFinder;
import com.googlecode.pinthura.reflection.FieldFinderImpl;
import com.googlecode.pinthura.reflection.FieldSetter;
import com.googlecode.pinthura.reflection.FieldSetterImpl;
import com.googlecode.pinthura.util.Arrayz;
import com.googlecode.pinthura.util.ArrayzImpl;

@SuppressWarnings({"MethodReturnOfConcreteClass"})
@SuppressionReason(SuppressionReason.Reason.BUILDER_PATTERN)
public final class EasyMockInjectorBuilder {

    private FieldFinder fieldFinder;
    private FieldSetter fieldSetter;
    private EasyMockWrapper easyMockWrapper;
    private MockConfigurer mockConfigurer;
    private Arrayz arrayz;

    public EasyMockInjectorBuilder() {
        arrayz = new ArrayzImpl();
        fieldFinder = new FieldFinderImpl(arrayz, new ClassBoundaryFactoryImpl());
        fieldSetter = new FieldSetterImpl();
        easyMockWrapper = new EasyMockWrapperImpl();
        mockConfigurer = new MockPrefixMockConfigurer();
    }

    public EasyMockInjectorBuilder withFieldFinder(final FieldFinder fieldFinder) {
        this.fieldFinder = fieldFinder;
        return this;
    }

    public EasyMockInjectorBuilder withFieldSetter(final FieldSetter fieldSetter) {
        this.fieldSetter = fieldSetter;
        return this;
    }

    public EasyMockInjectorBuilder withEasyMockWrapper(final EasyMockWrapper easyMockWrapper) {
        this.easyMockWrapper = easyMockWrapper;
        return this;
    }

    public EasyMockInjectorBuilder withMockConfigurer(final MockConfigurer mockConfigurer) {
        this.mockConfigurer = mockConfigurer;
        return this;
    }

    public EasyMockInjectorBuilder withArrayz(final Arrayz arrayz) {
        this.arrayz = arrayz;
        return this;
    }

    public MockInjector build() {
        return new EasyMockInjectorImpl(mockConfigurer, fieldFinder, fieldSetter, easyMockWrapper, arrayz);
    }
}
