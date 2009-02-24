package com.googlecode.pinthura.util;

/**
 * Defines utility methods for handling/wrangling <code>String</code>s.
 */
public interface StringWrangler {

    /**
     * Changes the case of all characters in the <code>String</code> supplied. If supplied with uppercase characters,
     * then changes them to lowercase and vice versa. If supplied with the mixture of upper and lowercase characters,
     * each character chase is inverted individually.
     *
     * @param value The <code>String</code> to change.
     * @return The converted value.
     */
    String changeCase(String value);
}
