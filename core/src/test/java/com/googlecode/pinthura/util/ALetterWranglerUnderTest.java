package com.googlecode.pinthura.util;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public final class ALetterWranglerUnderTest {

    private LetterWrangler wrangler;

    @Before
    public void setup() {
        wrangler = new LetterWranglerImpl();
    }

    @Test
    public void shouldReturnTheUpperCaseASCIIIndex() {
        assertThat(wrangler.getUpperCaseASCIIIndex(), equalTo(65));
    }

    @Test
    public void shouldReturnTheLowerCaseASCIIIndex() {
        assertThat(wrangler.getLowerCaseASCIIIndex(), equalTo(97));
    }

    @Test
    public void shouldReturnTheNumberOfUpperCaseLetters() {
        assertThat(wrangler.getNumberOfUpperCaseLetters(), equalTo(26));
    }

    @Test
    public void shouldReturnTheNumberOfLowerCaseLetters() {
        assertThat(wrangler.getNumberOfLowerCaseLetters(), equalTo(26));
    }

    @Test
    public void shouldReturnTheNumberOfLettersInTotal() {
        assertThat(wrangler.getNumberLettersInTotal(), equalTo(52));
    }

    @Test
    public void shouldReturnAllUpperCaseLetters() {
        List<Character> characterList = wrangler.getUpperCaseLetters();
        assertThat(characterList.size(), equalTo(26));

        for (int index = 65; index < 91; index++) {
            assertThat(characterList.get(index - 65), equalTo((char) index));
        }
    }

    @Test
    public void shouldReturnAllLowerCaseLetters() {
        List<Character> characterList = wrangler.getLowerCaseLetters();
        assertThat(characterList.size(), equalTo(26));

        //lowercase letters
        for (int index = 97; index < 123; index++) {
            assertThat(characterList.get(index - 97), equalTo((char) index));
        }
    }

    @Test
    public void shouldReturnAllLetters() {
        List<Character> characters = wrangler.getAllLetters();

        assertThat(characters.size(), equalTo(52));
        //uppercase letters
        for (int index = 65; index < 91; index++) {
            assertThat(characters.get(index - 65), equalTo((char) index));
        }

        //lowercase letters
        for (int index = 97; index < 123; index++) {
            assertThat(characters.get(index - 71), equalTo((char) index));
        }
    }
}
