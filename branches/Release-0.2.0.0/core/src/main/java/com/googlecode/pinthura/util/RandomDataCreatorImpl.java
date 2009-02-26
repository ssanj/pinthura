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

import com.googlecode.pinthura.boundary.java.lang.MathBoundary;

import java.util.List;

public final class RandomDataCreatorImpl implements RandomDataCreator {

    private final MathBoundary mathBoundary;
    private final List<Character> characterList;
    private final int numberLettersInTotal;

    public RandomDataCreatorImpl(final MathBoundary mathBoundary, final LetterWrangler letterWrangler) {
        this.mathBoundary = mathBoundary;
        characterList = letterWrangler.getAllLetters();
        numberLettersInTotal = letterWrangler.getNumberLettersInTotal();
    }

    public String createString(final int length) {
        StringBuilder builder = new StringBuilder();

        for (int index = 0; index  < length; index++) {
            builder.append(characterList.get(((int) (mathBoundary.random() * numberLettersInTotal))));
        }

        return builder.toString();
    }

    public String createFileName(final int length) {
        return new StringBuilder().append(createString(length)).append(".").append(createString(3)).toString();
    }

    public int createNumber(final int value) {
        return (int) (mathBoundary.random() * value);
    }

    public int createBoundedNumber(final int min, final int upperBoundary) {
        int range = upperBoundary - min;
        double randomValue = mathBoundary.random() * range;

        if (min < 0) {
            return (int) mathBoundary.floor(min + randomValue);
        }

        return (int) (min + randomValue);
    }

    public <T extends Enum> T createType(final Class<T> typeClass) {
        T[] elements = typeClass.getEnumConstants();
        return elements[((int) (mathBoundary.random() * elements.length))];
    }
}
