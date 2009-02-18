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
import org.easymock.EasyMock;
import org.easymock.IMocksControl;

/**
 * Wraps only the interface mocks (and not the class extension mocks) of EasyMock.
 */
public final class EasyMockWrapperImpl implements EasyMockWrapper {

    public IMocksControl createControl() {
        return EasyMock.createControl();
    }

    public <T> T createMock(final IMocksControl mockControl, final ClassBoundary<T> clazz) {
        return mockControl.createMock(clazz.getClazz());
    }
}
