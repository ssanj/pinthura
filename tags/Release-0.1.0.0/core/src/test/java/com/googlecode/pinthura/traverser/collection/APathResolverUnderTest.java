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

import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import static org.hamcrest.core.IsEqual.equalTo;
import org.hamcrest.core.IsSame;
import static org.junit.Assert.assertThat;
import org.junit.Before;
import org.junit.Test;
import com.googlecode.pinthura.bean.PathEvaluator;
import com.googlecode.pinthura.data.Employee;

public final class APathResolverUnderTest {

    private static final String PATH    = "package.name";
    private static final String RESULT  = "java.lang";

    private final IMocksControl mockControl = EasyMock.createControl();

    private PathResolver pathResolver;
    private PathEvaluator mockPathEvaluator;

    @Before
    public void setup() {
        mockPathEvaluator = mockControl.createMock(PathEvaluator.class);
        pathResolver = new PathResolverImpl(mockPathEvaluator);
    }

    @Test
    public void shouldResolveAValidPath() {
        EasyMock.expect(mockPathEvaluator.evaluate(PATH, String.class)).andReturn(RESULT);
        mockControl.replay();

        String packageName = pathResolver.resolvePath(PATH, String.class);
        assertThat(packageName, equalTo(RESULT));

        mockControl.verify();
    }

    @Test
    public void shouldReturnTheSameInstanceIfNoPathIsSpecified() {
        Employee employee = new Employee();
        mockControl.replay();

        Employee result = pathResolver.resolvePath(PathResolver.NO_PATH, employee);
        assertThat(result, IsSame.sameInstance(employee));

        mockControl.verify();
    }
}
