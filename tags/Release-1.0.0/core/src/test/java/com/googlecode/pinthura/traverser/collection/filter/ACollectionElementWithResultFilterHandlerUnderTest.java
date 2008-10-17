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
package com.googlecode.pinthura.traverser.collection.filter;

import com.googlecode.pinthura.traverser.CollectionTraverser;
import com.googlecode.pinthura.traverser.collection.CollectionTraverserImpl;
import com.googlecode.pinthura.traverser.collection.PathResolver;
import com.googlecode.pinthura.traverser.collection.CollectionElementWithResultHandler;
import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import static org.junit.Assert.assertThat;
import org.junit.Before;
import org.junit.Test;
import static org.hamcrest.core.IsEqual.equalTo;

import java.util.Arrays;
import java.util.List;

public final class ACollectionElementWithResultFilterHandlerUnderTest {

    private final IMocksControl mockControl = EasyMock.createControl();
    private CollectionElementWithResultFilterHandler filterHandler;
    private CollectionElementFilter mockFilter;
    private PathResolver mockPathResolver;
    private CollectionTraverser traverser;
    private CollectionElementWithResultHandler mockHandler;

    @SuppressWarnings({ "unchecked" })
    @Before
    public void setup() {
        mockFilter = mockControl.createMock(CollectionElementFilter.class);
        mockPathResolver = mockControl.createMock(PathResolver.class);
        mockHandler = mockControl.createMock(CollectionElementWithResultHandler.class);

        filterHandler = new CollectionElementWithResultFilterHandler(mockFilter, mockHandler);
        traverser = new CollectionTraverserImpl(mockPathResolver);
    }

    @SuppressWarnings({ "unchecked" })
    @Test
    public void shouldFilterElementsBeforeCallingTheHandler() {
        //CHECKSTYLE_OFF
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6);
        //CHECKSTYLE_ON
        expectNoPath(numbers);
        expectFilteredInput();
        expectHandledInput();
        mockControl.replay();

        Integer result = traverser.<Integer, Integer>forEachWithResult(numbers, filterHandler, 0);
        //CHECKSTYLE_OFF
        assertThat(result, equalTo(12));
        //CHECKSTYLE_ON

        mockControl.verify();
    }

    private void expectNoPath(final List<Integer> numbers) {
        for (Integer number : numbers) {
            expectNoPath(number);
        }
    }

    @SuppressWarnings({ "unchecked" })
    private void expectHandledInput() {
        EasyMock.expect(mockHandler.handle(2, 0)).andReturn(2);
        //CHECKSTYLE_OFF
        EasyMock.expect(mockHandler.handle(4, 2)).andReturn(6);
        EasyMock.expect(mockHandler.handle(6, 6)).andReturn(12);
        //CHECKSTYLE_ON
    }

    @SuppressWarnings({ "unchecked" })
    private void expectFilteredInput() {
        EasyMock.expect(mockFilter.filter(1)).andReturn(false);
        EasyMock.expect(mockFilter.filter(2)).andReturn(true);
        //CHECKSTYLE_OFF
        EasyMock.expect(mockFilter.filter(3)).andReturn(false);
        EasyMock.expect(mockFilter.filter(4)).andReturn(true);
        EasyMock.expect(mockFilter.filter(5)).andReturn(false);
        EasyMock.expect(mockFilter.filter(6)).andReturn(true);
        //CHECKSTYLE_ON
    }

    private void expectNoPath(final int input) {
        EasyMock.expect(mockPathResolver.resolvePath(PathResolver.NO_PATH, input)).andReturn(input);
    }
}
