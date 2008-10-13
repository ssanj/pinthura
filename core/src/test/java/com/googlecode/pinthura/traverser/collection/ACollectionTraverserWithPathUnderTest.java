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
import com.googlecode.pinthura.data.Authentication;
import com.googlecode.pinthura.data.Employee;
import com.googlecode.pinthura.data.Authorization;
import com.googlecode.pinthura.traverser.CollectionTraverser;
import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public final class ACollectionTraverserWithPathUnderTest {

    private static final String PATH_1      = "authentication";
    private static final String PATH_2      = "authorization";
    private static final int RESULT_2       = 5;
    private static final boolean RESULT_1   = true;

    private final IMocksControl mockControl = EasyMock.createControl();
    private CollectionTraverser traverser;
    private CollectionElementHandler mockCollectionElementHandler;
    private PathEvaluator mockPathEvaluator;

    @Before
    public void setup() {
        mockCollectionElementHandler = mockControl.createMock(CollectionElementHandler.class);
        mockPathEvaluator = mockControl.createMock(PathEvaluator.class);

        traverser = new CollectionTraverserImpl(mockPathEvaluator);
    }

    @SuppressWarnings({ "unchecked" })
    @Test
    public void shouldCallTheHandlerWithTheResolvedPathForEachListElement() {
        Employee employee1 = new Employee();
        Employee employee2 = new Employee();
        Authentication authentication1 = new Authentication();
        Authentication authentication2 = new Authentication();
        EasyMock.expect(mockPathEvaluator.evaluate(PATH_1, employee1)).andReturn(authentication1);
        EasyMock.expect(mockPathEvaluator.evaluate(PATH_1, employee2)).andReturn(authentication2);
        mockCollectionElementHandler.handle(authentication1);
        mockCollectionElementHandler.handle(authentication2);
        EasyMock.expect(mockCollectionElementHandler.getResult()).andReturn(RESULT_1);
        mockControl.replay();

        List<Employee> employeeList = Arrays.asList(employee1, employee2);
        Boolean result = (Boolean) traverser.forEach(employeeList, PATH_1, mockCollectionElementHandler);
        assertThat(result, equalTo(RESULT_1));

        mockControl.verify();
    }

    @SuppressWarnings({ "unchecked" })
    @Test
    public void shouldCallTheHandlerWithTheResolvedPathForEachListElementWithIndex() {
        Authentication authentication1 = new Authentication();
        Authentication authentication2 = new Authentication();
        Authorization authorization1 = new Authorization();
        Authorization authorization2 = new Authorization();
        CollectionElementWithIndexHandler mockHandler = mockControl.createMock(CollectionElementWithIndexHandler.class);
        EasyMock.expect(mockPathEvaluator.evaluate(PATH_2, authentication1)).andReturn(authorization1);
        EasyMock.expect(mockPathEvaluator.evaluate(PATH_2, authentication2)).andReturn(authorization2);
        mockHandler.handle(authorization1, true, false, 0L);
        mockHandler.handle(authorization2, false, true, 1L);
        EasyMock.expect(mockHandler.getResult()).andReturn(RESULT_2);
        mockControl.replay();

        List<Authentication> authList = Arrays.asList(authentication1, authentication2);
        Integer result = (Integer) traverser.forEach(authList, PATH_2, mockHandler);
        assertThat(result, equalTo(RESULT_2));

        mockControl.verify();
    }
}
