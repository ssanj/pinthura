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
package com.googlecode.pinthura.traverser.collection;

import com.googlecode.pinthura.bean.PathEvaluatorImpl;
import com.googlecode.pinthura.bean.PropertyFinderImpl;
import com.googlecode.pinthura.traverser.Break;
import com.googlecode.pinthura.traverser.CollectionTraverser;
import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

///Checkstyle has been turned off for 'MagicNumbers'
public final class ACollectionTraverserWithBreaksUnderTest {

    private final IMocksControl mockControl = EasyMock.createControl();

    private CollectionTraverser traverser;
    private List<Integer> numbers;

    @Before
    public void setup() {
        traverser = new CollectionTraverserImpl(new PathEvaluatorImpl(new PropertyFinderImpl()));
        //CHECKSTYLE_OFF
        numbers = Arrays.asList(1, 2, 3, 4);
        //CHECKSTYLE_ON
    }

    @SuppressWarnings({ "unchecked", "ThrowableInstanceNeverThrown" })
    @Test
    public void shouldExitEarlyWhenABreakIsThrown() {
        CollectionElementHandler mockHandler = mockControl.createMock(CollectionElementHandler.class);
        mockHandler.handle(1);
        mockHandler.handle(2);
        EasyMock.expectLastCall().andThrow(new Break());
        EasyMock.expect(mockHandler.getResult()).andReturn(2);
        mockControl.replay();

        final Integer result = traverser.<Integer, Integer>forEach(numbers, mockHandler);
        assertThat(result, equalTo(2));

        mockControl.verify();
    }

    @SuppressWarnings({ "unchecked", "ThrowableInstanceNeverThrown" })
    @Test
    public void shouldExitEarlyWhenPathIsGivenAndABreakIsThrown() {
        CollectionElementHandler mockHandler = mockControl.createMock(CollectionElementHandler.class);
        mockHandler.handle(1);
        EasyMock.expectLastCall().andThrow(new Break());
        EasyMock.expect(mockHandler.getResult()).andReturn(1);
        mockControl.replay();

        final Integer result = traverser.<Integer, Integer, Integer>forEach(numbers, "", mockHandler);
        assertThat(result, equalTo(1));

        mockControl.verify();
    }

    @SuppressWarnings({ "unchecked", "ThrowableInstanceNeverThrown" })
    @Test
    public void shouldExitEarlyWhenABreakIsThrownWithIndex() {
        CollectionElementWithIndexHandler mockHandler = mockControl.createMock(CollectionElementWithIndexHandler.class);
        mockHandler.handle(1, true, false, 0L);
        EasyMock.expectLastCall().andThrow(new Break());
        EasyMock.expect(mockHandler.getResult()).andReturn(1);
        mockControl.replay();


        final Integer result = traverser.<Integer, Integer>forEach(numbers, mockHandler);
        assertThat(result, equalTo(1));

        mockControl.verify();
    }

    @SuppressWarnings({ "unchecked", "ThrowableInstanceNeverThrown" })
    @Test
    public void shouldExitEarlyWhenABreakIsThrownWithPartialResult() {
        CollectionElementWithPartialResult mockHandler = mockControl.createMock(CollectionElementWithPartialResult.class);
        EasyMock.expect(mockHandler.handle(1, 0)).andReturn(1);
        //CHECKSTYLE_OFF
        EasyMock.expect(mockHandler.handle(2, 1)).andReturn(3);
        EasyMock.expect(mockHandler.handle(3, 3)).andThrow(new Break());
        //CHECKSTYLE_ON
        mockControl.replay();

        final Integer result = traverser.<Integer, Integer>forEach(numbers, mockHandler, 0);
        //CHECKSTYLE_OFF
        assertThat(result, equalTo(3));
        //CHECKSTYLE_ON

        mockControl.verify();
    }

}
