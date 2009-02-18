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
package com.googlecode.pinthura.injection;

import com.googlecode.pinthura.boundary.java.lang.ClassBoundary;
import org.easymock.IMocksControl;

/**
 * Wraps EasyMock methods.
 *
 * @see <a href="http://www.easymock.org">EasyMock</a>
 */
public interface EasyMockWrapper {

    /**
     * Creates a mock control which can create mocks of interfaces.
     * @return A mock control.
     */
    IMocksControl createControl();

    /**
     * Creates a mock of the supplied class using the mock control supplied.
     * 
     * @param mockControl The mock control to use when creating mocks.
     * @param clazz The interface to mock.
     * @param <T> The type of interface.
     * @return A mock of the supplied interface.
     */
    <T> T createMock(IMocksControl mockControl, ClassBoundary<T> clazz);
}
