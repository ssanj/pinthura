package com.googlecode.pinthura.util;

public interface Deux<O,T> {
    O getOne();
    T getTwo();

    class DeuxImpl<O,T> implements Deux<O,T> {
        private final O one;
        private final T two;

        public DeuxImpl(final O one, final T two) {
            this.one = one;
            this.two = two;
        }

        public O getOne() {
            return one;
        }

        public T getTwo() {
            return two;
        }
    }
}
