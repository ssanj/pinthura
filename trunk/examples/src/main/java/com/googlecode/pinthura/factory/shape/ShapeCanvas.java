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

public final class ShapeCanvas {

    private final SquareFactory squareFactory;
    private int canvasArea;

    public ShapeCanvas(final int canvasArea, final SquareFactory squareFactory) {
        this.canvasArea = canvasArea;
        this.squareFactory = squareFactory;
    }

    public int calcSquaresOnCanvas(int size) {
        Square square = squareFactory.create(size);
        return canvasArea / square.getArea();
    }
}
