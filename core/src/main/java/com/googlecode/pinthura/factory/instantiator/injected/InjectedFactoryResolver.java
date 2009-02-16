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
package com.googlecode.pinthura.factory.instantiator.injected;

import com.googlecode.pinthura.factory.MethodParam;

/**
 * Derives a <code>Constructor</code> from the annotations of <code>MethodParam</code>.
 */
public interface InjectedFactoryResolver {

    /**
     * Derives constructor parameters from the supplied <code>MethodParam</code>.
     * @param methodParam The annotated method.
     * @return An <code>InjectedFactoryValues</code> object encapsulating <code>Constructor</code> parameters.
     */
    ConstructorParam resolve(final MethodParam methodParam);
}
