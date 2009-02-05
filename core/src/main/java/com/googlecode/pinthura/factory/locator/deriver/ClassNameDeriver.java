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
 * Derives a <code>Class</code> name from the return type of a <code>MethodParam</code>.
 */
//TODO: move to internal
public interface ClassNameDeriver {

    /**
     * Derives a <code>Class</code> name from the return type of the supplied <code>MethodParam</code>.
     * @param methodParam The <code>MethodParam</code> containing the return type.
     * @return A <code>Class</code> name derived from the return type of <code>MethodParam</code>.
     */
    String derive(MethodParam methodParam);
}
