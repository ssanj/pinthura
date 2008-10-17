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
package com.googlecode.pinthura.factory.locator;

import com.googlecode.pinthura.annotation.AnnotationFinder;
import com.googlecode.pinthura.annotation.AnnotationNotFoundException;
import com.googlecode.pinthura.factory.ClassLocator;
import com.googlecode.pinthura.factory.MethodParam;
import com.googlecode.pinthura.filter.MatchNotFoundException;
import com.googlecode.pinthura.filter.annotation.InterfaceImpl;

public final class AnnotationLocatorImpl implements ClassLocator {

    private static final String FILTER_NAME = "An Annotation Filter";

    private final AnnotationFinder annotationFinder;

    public AnnotationLocatorImpl(final AnnotationFinder annotationFinder) {
        this.annotationFinder = annotationFinder;
    }

    @SuppressWarnings({ "unchecked" })
    public Class<?> filter(final MethodParam methodParam) {
        try {
            return annotationFinder.find(methodParam.getMethod(), InterfaceImpl.class).value();
        } catch (AnnotationNotFoundException e) {
           throw new MatchNotFoundException();
        }
    }

    public String getFilterName() {
        return FILTER_NAME;
    }
}
