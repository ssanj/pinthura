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

public final class PathEvaluatorImpl implements PathEvaluator {

    private static final String PATH_SEPARATOR = "\\.";

    private final PropertyFinder propertyFinder;

    public PathEvaluatorImpl(final PropertyFinder propertyFinder) {
        this.propertyFinder = propertyFinder;
    }

    @SuppressWarnings({ "unchecked" })
    public <Instance, Result> Result evaluate(final String path, final Instance instance) {
        String[] properties = path.split(PATH_SEPARATOR);
        Object currentInstance = instance;
        for (String property : properties) {
            try {
                Method method = propertyFinder.findMethodFor(property, currentInstance.getClass());
                currentInstance = method.invoke(currentInstance);
            } catch (Exception e) {
                throw new PathEvaluatorException(e);
            }
        }

        return (Result) currentInstance;
    }
}
