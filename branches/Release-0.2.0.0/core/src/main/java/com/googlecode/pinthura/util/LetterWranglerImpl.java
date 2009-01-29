package com.googlecode.pinthura.util;

import java.util.ArrayList;
import java.util.List;

public final class LetterWranglerImpl implements LetterWrangler {

    private List<Character> characters;

    public LetterWranglerImpl() {
        characters = new ArrayList<Character>();
        addUpperCaseCharacters();
        addLowerCaseCharacters();        
    }

    @Override
    public List<Character> getAllLetters() {
        return characters;
    }

    private void addLowerCaseCharacters() {
        for (int index = 0; index  < getNumberOfLowerCaseLetters() ; index++) {
            characters.add((char) (getLowerCaseASCIIIndex() + index));
        }
    }

    private void addUpperCaseCharacters() {
        for (int index = 0; index  < getNumberOfUpperCaseLetters(); index++) {
            characters.add((char) (getUpperCaseASCIIIndex() + index));
        }
    }

    @Override
    public int getUpperCaseASCIIIndex() {
        return 65;
    }

    @Override
    public int getLowerCaseASCIIIndex() {
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
}
