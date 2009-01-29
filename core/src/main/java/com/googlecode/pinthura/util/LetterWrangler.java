package com.googlecode.pinthura.util;

import java.util.List;

public interface LetterWrangler {

    List<Character> getAllLetters();
    int getUpperCaseASCIIIndex();
    int getLowerCaseASCIIIndex();
    int getNumberOfUpperCaseLetters();
    int getNumberOfLowerCaseLetters();
    int getNumberLettersInTotal();
    List<Character> getLowerCaseLetters();
    List<Character> getUpperCaseLetters();
}
