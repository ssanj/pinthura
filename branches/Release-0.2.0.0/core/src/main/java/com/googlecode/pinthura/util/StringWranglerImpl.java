package com.googlecode.pinthura.util;

public final class StringWranglerImpl implements StringWrangler {

    @Override
    public String changeCase(final String value) {
        char[] chars = value.toCharArray();
        for (int index = 0; index < chars.length; index++) {
            chars[index] = (Character.isLowerCase(chars[index]) ?
                    Character.toUpperCase(chars[index]) : Character.toLowerCase(chars[index]));
        }

        return new String(chars);        
    }
}
