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

import java.lang.reflect.Method;

/**
 * Creates a <code>MethodParam</code> object given a <code>Method</code> and its arguments.
 */
//TODO: move to internal
public interface MethodParamFactory {

    /**
     * Creates a <code>MethodParam</code> object given a <code>Method</code> and its arguments.
     * @param method The <code>Method</code>.
     * @param args The arguments.
     * @return A <code>MethodParam</code> object.
     */
    MethodParam create(Method method, Object[] args);
}
