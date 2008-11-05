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
package com.googlecode.pinthura.factory.builder;

import com.googlecode.pinthura.factory.instantiator.InstantiationStrategy;
import com.googlecode.pinthura.factory.instantiator.builder.AnnotationInstantiatorBuilder;
import com.googlecode.pinthura.factory.instantiator.builder.SimpleInstantiatorBuilder;
import com.googlecode.pinthura.util.CreationBroker;

import java.util.ArrayList;
import java.util.List;

public final class InstanceCreatorFilterBuilder {

    private boolean annotationInstantiator;
    private boolean simpleInstantiator;
    private boolean dynamicFactoryInstantiator;
    private CreationBroker creationBroker;

    public InstanceCreatorFilterBuilder havingAnnotationInstantiator() {
        annotationInstantiator = true;
        return this;
    }

    public InstanceCreatorFilterBuilder havingSimpleInstantiator() {
        simpleInstantiator = true;
        return this;
    }

    public InstanceCreatorFilterBuilder havingDynamicaFactoryInstantiator(final CreationBroker creationBroker) {
        this.creationBroker = creationBroker;
        dynamicFactoryInstantiator = true;
        return this;
    }

    public List<InstantiationStrategy> build() {
        List<InstantiationStrategy> strategyList = new ArrayList<InstantiationStrategy>();

        if (annotationInstantiator) {
            strategyList.add(new AnnotationInstantiatorBuilder().build());
        }

        if (simpleInstantiator) {
            strategyList.add(new SimpleInstantiatorBuilder().build());
        }

        if (dynamicFactoryInstantiator) {
            strategyList.add(new DynamicFactoryInstantiatorBuilder(creationBroker).build());
        }

        return strategyList;
    }
}
