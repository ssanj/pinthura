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
package com.googlecode.pinthura.util;

import com.googlecode.pinthura.data.Shape;
import com.googlecode.pinthura.data.SquareImpl;
import com.googlecode.pinthura.data.UrlBoundary;
import com.googlecode.pinthura.data.UrlBoundaryImpl;
import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import org.junit.Before;
import org.junit.Test;

public final class ACreationBrokerUnderTest {

    private final IMocksControl mockControl = EasyMock.createControl();
    private CreationBroker creationBroker;

    @Before
    public void setup() {
        creationBroker = new CreatioBrokerImpl();
    }

    @Test
    public void shouldNotifyForACreationType() {
        expectNotification(Shape.class, new SquareImpl(1));
    }

    @Test
    public void shouldNotifyForAnotherCreationType() {
        expectNotification(UrlBoundary.class, new UrlBoundaryImpl());
    }

    @SuppressWarnings({ "unchecked" })
    private void expectNotification(final Class<?> clazz, final Object instance) {
        CreationListener mockCreationListener = mockControl.createMock(CreationListener.class);
        mockCreationListener.instanceCreated(instance);
        mockControl.replay();

        creationBroker.addCreationListener(clazz, mockCreationListener);
        creationBroker.notifyInstanceCreated(instance);

        mockControl.verify();
    }

    //TODO: Add test for when a key is not found.
    //TODO: Add test for multiple instances of different assignable types.

}
