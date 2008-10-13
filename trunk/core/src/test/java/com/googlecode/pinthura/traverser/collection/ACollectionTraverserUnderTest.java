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

import com.googlecode.pinthura.bean.PathEvaluator;
import com.googlecode.pinthura.traverser.CollectionTraverser;
import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;

//Checkstyle has been turned off for "Magic Numbers".
public final class ACollectionTraverserUnderTest {

    private static final float RESULT_1     = 3.0F;
    private static final float RESULT_2     = 6F;

    private final IMocksControl mockControl = EasyMock.createControl();
    private CollectionTraverser traverser;
    private CollectionElementHandler mockCollectionElementHandler;

    @Before
    public void setup() {
        mockCollectionElementHandler = mockControl.createMock(CollectionElementHandler.class);
        PathEvaluator mockPathEvaluator = mockControl.createMock(PathEvaluator.class);

        traverser = new CollectionTraverserImpl(mockPathEvaluator);
    }

    @SuppressWarnings({ "unchecked" })
    @Test
    public void shouldCallTheHandlerForEachElementInASet() {
        mockCollectionElementHandler.handle("A");
        mockCollectionElementHandler.handle("B");
        mockCollectionElementHandler.handle("C");
        EasyMock.expect(mockCollectionElementHandler.getResult()).andReturn(RESULT_1);
        mockControl.replay();

        final Set<String> stringSet = new TreeSet<String>(Arrays.asList("A", "B", "C"));

        final Float result = traverser.<String, Float>forEach(stringSet, mockCollectionElementHandler);
        assertThat(result, equalTo(RESULT_1));

        mockControl.verify();
    }

    @SuppressWarnings({ "unchecked" })
    @Test
    public void shouldCallTheHandlerForEachElementInAList() {
        mockCollectionElementHandler.handle(1);
        mockCollectionElementHandler.handle(2);
        //CHECKSTYLE_OFF
        mockCollectionElementHandler.handle(3);
        //CHECKSTYLE_ON
        EasyMock.expect(mockCollectionElementHandler.getResult()).andReturn(RESULT_2);
        mockControl.replay();

        //CHECKSTYLE_OFF
        final Float result = traverser.<Integer, Float>forEach(Arrays.asList(1, 2, 3), mockCollectionElementHandler);
        //CHECKSTYLE_ON
        assertThat(result, equalTo(RESULT_2));

        mockControl.verify();
    }
}
