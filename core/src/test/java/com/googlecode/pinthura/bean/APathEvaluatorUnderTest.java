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
package com.googlecode.pinthura.bean;

import com.googlecode.pinthura.data.Access;
import com.googlecode.pinthura.data.Authentication;
import com.googlecode.pinthura.data.Authorization;
import com.googlecode.pinthura.data.Employee;
import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;
import org.junit.Before;
import org.junit.Test;

public final class APathEvaluatorUnderTest {

    private final IMocksControl mockControl = EasyMock.createControl();

    private PathEvaluator pathEvaluator;
    private PropertyFinder mockPropertyFinder;

    @Before
    public void setup() {
        mockPropertyFinder = mockControl.createMock(PropertyFinder.class);
        pathEvaluator = new PathEvaluatorImpl(mockPropertyFinder);
    }

    @Test
    public void shouldEvaluateASinglePathElement() throws NoSuchMethodException {
        expectProperty("package", Class.class, "getPackage");
        mockControl.replay();

        Package result = pathEvaluator.evaluate("package", Class.class);
        assertThat(result, equalTo(Class.class.getPackage()));

        mockControl.verify();
    }

    @Test
    public void shouldEvaluateAMultiPathElement() throws NoSuchMethodException {
        expectProperty("package", Class.class, "getPackage");
        expectProperty("name", Package.class, "getName");
        mockControl.replay();

        String result = pathEvaluator.evaluate("package.name", Class.class);
        assertThat(result, equalTo("java.lang"));

        mockControl.verify();
    }

    @Test
    public void shouldEvaluateAnotherMultiPathElement() throws NoSuchMethodException {
        expectProperty("authentication", Employee.class, "getAuthentication");
        expectProperty("authorization", Authentication.class, "getAuthorization");
        expectProperty("access", Authorization.class, "getAccess");
        expectProperty("code", Access.class, "getCode");
        mockControl.replay();

        Access.Code result = pathEvaluator.evaluate("authentication.authorization.access.code", new Employee());
        assertThat(result, equalTo(Access.Code.PARTIAL));

        mockControl.verify();
    }

    private void expectProperty(final String property, final Class<?> parentClass, final String methodName) throws NoSuchMethodException {
        EasyMock.expect(mockPropertyFinder.findMethodFor(property, parentClass)).
                andReturn(parentClass.getMethod(methodName));
    }
}
