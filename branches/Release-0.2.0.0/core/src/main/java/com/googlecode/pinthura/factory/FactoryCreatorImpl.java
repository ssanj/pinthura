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

import com.googlecode.pinthura.util.CreationBroker;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

/**
 * Creates a dynamic instance of a supplied factory interface. Also caches these instances so that a second call to
 * create the same factory interface returns the cached instance. Factories should be thread-safe for this reason.
 *
 * A <code>CreatioBroker</code> registers an instance of this class against its <code>FactoryCreator</code> interface.
 * This allows other classes that require access to this instance to retrieve it from the <code>CreatioBroker</code>. 
 */
//TODO: Write some exceptional tests.
public final class FactoryCreatorImpl implements FactoryCreator {

    private final InvocationHandler invocationHandler;
    private final Map<Class<?>, Object> factoryCache;

    public FactoryCreatorImpl(final InvocationHandler invocationHandler, final CreationBroker creationBroker) {
        this.invocationHandler = invocationHandler;
        factoryCache = new HashMap<Class<?>, Object>();
        creationBroker.setInstance(FactoryCreator.class, this);
    }

    public synchronized <T> T create(final Class<T> factoryInterface) {
        if (!factoryCache.containsKey(factoryInterface)) {
            factoryCache.put(factoryInterface, createProxy(factoryInterface));
        }

        return factoryInterface.cast(factoryCache.get(factoryInterface));
    }

    private <T> T createProxy(final Class<T> factoryInterface) {
        return factoryInterface.cast(Proxy.newProxyInstance(getClassLoader(), createInterfaces(factoryInterface), invocationHandler));
    }

    private <T> Class[] createInterfaces(final Class<T> factoryInterface) {
        return new Class[] {factoryInterface};
    }

    private ClassLoader getClassLoader() {
        return getClass().getClassLoader();
    }
}
