package com.googlecode.pinthura.util;

import java.util.List;

/**
 * Utility methods for handling/wrangling letters. 
 */
public interface LetterWrangler {

    /**
     * Returns a list of all ASCII letters (upper and lowercase).
     * @return A list of all ASCII letters (upper and lowercase).
     */
    List<Character> getAllLetters();

    /**
     * Returns starting index of the uppercase ASCII letters.
     * @return The starting index of the uppercase ASCII letters.
     */
    int getStartOfUpperCaseASCIIIndex();

    /**
     * Returns starting index of the lowercase ASCII letters.
     * @return The starting index of the lowercase ASCII letters.
     */
    int getStartOfLowerCaseASCIIIndex();

    /**
     * Returns the number of uppercase ASCII letters.
     * @return The number of uppercase ASCII letters.
     */
    int getNumberOfUpperCaseLetters();

    /**
     * Returns the number of lowercase ASCII letters.
     * @return The number of lowercase ASCII letters.
     */
    int getNumberOfLowerCaseLetters();

    /**
     * Returns the total number of ASCII letters. This would be equal to
     * {@link #getNumberOfLowerCaseLetters()} + {@link #getNumberOfUpperCaseLetters()}.
     * 
     * @return The total number of ASCII letters.
     */
    int getNumberLettersInTotal();

    /**
     * Returns all ASCII lowercase characters.
     * @return A <code>List<Character></code> representing all lowercase ASCII characters.
     */
    List<Character> getLowerCaseLetters();

    /**
     * Returns all ASCII uppercase characters.
     * @return A <code>List<Character></code> representing all uppercase ASCII characters.
     */
    List<Character> getUpperCaseLetters();
}
