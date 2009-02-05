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
package com.googlecode.pinthura.factory.locator.deriver;

import com.googlecode.pinthura.factory.MethodParam;

/**
 * Derives a <code>Class</code> name by appending "Impl" to the end of the <code>Class</code> name.
 */
//TODO: move to internal
public final class ImplSuffixingDeriver implements ClassNameDeriver {

    private static final String DEFAULT_SUFFIX  = "Impl";

    public String derive(final MethodParam methodParam) {
        return new StringBuilder().append(methodParam.getReturnType().getName()).append(DEFAULT_SUFFIX).toString();
    }
}
