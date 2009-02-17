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

import com.googlecode.pinthura.factory.InstanceCreator;
import com.googlecode.pinthura.factory.InstanceCreatorImpl;
import com.googlecode.pinthura.factory.MethodParam;
import com.googlecode.pinthura.processer.ChainOfResponsibility;
import com.googlecode.pinthura.processer.ProcesserChain;
import com.googlecode.pinthura.util.CreationBroker;
import com.googlecode.pinthura.util.CreationBrokerImpl;

public final class InstanceCreatorBuilder {

    private CreationBroker creationBroker;

    public InstanceCreatorBuilder() {
        creationBroker = new CreationBrokerImpl();
    }

    public InstanceCreatorBuilder withCreationBroker(final CreationBroker creationBroker) {
        this.creationBroker = creationBroker;
        return this;
    }

    //TODO: The order of these method calls should be important. Currently the end-result is the same no matter the order.
    public InstanceCreator build() {
        ChainOfResponsibility<MethodParam, Object> chain =
                new ProcesserChain<MethodParam, Object>(
                    new InstanceCreatorFilterBuilder().
                        havingAnnotationInstantiator().
                        havingSimpleInstantiator().
                        havingDynamicaFactoryInstantiator(creationBroker).
                        build());
        return new InstanceCreatorImpl(chain);
    }
}
