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

public interface MethodParam {

    Class<?> getReturnType();
    Object[] getArguments();
    Method getMethod();

    public final class Impl implements MethodParam {

        private final Class<?> returnType;
        private final Object[] arguments;
        private final Method method;

        public Impl(final Method method, final Object[] arguments) {
            this.returnType = method.getReturnType();
            this.arguments = arguments;
            this.method = method;
        }

        public Class<?> getReturnType() {
            return returnType;
        }

        public Object[] getArguments() {
            return arguments;
        }

        public Method getMethod() {
            return method;
        }
    }
}
