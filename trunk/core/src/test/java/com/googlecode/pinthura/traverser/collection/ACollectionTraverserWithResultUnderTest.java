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

import com.googlecode.pinthura.traverser.CollectionTraverser;
import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

public final class ACollectionTraverserWithResultUnderTest {

    private final IMocksControl mockControl = EasyMock.createControl();
    private CollectionTraverser traverser;
    private PathResolver mockPathResolver;
    private CollectionElementWithPartialResult mockHandler;

    @Before
    public void setup() {
        mockPathResolver = mockControl.createMock(PathResolver.class);
        mockHandler = mockControl.createMock(CollectionElementWithPartialResult.class);
        traverser = new CollectionTraverserImpl(mockPathResolver);
    }

    @SuppressWarnings({ "unchecked" })
    @Test
    public void shouldCallTheHandlerForEachListElement() {
        EasyMock.expect(mockPathResolver.resolvePath(PathResolver.NO_PATH, "this")).andReturn("this");
        EasyMock.expect(mockPathResolver.resolvePath(PathResolver.NO_PATH, "is")).andReturn("is");
        EasyMock.expect(mockPathResolver.resolvePath(PathResolver.NO_PATH, "simple")).andReturn("simple");

        //CHECKSTYLE_OFF
        EasyMock.expect(mockHandler.handle("this", 0)).andReturn(4);
        EasyMock.expect(mockHandler.handle("is", 4)).andReturn(6);
        EasyMock.expect(mockHandler.handle("simple", 6)).andReturn(12);
        //CHECKSTYLE_ON
        mockControl.replay();

        Integer result = traverser.<String, String, Integer>forEachWithResult(Arrays.asList("this", "is", "simple"), mockHandler, 0);
        //CHECKSTYLE_OFF
        assertThat(result, equalTo(12));
        //CHECKSTYLE_ON

        mockControl.verify();
    }
}
