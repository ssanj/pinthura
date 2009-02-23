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
package com.googlecode.pinthura.injection.filter;

import com.googlecode.pinthura.boundary.java.lang.reflect.FieldBoundary;
import com.googlecode.pinthura.injection.filters.MockPrefixFilter;
import com.googlecode.pinthura.util.ItemFilter;
import com.googlecode.pinthura.util.RandomDataCreator;
import com.googlecode.pinthura.util.StringWrangler;
import com.googlecode.pinthura.util.StringWranglerImpl;
import com.googlecode.pinthura.util.builder.RandomDataCreatorBuilder;
import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;
import org.junit.Before;
import org.junit.Test;

public final class AMockPrefixFilterUnderTest {

    private IMocksControl mockControl;
    private ItemFilter<FieldBoundary> filter;
    private RandomDataCreator random;
    private String randomPrefix;
    private StringWrangler strWrangler;

    @Before
    public void setup() {
        mockControl = EasyMock.createControl();
        random = new RandomDataCreatorBuilder().build();
        strWrangler = new StringWranglerImpl();
        randomPrefix = random.createString(5);
        filter = new MockPrefixFilter(randomPrefix);
    }

    @Test
    public void shouldFilterNamesThatDontStartWithThePrefix() {
        expectInclude(random.createString(3) + randomPrefix, false);
    }

    @Test
    public void shouldFilterNamesThatHaveThePrefixInTheWrongCase() {
        expectInclude(strWrangler.changeCase(randomPrefix) + random.createString(10), false);
    }

    @Test
    public void shouldFilterNamesThatAreEmpty() {
        expectInclude("", false);
    }

    @Test
    public void shouldIncludeNamesThatStartWithAPrefix() {
        expectInclude(randomPrefix + random.createString(10), true);
    }

    private void expectInclude(final String fieldName, final boolean expectedResult) {
        FieldBoundary mockFieldBoundary = mockControl.createMock(FieldBoundary.class);
        EasyMock.expect(mockFieldBoundary.getName()).andReturn(fieldName);
        mockControl.replay();

        boolean result = filter.include(mockFieldBoundary);
        assertThat("[" + fieldName +"] should not match prefix [" + randomPrefix + "]", result, equalTo(expectedResult));

        mockControl.verify();
    }
}
