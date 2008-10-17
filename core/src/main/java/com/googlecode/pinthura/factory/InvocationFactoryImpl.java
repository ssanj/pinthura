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
package com.googlecode.pinthura.factory;

import com.googlecode.pinthura.annotation.AnnotationFinderImpl;
import com.googlecode.pinthura.factory.locator.AnnotationLocatorImpl;
import com.googlecode.pinthura.factory.locator.DerivedImplementationNameLocator;
import com.googlecode.pinthura.factory.locator.deriver.ImplSuffixingDeriver;
import com.googlecode.pinthura.filter.FilterChainImpl;

import java.lang.reflect.InvocationHandler;
import java.util.ArrayList;
import java.util.List;

public final class InvocationFactoryImpl implements InvocationFactory {

    private final List<ClassLocator> locators;
    private final MethodParamFactory factory;

    public InvocationFactoryImpl() {
        factory = new MethodParamFactoryImpl();
        locators = createDefaultLocators();
    }

    public InvocationFactoryImpl(final List<ClassLocator> locators) {
        factory = new MethodParamFactoryImpl();
        this.locators = locators;
    }

    public InvocationHandler create() {
        ClassLocator locatorChain = new ClassLocatorChain(new FilterChainImpl<MethodParam, Class<?>>(locators));
        return new FactoryCreationInvocationHandler(locatorChain, factory);
    }

    private List<ClassLocator> createDefaultLocators() {
        List<ClassLocator> filters = new ArrayList<ClassLocator>();
        filters.add(new AnnotationLocatorImpl(new AnnotationFinderImpl()));
        filters.add(new DerivedImplementationNameLocator(new ImplSuffixingDeriver()));
        return filters;
    }
}
