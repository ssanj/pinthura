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
import com.googlecode.pinthura.boundary.java.lang.ClassBoundaryImpl;
import com.googlecode.pinthura.boundary.java.lang.reflect.FieldBoundary;
import com.googlecode.pinthura.boundary.java.lang.reflect.FieldBoundaryImpl;
import com.googlecode.pinthura.injection.filters.ItemFilter;
import com.googlecode.pinthura.util.Arrayz;

import java.util.List;

public final class FieldFinderImpl implements FieldFinder {

    @SuppressWarnings({ "unchecked" })
    @SuppressionReason(SuppressionReason.Reason.CANT_INFER_GENERICS)
    public <T> FieldBoundary<T> findByName(final String varName, final Object instance) {
        try {
            return new FieldBoundaryImpl(instance.getClass().getDeclaredField(varName));
        } catch (NoSuchFieldException e) {
            throw new FieldFinderException(createCouldNotFindFieldMessage(varName, instance), e);
        }
    }

    @SuppressWarnings({ "unchecked" })
    @SuppressionReason(SuppressionReason.Reason.CANT_INFER_GENERICS)
    public List<FieldBoundary<?>> findByPrefix(final String prefix, final Object instance) {
        List<FieldBoundary<?>> fields = Arrayz.filter(new ClassBoundaryImpl(instance.getClass()).getDeclaredFields(),
                new PrefixedFieldsFilter(prefix));

        if (hasPrefixedFields(fields)) {
            return fields;
        }

        throw new FieldFinderException(createCouldNotFindPrefixMessage(prefix, instance));
    }

    private String createCouldNotFindFieldMessage(final String varName, final Object instance) {
        return "Could not find field [" + varName + "] on class [" + instance.getClass().getName() + "]";
    }

    private String createCouldNotFindPrefixMessage(final String prefix, final Object instance) {
        return "Could not find any fields prefixed with [" + prefix + "] on class [" + instance.getClass().getName() + "]";
    }

    private boolean hasPrefixedFields(final List<FieldBoundary<?>> fields) {
        return !fields.isEmpty();
    }

    private static class PrefixedFieldsFilter implements ItemFilter<FieldBoundary<?>> {
        private final String prefix;

        public PrefixedFieldsFilter(final String prefix) {
            this.prefix = prefix;
        }

        public boolean include(final FieldBoundary<?> item) {
            return item.getName().startsWith(prefix);
        }
    }
}
