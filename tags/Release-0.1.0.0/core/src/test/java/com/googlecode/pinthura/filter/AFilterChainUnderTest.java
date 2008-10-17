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
package com.googlecode.pinthura.filter;

import static junit.framework.Assert.fail;
import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsSame.sameInstance;
import static org.junit.Assert.assertThat;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collection;

public final class AFilterChainUnderTest {

    private static final String STRING_INPUT = "BlablahbleuBlue";
    private static final String FILTER1 = "Filter1";
    private static final String FILTER2 = "Filter2";

    private final IMocksControl mockControl = EasyMock.createControl();

    private FilterLink<String, Collection<?>> mockLink1;
    private FilterLink<String, Collection<?>> mockLink2;
    private Collection mockCollection;

    @SuppressWarnings({ "unchecked" })
    @Before
    public void setup() {
        mockLink1 = mockControl.createMock(FilterLink.class);
        mockLink2 = mockControl.createMock(FilterLink.class);
        mockCollection = mockControl.createMock(Collection.class);
    }

    @SuppressWarnings({ "unchecked", "ThrowableInstanceNeverThrown" })
    @Test
    public void shouldReturnTheFoundMatch() {
        EasyMock.expect(mockLink1.filter(STRING_INPUT)).andThrow(new MatchNotFoundException());
        EasyMock.expect(mockLink2.filter(STRING_INPUT)).andReturn(mockCollection);
        mockControl.replay();

        FilterLink<String, Collection<?>> chain = createFilterChain();
        Collection result = chain.filter(STRING_INPUT);
        assertThat(result, sameInstance(mockCollection));

        mockControl.verify();
    }

    @SuppressWarnings({ "ThrowableInstanceNeverThrown" })
    @Test
    public void shouldThrowAnExceptionIfAMatchIsNotFound() {
        EasyMock.expect(mockLink1.filter(STRING_INPUT)).andThrow(new MatchNotFoundException());
        EasyMock.expect(mockLink1.getFilterName()).andReturn(FILTER1);
        EasyMock.expect(mockLink2.filter(STRING_INPUT)).andThrow(new MatchNotFoundException());
        EasyMock.expect(mockLink2.getFilterName()).andReturn(FILTER2);
        mockControl.replay();

        expectMatchNotFound();
    }

    @Test
    public void shouldReturnItsName() {
        FilterLink<String, ?> filterChain = createFilterChain();
        assertThat(filterChain.getFilterName(), equalTo("FilterChain"));
    }

    private void expectMatchNotFound() {
        try {
            createFilterChain().filter(STRING_INPUT);
            fail();
        } catch (MatchNotFoundException e) {
            assertThat(e.getMessage(), equalTo("No match found for: " + STRING_INPUT + " with filters: Filter1, Filter2"));
            mockControl.verify();
        }
    }

    @SuppressWarnings({ "unchecked" })
    private FilterLink<String, Collection<?>> createFilterChain() {
        return new FilterChainImpl<String, Collection<?>>(Arrays.asList(mockLink1, mockLink2));
    }
}
