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

import com.googlecode.pinthura.util.Arrayz;
import com.googlecode.pinthura.util.ItemFilter;
import org.easymock.EasyMock;
import org.easymock.IMocksControl;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

//TODO: Refactor
public final class MockInjectorImpl implements MockInjector {

    public <T> T inject(final T instance) {
        try {
            Field mockControl = instance.getClass().getDeclaredField("mockControl");
            mockControl.setAccessible(true);
            mockControl.set(instance, EasyMock.createControl());

            List<Field> fields = filterMockControl(getFieldsByPrefix(instance, "mock"), "mockControl");
            for (Field field : fields) {
                Field mock = instance.getClass().getDeclaredField(field.getName());
                mock.setAccessible(true);
                mock.set(instance, ((IMocksControl) mockControl.get(instance)).createMock(mock.getType()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return instance;
    }

    private List<Field> filterMockControl(final List<Field> fieldsByPrefix, final String mockControlName) {
        return Arrayz.filter(fieldsByPrefix, new RemoveMockControlFromPrefixListFilter(mockControlName));
    }

    private <T> List<Field> getFieldsByPrefix(final T instance, final String prefix) {
        Field[] fields = instance.getClass().getDeclaredFields();
        return Arrayz.filter(Arrays.asList(fields), new MockPrefixFilter(prefix));
    }

    private static final class MockPrefixFilter implements ItemFilter<Field> {

        private final String prefix;

        public MockPrefixFilter(final String prefix) {
            this.prefix = prefix;
        }

        public boolean include(final Field item) {
            return item.getName().startsWith(prefix);
        }
    }

    private class RemoveMockControlFromPrefixListFilter implements ItemFilter<Field> {

        private final String mockControlName;

        public RemoveMockControlFromPrefixListFilter(final String mockControlName) {
            this.mockControlName = mockControlName;
        }

        public boolean include(final Field item) {
            return !item.getName().equals(mockControlName);
        }
    }
}
