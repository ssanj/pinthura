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
import com.googlecode.pinthura.reflection.FieldFinder;
import com.googlecode.pinthura.reflection.FieldSetter;

@SuppressWarnings({"MethodReturnOfConcreteClass"})
@SuppressionReason(SuppressionReason.Reason.BUILDER_PATTERN)
public final class MockInjectorBuilder {

    private FieldFinder fieldFinder;
    private FieldSetter fieldSetter;
    private EasyMockWrapper easyMockWrapper;

    public MockInjectorBuilder withFieldFinder(final FieldFinder fieldFinder) {
        this.fieldFinder = fieldFinder;
        return this;
    }

    public MockInjectorBuilder withFieldSetter(final FieldSetter fieldSetter) {
        this.fieldSetter = fieldSetter;
        return this;
    }

    public MockInjectorBuilder withEasyMockWrapper(final EasyMockWrapper easyMockWrapper) {
        this.easyMockWrapper = easyMockWrapper;
        return this;
    }

    public MockInjector build() {
        return new MockInjectorImpl(fieldFinder, fieldSetter, easyMockWrapper);
    }
}