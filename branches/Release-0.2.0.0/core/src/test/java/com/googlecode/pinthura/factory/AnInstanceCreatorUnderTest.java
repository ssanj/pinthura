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
package com.googlecode.pinthura.factory;

import com.googlecode.pinthura.annotation.SuppressionReason;
import com.googlecode.pinthura.data.SquareImpl;
import com.googlecode.pinthura.data.UrlBoundaryImpl;
import com.googlecode.pinthura.filter.ChainOfResponsibility;
import com.googlecode.pinthura.util.builder.RandomDataChooserBuilder;
import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;
import org.junit.Before;
import org.junit.Test;

public final class AnInstanceCreatorUnderTest {

    private final IMocksControl mockControl = EasyMock.createControl();
    private MethodParam mockMethodParam;
    private InstanceCreator instanceCreator;
    private Object createdInstance;
    private ChainOfResponsibility mockChainOfResponsibility;

    @SuppressWarnings("unchecked")
    @Before
    public void setup() {
        mockMethodParam = mockControl.createMock(MethodParam.class);
        mockChainOfResponsibility = mockControl.createMock(ChainOfResponsibility.class);
        //TODO:Create a class that returns random objects.
        createdInstance = new RandomDataChooserBuilder().build().
                chooseOneOf("String instance!", new SquareImpl(4), new UrlBoundaryImpl(), 20);

        instanceCreator = new InstanceCreatorImpl(mockChainOfResponsibility);
    }

    @SuppressWarnings("unchecked")
    @SuppressionReason(SuppressionReason.Reason.CANT_INFER_GENERICS_ON_MOCKS)
    @Test
    public void shouldCreateAnInstanceGivenAMethodParam() {
        EasyMock.expect(mockChainOfResponsibility.process(mockMethodParam)).andReturn(createdInstance);
        mockControl.replay();

        Object instance = instanceCreator.createInstance(mockMethodParam);
        assertThat(instance, equalTo(createdInstance));

        mockControl.verify();
    }

}
