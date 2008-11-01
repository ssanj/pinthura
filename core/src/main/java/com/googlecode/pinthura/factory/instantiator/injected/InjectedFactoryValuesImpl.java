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

import com.googlecode.pinthura.factory.boundary.ClassBoundary;
import com.googlecode.pinthura.factory.instantiator.ClassInstance;
import static com.googlecode.pinthura.util.Arrayz.createArray;

import java.util.ArrayList;
import java.util.List;

//TODO: Test
public final class InjectedFactoryValuesImpl implements InjectedFactoryValues {

    private final ClassInstance[] classInstances;

    public InjectedFactoryValuesImpl(final ClassInstance[] classInstances) {
        this.classInstances = classInstances;
    }

    public ClassBoundary<?>[] getConstructorTypes() {
        List<ClassBoundary<?>> typeList = new ArrayList<ClassBoundary<?>>();

        for (ClassInstance classInstance : classInstances) {
            typeList.add(classInstance.getClazz());
        }

        return (ClassBoundary<?>[]) createArray(typeList, ClassBoundary.class);
    }

    public Object[] getConstructorArguments() {
        List<Object> instanceList = new ArrayList<Object>();

        for (ClassInstance classInstance : classInstances) {
            instanceList.add(classInstance.getInstance());
        }

        return createArray(instanceList, Object.class);
    }
}
