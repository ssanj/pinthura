package com.googlecode.pinthura.util;

import java.util.ArrayList;
import java.util.List;

public final class LetterWranglerImpl implements LetterWrangler {

    private final List<Character> allLetters;
    private final List<Character> lowerCaseLetters;
    private final List<Character> upperCaseLetters;

    public LetterWranglerImpl() {
        upperCaseLetters = createUpperCaseCharacters();
        lowerCaseLetters = createLowerCaseCharacters();
        allLetters = new ArrayList<Character>();
        allLetters.addAll(upperCaseLetters);
        allLetters.addAll(lowerCaseLetters);
    }

    @Override
    public List<Character> getAllLetters() {
        return allLetters;
    }

    private List<Character> createLowerCaseCharacters() {
        List<Character> characters = new ArrayList<Character>();

        for (int index = 0; index  < getNumberOfLowerCaseLetters() ; index++) {
            characters.add((char) (getStartOfLowerCaseASCIIIndex() + index));
        }

        return characters;
    }

    private List<Character> createUpperCaseCharacters() {
        List<Character> characters = new ArrayList<Character>();

        for (int index = 0; index  < getNumberOfUpperCaseLetters(); index++) {
            characters.add((char) (getStartOfUpperCaseASCIIIndex() + index));
        }

        return characters;
    }

    @Override
    public int getStartOfUpperCaseASCIIIndex() {
        return 65;
    }

    @Override
    public int getStartOfLowerCaseASCIIIndex() {
        return 97;
    }

    @Override
    public int getNumberOfUpperCaseLetters() {
        return 26;
    }

    @Override
    public int getNumberOfLowerCaseLetters() {
        return 26;
    }

    @Override
    public int getNumberLettersInTotal() {
        return 52;
    }

    @Override
    public List<Character> getLowerCaseLetters() {
        return lowerCaseLetters;
    }

    @Override
    public List<Character> getUpperCaseLetters() {
        return upperCaseLetters;
    }
}
