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
 * Creates a dynamic instance of a factory inteface.
 */
public interface FactoryCreator {

    /**
     * Creates a dynamic instance of the supplied factory interface.
     *
     * @param factoryInterface The factory interface.
     * @param <T> The type of the factory interface and the created instance.
     * @return A dynamic instance of the supplied factory interface.
     */
    <T> T create(Class<T> factoryInterface);
}
