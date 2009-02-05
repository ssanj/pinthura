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

/**
 * Creates an instance of an object given a <code>MethodParam</code>.
 */
public interface InstanceCreator {

    /**
     * Creates an instance of the return type of a <code>MethodParam</code>.
     * @param param The <code>MethodParam</code> describing the return type.
     * @return An instance of the return type of a <code>MethodParam</code>.
     * @throws InstanceCreationException If the to instantiate could not be found.
     */
    Object createInstance(MethodParam param) throws InstanceCreationException;
}
