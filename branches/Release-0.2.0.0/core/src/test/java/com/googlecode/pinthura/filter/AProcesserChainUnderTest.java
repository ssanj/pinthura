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

import com.googlecode.pinthura.annotation.SuppressionReason;
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
import java.util.List;

public final class AProcesserChainUnderTest {

    private static final String STRING_INPUT = "BlablahbleuBlue";
    private static final String FILTER1 = "Filter1";
    private static final String FILTER2 = "Filter2";

    private final IMocksControl mockControl = EasyMock.createControl();

    private ProcesserChainlet<String, Collection<?>> mockChainlet1;
    private ProcesserChainlet<String, Collection<?>> mockChainlet2;
    private Collection mockCollection;

    @SuppressWarnings("unchecked")
    @Before
    public void setup() {
        mockChainlet1 = mockControl.createMock(ProcesserChainlet.class);
        mockChainlet2 = mockControl.createMock(ProcesserChainlet.class);
        mockCollection = mockControl.createMock(Collection.class);
    }

    @SuppressWarnings({ "unchecked", "ThrowableInstanceNeverThrown" })
    @Test
    public void shouldReturnTheFoundMatch() {
        EasyMock.expect(mockChainlet1.process(STRING_INPUT)).andThrow(new MatchNotFoundException());
        EasyMock.expect(mockChainlet2.process(STRING_INPUT)).andReturn(mockCollection);
        mockControl.replay();

        ChainOfResponsibility<String, Collection<?>> chain = createFilterChain();
        Collection result = chain.process(STRING_INPUT);
        assertThat(result, sameInstance(mockCollection));

        mockControl.verify();
    }

    @SuppressWarnings({ "ThrowableInstanceNeverThrown" })
    @Test
    public void shouldThrowAnExceptionIfAMatchIsNotFound() {
        EasyMock.expect(mockChainlet1.process(STRING_INPUT)).andThrow(new MatchNotFoundException());
        EasyMock.expect(mockChainlet1.getProcesserName()).andReturn(FILTER1);
        EasyMock.expect(mockChainlet2.process(STRING_INPUT)).andThrow(new MatchNotFoundException());
        EasyMock.expect(mockChainlet2.getProcesserName()).andReturn(FILTER2);
        mockControl.replay();

        expectMatchNotFound();
    }

    @SuppressWarnings("unchecked")
    @Test(expected = UnsupportedOperationException.class)
    public void shouldNotAllowModificationOfSetFilters() {
        mockControl.replay();

        List<ProcesserChainlet<String, Collection<?>>> processerChainletList = Arrays.asList(mockChainlet1, mockChainlet2);
        new ProcesserChain<String, Collection<?>>(processerChainletList);
        processerChainletList.clear();
    }

    private void expectMatchNotFound() {
        try {
            createFilterChain().process(STRING_INPUT);
            fail();
        } catch (MatchNotFoundException e) {
            assertThat(e.getMessage(), equalTo("No match found for: " + STRING_INPUT + " with processers: Filter1, Filter2"));
            mockControl.verify();
        }
    }

    @SuppressWarnings("unchecked")
    @SuppressionReason(SuppressionReason.Reason.CANT_INFER_GENERICS_ON_MOCKS)
    private ChainOfResponsibility<String, Collection<?>> createFilterChain() {
        return new ProcesserChain<String, Collection<?>>(Arrays.asList(mockChainlet1, mockChainlet2));
    }
}
