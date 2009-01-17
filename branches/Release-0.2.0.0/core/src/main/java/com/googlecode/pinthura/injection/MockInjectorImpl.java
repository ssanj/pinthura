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
import org.easymock.EasyMock;
import org.easymock.IMocksControl;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public final class MockInjectorImpl implements MockInjector {

    public <T> T inject(final T instance) {
        try {
            Field mockControl = instance.getClass().getDeclaredField("mockControl");
            mockControl.setAccessible(true);
            mockControl.set(instance, EasyMock.createControl());

            Field[] fields = getFieldsByPrefix(instance, "mock", "mockControl");
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

    private <T> Field[] getFieldsByPrefix(final T instance, final String prefix, final String exclude) {
        Field[] fields = instance.getClass().getDeclaredFields();

        List<Field> filteredFields = new ArrayList<Field>();
        for (Field field : fields) {
            if (field.getName().startsWith(prefix) && !field.getName().equals(exclude)) {
                filteredFields.add(field);
            }
        }

        return Arrayz.fromCollection(filteredFields, Field.class);
    }
}
