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

public final class Deux<ONE, TWO> {
        private final ONE one;
        private final TWO two;

        public Deux(final ONE one, final TWO two) {
            this.one = one;
            this.two = two;
        }

        public ONE one() {
            return one;
        }

        public TWO two() {
            return two;
        }

    @SuppressWarnings({"CastToConcreteClass", "RedundantIfStatement"})
    @SuppressionReason(SuppressionReason.Reason.GENERATED_CODE)
    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Deux deux = (Deux) o;

        if (!one.equals(deux.one)) return false;
        if (!two.equals(deux.two)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = one.hashCode();
        result = 31 * result + two.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Deux{" +
                "one=" + one +
                ", two=" + two +
                '}';
    }
}
