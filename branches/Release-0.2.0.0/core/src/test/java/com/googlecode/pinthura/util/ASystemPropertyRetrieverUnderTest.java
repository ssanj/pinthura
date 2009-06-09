/*
 * Copyright 2008 Sanjiv Sahayam
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.googlecode.pinthura.util;

import com.googlecode.pinthura.boundary.java.lang.MathBoundaryImpl;
import static org.hamcrest.core.IsEqual.equalTo;
import org.junit.After;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.Test;

public final class ASystemPropertyRetrieverUnderTest {

    private static final String LINE_SEPARATOR = "line.separator";
    private static final String FILE_SEPARATOR = "file.separator";

    private SystemPropertyRetriever propertyRetriever;
    private RandomDataCreator randomDataCreator;
    private String defaultLineSeparator;
    private String defaultFileSeparator;

    @Before
    public void setup() {
        propertyRetriever = new SystemPropertyRetrieverImpl();
        randomDataCreator = new RandomDataCreatorImpl(new MathBoundaryImpl(), new LetterWranglerImpl());

        defaultLineSeparator = System.getProperty(LINE_SEPARATOR);
        defaultFileSeparator = System.getProperty(FILE_SEPARATOR);

        System.out.println("defaultLineSeparator=" + defaultLineSeparator);
        System.out.println("defaultFileSeparator=" + defaultFileSeparator);
    }

    @After
    public void teardown() {
        System.setProperty(LINE_SEPARATOR, defaultLineSeparator);
        System.setProperty(FILE_SEPARATOR, defaultFileSeparator);
    }

    @Test
    public void shouldRetrieveAFileSeparatorProperty() {
        String fileSeparator = setRandomProperty(FILE_SEPARATOR);
        assertThat(propertyRetriever.getFileSeparator(), equalTo(fileSeparator));
    }

    @Test
    public void shouldRetrieveAPathSeparator() {
        String lineSeparator = setRandomProperty(LINE_SEPARATOR);
        assertThat(propertyRetriever.getLineSeparator(), equalTo(lineSeparator));
    }

    @Test
    public void shouldRetrieveAPropertyThatHasBeenSet() {
        String randomValue = randomDataCreator.createString(10);
        System.setProperty("atlantis.location", randomValue);
        assertThat(randomValue, equalTo(propertyRetriever.getProperty("atlantis.location")));
    }

    @Test
    public void shouldThrowAnExceptionIfAPropertyIsNotFound() {
        assertPropertyNotFound("my.property");
    }

    private void assertPropertyNotFound(final String propertyName) {
        try {
            propertyRetriever.getProperty(propertyName);
            fail("Expected PropertyRetrieverException.");
        } catch (PropertyRetrieverException e) {
            assertThat(e.getMessage(), equalTo("System property " +
                                                    propertyName + " has not been set. Set it with -D" + propertyName + "=value."));
        }
    }

    private String setRandomProperty(final String property) {
        String randomValue = randomDataCreator.createString(1);
        System.setProperty(property, randomValue);
        return randomValue;
    }
}
