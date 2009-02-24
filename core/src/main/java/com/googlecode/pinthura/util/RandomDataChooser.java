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
package com.googlecode.pinthura.util;

/**
 * Randomly chooses one for the supplied values.
 */
public interface RandomDataChooser {

    /**
     * Randomly chooses one of the supplied values.
     * 
     * @param values The values to choose from.
     * @param <T> The type of values.
     * @return One of the values returned randomly.
     */
    <T> T chooseOneOf(T... values);
}
