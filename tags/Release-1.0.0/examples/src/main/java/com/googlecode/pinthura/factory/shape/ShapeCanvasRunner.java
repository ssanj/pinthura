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
package com.googlecode.pinthura.factory.shape;

import com.googlecode.pinthura.factory.FactoryCreator;
import com.googlecode.pinthura.factory.FactoryCreatorImpl;
import com.googlecode.pinthura.factory.InvocationFactoryImpl;

public final class ShapeCanvasRunner {

    private static final int CANVAS_AREA = 40;

    private ShapeCanvasRunner() {
        //Main class.
    }

    public static void main(final String[] args) {
        FactoryCreator factoryCreator = new FactoryCreatorImpl(new InvocationFactoryImpl());
        SquareFactory squareFactory = factoryCreator.create(SquareFactory.class);

        ShapeCanvas canvas = new ShapeCanvas(CANVAS_AREA, squareFactory);
        System.out.println("Number of squares: " + canvas.calcSquaresOnCanvas(2));
    }
}
