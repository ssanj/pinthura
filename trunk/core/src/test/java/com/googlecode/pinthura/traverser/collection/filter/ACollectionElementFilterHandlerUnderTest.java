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
import com.googlecode.pinthura.traverser.collection.CollectionElementHandler;
import com.googlecode.pinthura.traverser.collection.CollectionTraverserImpl;
import com.googlecode.pinthura.traverser.collection.PathResolver;
import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

public final class ACollectionElementFilterHandlerUnderTest {

    private static final String PATH = "length";

    private final IMocksControl mockControl = EasyMock.createControl();
    private CollectionElementHandler filterHandler;
    private CollectionElementFilter mockFilter;
    private PathResolver mockPathResolver;
    private CollectionTraverser traverser;
    private CollectionElementHandler mockHandler;

    @SuppressWarnings({ "unchecked" })
    @Before
    public void setup() {
        mockFilter = mockControl.createMock(CollectionElementFilter.class);
        mockPathResolver = mockControl.createMock(PathResolver.class);
        mockHandler = mockControl.createMock(CollectionElementHandler.class);

        filterHandler = new CollectionElementFilterHandlerImpl(mockFilter, mockHandler);
        traverser = new CollectionTraverserImpl(mockPathResolver);
    }

    @SuppressWarnings({ "unchecked" })
    @Test
    public void shouldFilterElementsBeforeCallingAHandler() {
        //CHECKSTYLE_OFF
        EasyMock.expect(mockPathResolver.resolvePath(PATH, "eight")).andReturn(5);
        EasyMock.expect(mockPathResolver.resolvePath(PATH, "four")).andReturn(4);
        EasyMock.expect(mockPathResolver.resolvePath(PATH, "sixteen")).andReturn(7);

        EasyMock.expect(mockFilter.filter(5)).andReturn(false);
        EasyMock.expect(mockFilter.filter(4)).andReturn(true);
        EasyMock.expect(mockFilter.filter(7)).andReturn(false);

        mockHandler.handle(4);
        //CHECKSTYLE_ON
        EasyMock.expect(mockHandler.getResult()).andReturn(1);
        mockControl.replay();

        Integer result = traverser.<String, Integer, Integer>forEach(Arrays.asList("eight", "four", "sixteen"), PATH, filterHandler);
        assertThat(result, equalTo(1));

        mockControl.verify();
    }
}
