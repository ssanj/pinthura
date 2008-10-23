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
package com.googlecode.pinthura.factory.instantiator;

import com.googlecode.pinthura.factory.boundary.ConstructorBoundary;
import com.googlecode.pinthura.factory.MethodParam;

public final class ConstructorInstantiatorImpl implements ConstructorInstantiator {

    public <T> Object instantiate(final MethodParam methodParam, final ConstructorBoundary<T> constructorBoundary) {
        return constructorBoundary.newInstance(methodParam.getArguments());
    }

    public <T> Object instantiate(final InjectedFactoryValues factoryParamValues, final ConstructorBoundary<T> constructor) {
        return null;
    }

}
