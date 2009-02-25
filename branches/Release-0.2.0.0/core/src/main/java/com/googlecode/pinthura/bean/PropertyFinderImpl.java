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
package com.googlecode.pinthura.bean;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

/**
 * Finds a <code>Method</code> given a property and a <code>Class</code>.
 * Uses a strategy of get[methodName], is[MethodName] and methodName respectively in turn to find the correct method
 * on the supplied <code>Class</code>.
 */
public final class PropertyFinderImpl implements PropertyFinder {

    public <ParentClass> Method findMethodFor(final String property, final Class<ParentClass> parentClass)
            throws PropertyFinderException {
        List<String> properties = getCombinations(property);

        for (String methodName : properties) {
            try {
                return parentClass.getMethod(methodName);
                //CHECKSTYLE_OFF
            } catch (Exception e) {
                //do nothing.
                //CHECKSTYLE_ON
            }
        }

        throw new PropertyFinderException("Could not find property: " + property + " on " + parentClass);
    }

    private List<String> getCombinations(final String property) {
        String formattedProperty = property.substring(0, 1).toUpperCase(Locale.ENGLISH) + property.substring(1);
        return Arrays.asList(new StringBuilder("get").append(formattedProperty).toString(),
                             new StringBuilder("is").append(formattedProperty).toString(),
                             property);
    }
}
