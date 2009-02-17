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
package com.googlecode.pinthura.factory.instantiator.builder;

import com.googlecode.pinthura.factory.instantiator.ConstructorInstantiator;
import com.googlecode.pinthura.factory.instantiator.ConstructorInstantiatorImpl;
import com.googlecode.pinthura.factory.instantiator.ConstructorLocator;
import com.googlecode.pinthura.factory.instantiator.ConstructorLocatorImpl;
import com.googlecode.pinthura.factory.instantiator.SimpleInstantiator;
import com.googlecode.pinthura.factory.locator.deriver.ImplSuffixingDeriver;
import com.googlecode.pinthura.util.Arrayz;
import com.googlecode.pinthura.util.ArrayzImpl;

public final class SimpleInstantiatorBuilder {

    private Arrayz arrayz;
    private ConstructorLocator constructorLocator;
    private ConstructorInstantiator constructorInstantiator;
    private ImplSuffixingDeriver implSuffixingDeriver;

    public SimpleInstantiatorBuilder() {
        arrayz = new ArrayzImpl();
        constructorLocator = new ConstructorLocatorImpl(arrayz);
        constructorInstantiator = new ConstructorInstantiatorImpl(arrayz);
        implSuffixingDeriver = new ImplSuffixingDeriver();
    }

    public SimpleInstantiatorBuilder withArrayz(final Arrayz arrayz) {
        this.arrayz = arrayz;
        return this;
    }

    public SimpleInstantiatorBuilder withConstructorLocator(final ConstructorLocator constructorLocator) {
        this.constructorLocator = constructorLocator;
        return this;
    }

    public SimpleInstantiatorBuilder withConstructorInstantiator(final ConstructorInstantiator constructorInstantiator) {
        this.constructorInstantiator = constructorInstantiator;
        return this;
    }

    public SimpleInstantiatorBuilder withImplSuffixingDeriver(final ImplSuffixingDeriver implSuffixingDeriver) {
        this.implSuffixingDeriver = implSuffixingDeriver;
        return this;
    }

    public SimpleInstantiator build() {
        return new SimpleInstantiator(implSuffixingDeriver, constructorLocator, constructorInstantiator);
    }
}
