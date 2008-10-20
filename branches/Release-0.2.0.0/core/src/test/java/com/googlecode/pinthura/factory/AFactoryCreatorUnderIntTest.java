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

import com.googlecode.pinthura.data.Shape;
import com.googlecode.pinthura.data.ShapeFactory;
import com.googlecode.pinthura.data.Square;
import com.googlecode.pinthura.data.SquareImpl;
import com.googlecode.pinthura.data.WobblyCircle;
import com.googlecode.pinthura.filter.FilterChainImpl;
import com.googlecode.pinthura.filter.FilterLink;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;
import org.junit.Before;
import org.junit.Test;

public final class AFactoryCreatorUnderIntTest {

    private static final int SIDE_OF_SQUARE         = 4;
    private static final int LENGHT_OF_SQUARE       = 16;
    private static final int RADIUS_OF_CIRCLE       = 2;
    private static final int PERIMETER_OF_CIRCLE    = 12;

    private FactoryCreator creator;

    @Before
    public void setup() {
        FilterLink<MethodParam, Object> filterChain = new FilterChainImpl<MethodParam, Object>(null);
        creator = new FactoryCreatorImpl(
                new DynamicFactoryInvocationHandler(new InstanceCreatorImpl(filterChain),
                new MethodParamFactoryImpl()));
    }

    @Test
    public void shouldCreateInstancesOfTheRequestedInterface() {
        ShapeFactory shapeFactory = creator.create(ShapeFactory.class);

        Square square = shapeFactory.createSquare(SIDE_OF_SQUARE);
        assertThat(SquareImpl.class.isAssignableFrom(square.getClass()), equalTo(true));
        assertThat(square.getLength(), equalTo(LENGHT_OF_SQUARE));

        Shape circle = shapeFactory.createCircle(RADIUS_OF_CIRCLE);
        assertThat(WobblyCircle.class.isAssignableFrom(circle.getClass()), equalTo(true));
        assertThat(circle.getLength(), equalTo(PERIMETER_OF_CIRCLE));
    }
}
