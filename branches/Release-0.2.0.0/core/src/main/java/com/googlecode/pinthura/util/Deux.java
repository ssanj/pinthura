package com.googlecode.pinthura.util;

public interface Deux<O,T> {
    O one();
    T two();

    class DeuxImpl<O,T> implements Deux<O,T> {
        private final O one;
        private final T two;

        public DeuxImpl(final O one, final T two) {
            this.one = one;
            this.two = two;
        }

        public O one() {
            return one;
        }

        public T two() {
            return two;
        }
    }
}
