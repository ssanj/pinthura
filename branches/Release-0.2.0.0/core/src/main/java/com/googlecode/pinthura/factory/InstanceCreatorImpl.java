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

import com.googlecode.pinthura.filter.ChainOfResponsibility;
import com.googlecode.pinthura.filter.MatchNotFoundException;

/**
 * Creates instances using a <code>ChainOfResponsibility</code> of instantiation strategies.
 */
//TODO: move to internal
public final class InstanceCreatorImpl implements InstanceCreator {
    private final ChainOfResponsibility<MethodParam, Object> chain;


    public InstanceCreatorImpl(final ChainOfResponsibility<MethodParam,Object> chain) {
        this.chain = chain;
    }

    public Object createInstance(final MethodParam param) throws InstanceCreationException {
        try {
            return chain.process(param);
        } catch (MatchNotFoundException e) {
            throw new InstanceCreationException("Could not create instance of " + param.getReturnType(), e);
        }
    }
}
