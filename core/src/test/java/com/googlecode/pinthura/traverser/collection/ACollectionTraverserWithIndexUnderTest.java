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

import com.googlecode.pinthura.data.Shape;
import com.googlecode.pinthura.data.Square;
import com.googlecode.pinthura.data.Authentication;
import com.googlecode.pinthura.data.Authorization;
import com.googlecode.pinthura.traverser.CollectionTraverser;
import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsSame.sameInstance;
import static org.junit.Assert.assertThat;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public final class ACollectionTraverserWithIndexUnderTest {

    private static final String RESULT_1    = "tada!";
    private static final Integer RESULT_2   = 2;
    private static final String PATH        = "";

    private final IMocksControl mockControl = EasyMock.createControl();
    private CollectionTraverser traverser;
    private CollectionElementWithIndexHandler mockCollectionElementWithIndexHandler;
    private PathResolver mockPathResolver;

    @Before
    public void setup() {
        mockPathResolver = mockControl.createMock(PathResolver.class);
        mockCollectionElementWithIndexHandler = mockControl.createMock(CollectionElementWithIndexHandler.class);
        traverser = new CollectionTraverserImpl(mockPathResolver);
    }

    @SuppressWarnings({ "unchecked" })
    @Test
    public void shouldCallTheHandlerForEachElementInACollection() {
        expectPathResolution();
        expectHandlerCalled();

        EasyMock.expect(mockCollectionElementWithIndexHandler.getResult()).andReturn(RESULT_1);
        mockControl.replay();

        //CHECKSTYLE_OFF
        List<Integer> integerList = Arrays.asList(5, 4, 3, 2, 1);
        //CHECKSTYLE_ON
        String result = traverser.<Integer, String>forEach(integerList, mockCollectionElementWithIndexHandler);
        assertThat(result, equalTo(RESULT_1));

        mockControl.verify();
    }

    @SuppressWarnings({ "unchecked" })
    @Test
    public void shouldCallTheHandlerWhenACollectionHasASingleElement() {
        Shape shape = mockControl.createMock(Shape.class);
        Square mockSquare = mockControl.createMock(Square.class);

        EasyMock.expect(mockPathResolver.resolvePath(PathResolver.NO_PATH, shape)).andReturn(shape);
        mockCollectionElementWithIndexHandler.handle(shape, true,  true, 0L);

        EasyMock.expect(mockCollectionElementWithIndexHandler.getResult()).andReturn(mockSquare);
        mockControl.replay();

        Square result = traverser.<Shape, Square>forEach(Arrays.asList(shape), mockCollectionElementWithIndexHandler);
        assertThat(result, sameInstance(mockSquare));

        mockControl.verify();
    }

    @SuppressWarnings({ "unchecked" })
    @Test
    public void shouldCallTheHandlerWithTheResolvedPathEachListElement() {
        Authentication authentication1 = new Authentication();
        Authentication authentication2 = new Authentication();
        Authorization authorization1 = new Authorization();
        Authorization authorization2 = new Authorization();

        CollectionElementWithIndexHandler mockHandler = mockControl.createMock(CollectionElementWithIndexHandler.class);
        EasyMock.expect(mockPathResolver.resolvePath(PATH, authentication1)).andReturn(authorization1);
        EasyMock.expect(mockPathResolver.resolvePath(PATH, authentication2)).andReturn(authorization2);
        mockHandler.handle(authorization1, true, false, 0L);
        mockHandler.handle(authorization2, false, true, 1L);

        EasyMock.expect(mockHandler.getResult()).andReturn(RESULT_2);
        mockControl.replay();

        Integer result = (Integer) traverser.forEach(Arrays.asList(authentication1, authentication2), PATH, mockHandler);
        assertThat(result, equalTo(RESULT_2));

        mockControl.verify();
    }

    @SuppressWarnings({ "unchecked" })
    private void expectHandlerCalled() {
        //CHECKSTYLE_OFF
        mockCollectionElementWithIndexHandler.handle(5, true,  false, 0L);
        mockCollectionElementWithIndexHandler.handle(4, false, false, 1L);
        mockCollectionElementWithIndexHandler.handle(3, false, false, 2L);
        mockCollectionElementWithIndexHandler.handle(2, false, false, 3L);
        mockCollectionElementWithIndexHandler.handle(1, false, true,  4L);
        //CHECKSTYLE_ON
    }

    private void expectPathResolution() {
        //CHECKSTYLE_OFF
        EasyMock.expect(mockPathResolver.resolvePath(PathResolver.NO_PATH, 5)).andReturn(5);
        EasyMock.expect(mockPathResolver.resolvePath(PathResolver.NO_PATH, 4)).andReturn(4);
        EasyMock.expect(mockPathResolver.resolvePath(PathResolver.NO_PATH, 3)).andReturn(3);
        //CHECKSTYLE_ON
        EasyMock.expect(mockPathResolver.resolvePath(PathResolver.NO_PATH, 2)).andReturn(2);
        EasyMock.expect(mockPathResolver.resolvePath(PathResolver.NO_PATH, 1)).andReturn(1);

    }
}
