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

import com.googlecode.pinthura.data.Authentication;
import com.googlecode.pinthura.data.Employee;
import com.googlecode.pinthura.traverser.CollectionTraverser;
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

    private static final float RESULT_1     = 3.0F;
    private static final float RESULT_2     = 6F;
    private static final Boolean RESULT_3   = true;
    private static final String PATH_1      = "";

    private final IMocksControl mockControl = EasyMock.createControl();

    private CollectionTraverser traverser;
    private CollectionElementHandler mockCollectionElementHandler;
    private PathResolver mockPathResolver;

    @Before
    public void setup() {
        mockCollectionElementHandler = mockControl.createMock(CollectionElementHandler.class);
        mockPathResolver = mockControl.createMock(PathResolver.class);

        traverser = new CollectionTraverserImpl(mockPathResolver);
    }

    @SuppressWarnings({ "unchecked" })
    @Test
    public void shouldCallTheHandlerForEachElementInASet() {
        expectPathResolution();
        expectHandler();

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

        EasyMock.expect(mockPathResolver.resolvePath(PathResolver.NO_PATH, 1)).andReturn(1);
        EasyMock.expect(mockPathResolver.resolvePath(PathResolver.NO_PATH, 2)).andReturn(2);
        //CHECKSTYLE_OFF
        EasyMock.expect(mockPathResolver.resolvePath(PathResolver.NO_PATH, 3)).andReturn(3);
        //CHECKSTYLE_ON

        EasyMock.expect(mockCollectionElementHandler.getResult()).andReturn(RESULT_2);
        mockControl.replay();

        //CHECKSTYLE_OFF
        final Float result = traverser.<Integer, Float>forEach(Arrays.asList(1, 2, 3), mockCollectionElementHandler);
        //CHECKSTYLE_ON
        assertThat(result, equalTo(RESULT_2));

        mockControl.verify();
    }

    @SuppressWarnings({ "unchecked" })
    @Test
    public void shouldCallTheHandlerWithTheResolvedPathForEachListElement() {
        Employee employee1 = new Employee();
        Employee employee2 = new Employee();
        Authentication authentication1 = new Authentication();
        Authentication authentication2 = new Authentication();

        EasyMock.expect(mockPathResolver.resolvePath(PATH_1, employee1)).andReturn(authentication1);
        EasyMock.expect(mockPathResolver.resolvePath(PATH_1, employee2)).andReturn(authentication2);
        mockCollectionElementHandler.handle(authentication1);
        mockCollectionElementHandler.handle(authentication2);

        EasyMock.expect(mockCollectionElementHandler.getResult()).andReturn(RESULT_3);
        mockControl.replay();

        List<Employee> employeeList = Arrays.asList(employee1, employee2);
        Boolean result = (Boolean) traverser.forEach(employeeList, PATH_1, mockCollectionElementHandler);
        assertThat(result, equalTo(RESULT_3));

        mockControl.verify();
    }

    @SuppressWarnings({ "unchecked" })
    private void expectHandler() {
        mockCollectionElementHandler.handle("A");
        mockCollectionElementHandler.handle("B");
        mockCollectionElementHandler.handle("C");
    }

    private void expectPathResolution() {
        EasyMock.expect(mockPathResolver.resolvePath(PathResolver.NO_PATH, "A")).andReturn("A");
        EasyMock.expect(mockPathResolver.resolvePath(PathResolver.NO_PATH, "B")).andReturn("B");
        EasyMock.expect(mockPathResolver.resolvePath(PathResolver.NO_PATH, "C")).andReturn("C");
    }

}
