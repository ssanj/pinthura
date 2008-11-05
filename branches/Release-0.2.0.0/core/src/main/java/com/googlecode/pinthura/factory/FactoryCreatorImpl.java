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

import com.googlecode.pinthura.factory.builder.DynamicFactoryInvocationHandlerBuilder;
import com.googlecode.pinthura.util.CreationBroker;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

//TODO: Cache factory instances.
public final class FactoryCreatorImpl implements FactoryCreator {

    private final InvocationHandler invocationHandler;

    public FactoryCreatorImpl(final InvocationHandler invocationHandler, final CreationBroker creationBroker) {
        this.invocationHandler = invocationHandler;
        creationBroker.setInstance(FactoryCreator.class, this);
    }

    public FactoryCreatorImpl(final CreationBroker creationBroker) {
        this(new DynamicFactoryInvocationHandlerBuilder(creationBroker).build(), creationBroker);
    }

    public <T> T create(final Class<T> factoryInterface) {
        return createProxy(factoryInterface);
    }

    @SuppressWarnings({ "unchecked" })
    private <T> T createProxy(final Class<T> factoryInterface) {
        return (T) Proxy.newProxyInstance(getClassLoader(), createInterfaces(factoryInterface), invocationHandler);
    }

    private <T> Class[] createInterfaces(final Class<T> factoryInterface) {
        return new Class[] {factoryInterface};
    }

    private ClassLoader getClassLoader() {
        return getClass().getClassLoader();
    }
}
