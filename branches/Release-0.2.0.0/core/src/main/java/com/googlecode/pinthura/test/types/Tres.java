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
package com.googlecode.pinthura.test.types;

import com.googlecode.pinthura.annotation.SuppressionReason;

/**
 * Models an abstract class that holds three parameters.
 * 
 * @param <ONE> The type of the first parameters.
 * @param <TWO> The type of the second parameters.
 * @param <THREE> The type of the third parameters.                             
 */
public final class Tres<ONE, TWO, THREE> {

    private final ONE one;
    private final TWO two;
    private final THREE three;

    public Tres(final ONE one, final TWO two, final THREE three) {
        this.one = one;
        this.two = two;
        this.three = three;
    }

    public ONE one() {
        return one;
    }

    public TWO two() {
        return two;
    }

    public THREE three() {
        return three;
    }

    @SuppressWarnings({"CastToConcreteClass", "RedundantIfStatement"})
    @SuppressionReason(SuppressionReason.Reason.GENERATED_CODE)
    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Tres tres = (Tres) o;

        if (!one.equals(tres.one)) return false;
        if (!three.equals(tres.three)) return false;
        if (!two.equals(tres.two)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = one.hashCode();
        result = 31 * result + two.hashCode();
        result = 31 * result + three.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Tres{" +
                "one=" + one +
                ", two=" + two +
                ", three=" + three +
                '}';
    }
}
