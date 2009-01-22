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
package com.googlecode.pinthura.factory.boundary;

import com.googlecode.pinthura.annotation.SuppressionReason;

import java.lang.reflect.Field;

public final class FieldBoundaryImpl<T> implements FieldBoundary<T> {

    private final Field field;

    public FieldBoundaryImpl(final Field field) {
        this.field = field;
    }

    public String getName() {
        return field.getName();
    }

    @SuppressWarnings({"unchecked"})
    @SuppressionReason(SuppressionReason.Reason.CANT_INFER_GENERICS)
    public <I> T get(I instance) {
        try {
            return (T) field.get(instance);
        } catch (IllegalAccessException e) {
            throw new BoundaryException(e);
        }
    }

    @SuppressWarnings({"unchecked"})
    @SuppressionReason(SuppressionReason.Reason.CANT_INFER_GENERICS)
    public ClassBoundary<T> getType() {
        return new ClassBoundaryImpl(field.getType());
    }

    @SuppressWarnings({"CastToConcreteClass"})
    @SuppressionReason(SuppressionReason.Reason.GENERATED_CODE)
    @Override
    public boolean equals(final Object object) {
        return this == object || object != null && object instanceof FieldBoundaryImpl && field.equals(((FieldBoundaryImpl) object).field);
    }

    @Override
    public int hashCode() {
        return field.hashCode();
    }
}
