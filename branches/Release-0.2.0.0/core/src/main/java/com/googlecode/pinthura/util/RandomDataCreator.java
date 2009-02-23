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
 * Creates random data of various types.
 */
public interface RandomDataCreator {

    /**
     * Creates a simple alphabetic <code>String</code> composed of random upper and lowercase characters.
     * @param length The length of the <code>String</code>.
     * @return A String of the specified length composed of only alphabetic characters.
     */
    String createString(int length);

    /**
     * Creates a random file name of the specified length plus an extension.
     * @param length The length of the filename minus the extension.
     * @return A random file name of the specified length.
     */
    String createFileName(int length);

    /**
     * Creates a number between 0 and value (exclusive).
     * @param value The value below which numbers should be generated. For positive numbers the value generated will have a maximum value of
     * (value - 1) and for negative numbers it will have a maximum value of (value + 1).
     * @return a number between 0 and value (exclusive).
     *
     * -value <---- 0 ----> value
     */
    int createNumber(int value);

    /**
     * Creates a number between the min (inclusive) and the upper boundary (exclusive).
     * @param min The lowest number possible.
     * @param upperBoundary The upper boundary for the created number. (exclusive).
     * @return A number between the lower boundary (inclusive) and the upper boundary (exclusive).
     */
    int createBoundedNumber(int min, int upperBoundary);

    /**
     * Returns a random element of an enumeration.
     * @param typeClass The enumeration class of T.
     * @param <T> The Type of enumeration.
     * @return A random element of an enumeration of type T.
     */
    <T extends Enum> T createType(Class<T> typeClass);
}
