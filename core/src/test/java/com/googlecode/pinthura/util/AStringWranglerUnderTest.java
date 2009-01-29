package com.googlecode.pinthura.util;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;
import org.junit.Before;
import org.junit.Test;

public final class AStringWranglerUnderTest {

    private StringWrangler wrangler;

    @Before
    public void setup() {
        wrangler = new StringWranglerImpl();
    }

    @Test
    public void shouldChangeTheCaseOfACapitalisedValue() {
        assertThat(wrangler.changeCase("THE LETTER A"), equalTo("the letter a"));
    }

    @Test
    public void shouldChangeTheCaseOfALowerCasedValue() {
        assertThat(wrangler.changeCase("bbbbbbb"), equalTo("BBBBBBB"));
    }

    @Test
    public void shouldChangeTheCaseOfAMixedCasedValue() {
        assertThat(wrangler.changeCase("ThIs VaLuE is VERY UP and down."), equalTo("tHiS vAlUe IS very up AND DOWN."));
    }
}
