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

import com.googlecode.pinthura.factory.locator.ImplementationNotFoundException;
import com.googlecode.pinthura.filter.MatchNotFoundException;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

final class FactoryCreationInvocationHandler implements InvocationHandler {

    private final ClassLocator implLocators;
    private final MethodParamFactory methodParamFactory;
    private final Class<?> factoryInterface;

    public FactoryCreationInvocationHandler(final ClassLocator implLocators,
        final MethodParamFactory methodParamFactory, final Class<?> factoryInterface) {
        this.implLocators = implLocators;
        this.methodParamFactory = methodParamFactory;
        this.factoryInterface = factoryInterface;
    }

    public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {
        try {
            Class<?> clazz = implLocators.filter(methodParamFactory.create(method, args, factoryInterface));
            return instantiateClass(clazz, method.getParameterTypes(), args);
        } catch (MatchNotFoundException e) {
            throw new ImplementationNotFoundException("Could not find implementation for " + method.getReturnType(), e);
        }
    }

    private Object instantiateClass(final Class<?> clazz, final Class[] parameterTypes, final Object... args) throws Exception {
        return clazz.getConstructor(parameterTypes).newInstance(args);
    }
}
