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
import com.googlecode.pinthura.factory.boundary.ClassBoundary;
import com.googlecode.pinthura.factory.instantiator.ClassInstance;
import com.googlecode.pinthura.factory.instantiator.ClassInstanceFactory;

/**
 * This class adds the supplied parameters into each of the unused slots in the <code>ClassInstance[]</code> supplied.
 * Eg.
 * Assume [*] is a used slot and px is the parameter followed by its number.
 * If classInstance[] was -> [*][][*][] and 2 parameters p1 and p2 were supplied,
 * adds the parameters to -> [*][p1][*][p2]
 */
public final class SuppliedFactorySorterImpl implements SuppliedFactorySorter {

    private final ClassInstanceFactory classInstanceFactory;

    public SuppliedFactorySorterImpl(final ClassInstanceFactory classInstanceFactory) {
        this.classInstanceFactory = classInstanceFactory;
    }

    public void sort(final MethodParam methodParam, final ClassInstance[] classInstances) {
        ClassBoundary<?>[] methodParamTypes = methodParam.getParameterTypes();
        Object[] methodParamArgs = methodParam.getArguments();
        for (int x = 0, count = 0; x < classInstances.length && count < methodParamTypes.length; x++) {
            if (classInstances[x] == null) {
                classInstances[x] = classInstanceFactory.createClassInstance(methodParamTypes[count], methodParamArgs[count++]);
            }
        }
    }
}
