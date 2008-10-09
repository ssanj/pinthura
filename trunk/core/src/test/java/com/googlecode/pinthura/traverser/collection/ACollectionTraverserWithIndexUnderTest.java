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

    private static final String RESULT_1 = "tada!";

    private final IMocksControl mockControl = EasyMock.createControl();
    private CollectionTraverser traverser;
    private CollectionElementWithIndexHandler mockCollectionElementWithIndexHandler;

    @Before
    public void setup() {
        traverser = new CollectionTraverserImpl();
        mockCollectionElementWithIndexHandler = mockControl.createMock(CollectionElementWithIndexHandler.class);
    }

    @SuppressWarnings({ "unchecked" })
    @Test
    public void shouldCallTheHandlerForEachElementInACollection() {
        //CHECKSTYLE_OFF
        mockCollectionElementWithIndexHandler.handle(5, true,  false, 0L);
        mockCollectionElementWithIndexHandler.handle(4, false, false, 1L);
        mockCollectionElementWithIndexHandler.handle(3, false, false, 2L);
        mockCollectionElementWithIndexHandler.handle(2, false, false, 3L);
        mockCollectionElementWithIndexHandler.handle(1, false, true,  4L);
        EasyMock.expect(mockCollectionElementWithIndexHandler.getResult()).andReturn(RESULT_1);
        mockControl.replay();

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

        mockCollectionElementWithIndexHandler.handle(shape, true,  true, 0L);
        EasyMock.expect(mockCollectionElementWithIndexHandler.getResult()).andReturn(mockSquare);
        mockControl.replay();

        Square result = traverser.<Shape, Square>forEach(Arrays.asList(shape), mockCollectionElementWithIndexHandler);
        assertThat(result, sameInstance(mockSquare));

        mockControl.verify();
    }
}
