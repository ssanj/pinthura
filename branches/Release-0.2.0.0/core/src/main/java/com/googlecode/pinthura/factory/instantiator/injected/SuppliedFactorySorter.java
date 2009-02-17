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
import com.googlecode.pinthura.factory.instantiator.ClassInstance;

/**
 * Extracts the parameters specified in the <code>MethodParam</code> to <code>ClassInstance</code>s that are put
 * into vacant indexes in the <code>ClassInstance[]</code> supplied.
 */
public interface SuppliedFactorySorter {

    /**
     * Inserts the supplied <code>ClassInstance</code>s at the vacant indexes in the <code>ClassInstance[]</code>.
     *
     * @param param The method.
     * @param classInstances The <code>ClassInstance[]</code>.
     */
    void sort(MethodParam param, ClassInstance[] classInstances);
}
