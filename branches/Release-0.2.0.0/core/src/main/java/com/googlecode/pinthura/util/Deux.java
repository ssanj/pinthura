package com.googlecode.pinthura.util;

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
