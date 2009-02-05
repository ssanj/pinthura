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
package com.googlecode.pinthura.factory.instantiator;

import com.googlecode.pinthura.annotation.SuppressionReason;
import com.googlecode.pinthura.boundary.java.lang.reflect.ConstructorBoundary;
import com.googlecode.pinthura.data.Shape;
import com.googlecode.pinthura.data.ShapeFactory;
import com.googlecode.pinthura.data.UrlBoundary;
import com.googlecode.pinthura.factory.MethodParam;
import com.googlecode.pinthura.util.Arrayz;
import com.googlecode.pinthura.util.RandomDataChooser;
import com.googlecode.pinthura.util.builder.RandomDataChooserBuilder;
import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import static org.hamcrest.core.IsSame.sameInstance;
import static org.junit.Assert.assertThat;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public final class AConstructorInstantiatorUnderTest {

    private final IMocksControl mockControl = EasyMock.createControl();
    private ConstructorInstantiator instantiator;
    private MethodParam mockMethodParam;
    private ConstructorBoundary mockConstructorBoundary;
    private Arrayz mockArrayz;
    private RandomDataChooser randomDataChooser;

    @Before
    public void setup() {
        mockMethodParam = mockControl.createMock(MethodParam.class);
        mockConstructorBoundary = mockControl.createMock(ConstructorBoundary.class);
        mockArrayz = mockControl.createMock(Arrayz.class);

        randomDataChooser = new RandomDataChooserBuilder().build();
        instantiator = new ConstructorInstantiatorImpl(mockArrayz);
    }

    @SuppressWarnings("unchecked")
    @SuppressionReason(SuppressionReason.Reason.CANT_INFER_GENERICS)
    @Test
    public void shouldInstantiateAParameteredConstructor() {
        List<Object> randomArguments = randomDataChooser.chooseOneOf(
                Arrays.<Object>asList("testing", "one two three"), 
                Arrays.<Object>asList(1, 2, 3));
        expectInstantiation(randomArguments, randomDataChooser.<Class>chooseOneOf(
                ShapeFactory.class,
                UrlBoundary.class,
                Shape.class));
    }

    @Test
    public void shouldInstantiateAParameterlessConstructor() {
        expectInstantiation(Arrays.<Object>asList(), UrlBoundary.class);
    }

    @SuppressWarnings({ "unchecked" })
    private void expectInstantiation(final List<Object> argsList, final Class<?> type) {
        Object mockTypeInstance = mockControl.createMock(type);
        EasyMock.expect(mockMethodParam.getArguments()).andReturn(argsList);
        Object[] argsArray = argsList.toArray();
        EasyMock.expect(mockArrayz.fromCollection(argsList, Object.class)).andReturn(argsArray);
        EasyMock.expect(mockConstructorBoundary.newInstance(argsArray)).andReturn(mockTypeInstance);
        mockControl.replay();

        Object result = instantiator.instantiate(mockMethodParam, mockConstructorBoundary);
        assertThat(result, sameInstance(mockTypeInstance));

        mockControl.verify();
    }
}
