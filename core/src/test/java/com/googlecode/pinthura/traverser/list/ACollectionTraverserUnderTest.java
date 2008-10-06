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
package com.googlecode.pinthura.traverser.list;

import com.googlecode.pinthura.traverser.CollectionTraverser;
import com.googlecode.pinthura.traverser.collection.CollectionElementHandler;
import com.googlecode.pinthura.traverser.collection.CollectionTraverserImpl;
import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

//Checkstyle has been turned off for "Magic Numbers".
public final class ACollectionTraverserUnderTest {

    private static final String RESULT1 = "tada!";
    private static final float RESULT_2 = 3.0F;

    private final IMocksControl mockControl = EasyMock.createControl();
    private CollectionTraverser traverser;
    private CollectionElementHandler mockCollectionElementHandler;

    @Before
    public void setup() {
        traverser = new CollectionTraverserImpl();
        mockCollectionElementHandler = mockControl.createMock(CollectionElementHandler.class);
    }

    @SuppressWarnings({ "unchecked" })
    @Test
    public void shouldCallTheHandlerForEachElementInTheCollection() {
        //CHECKSTYLE_OFF
        mockCollectionElementHandler.handle(5, true,  false, 0L);
        mockCollectionElementHandler.handle(4, false, false, 1L);
        mockCollectionElementHandler.handle(3, false, false, 2L);
        mockCollectionElementHandler.handle(2, false, false, 3L);
        mockCollectionElementHandler.handle(1, false, true,  4L);
        EasyMock.expect(mockCollectionElementHandler.getResult()).andReturn(RESULT1);
        mockControl.replay();

        List<Integer> integerList = Arrays.asList(5, 4, 3, 2, 1);
        //CHECKSTYLE_ON
        String result = traverser.<Integer, String>forEach(integerList, mockCollectionElementHandler);
        assertThat(result, equalTo(RESULT1));

        mockControl.verify();
    }

    @SuppressWarnings({ "unchecked" })
    @Test
    public void shouldCallTheHandlerForEachElementInAnotherCollection() {
        mockCollectionElementHandler.handle("A", true,  false, 0L);
        mockCollectionElementHandler.handle("B", false, false, 1L);
        mockCollectionElementHandler.handle("C", false, true,  2L);
        EasyMock.expect(mockCollectionElementHandler.getResult()).andReturn(RESULT_2);
        mockControl.replay();

        Set<String> stringSet = new TreeSet<String>();
        stringSet.add("A");
        stringSet.add("B");
        stringSet.add("C");

        Float result = traverser.<String, Float>forEach(stringSet, mockCollectionElementHandler);
        assertThat(result, equalTo(RESULT_2));

        mockControl.verify();
    }
}
