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
package com.googlecode.pinthura.util.builder;

import com.googlecode.pinthura.annotation.SuppressionReason;
import com.googlecode.pinthura.boundary.java.lang.MathBoundary;
import com.googlecode.pinthura.boundary.java.lang.MathBoundaryImpl;
import com.googlecode.pinthura.util.LetterWrangler;
import com.googlecode.pinthura.util.LetterWranglerImpl;
import com.googlecode.pinthura.util.RandomDataCreator;
import com.googlecode.pinthura.util.RandomDataCreatorImpl;

@SuppressWarnings("MethodReturnOfConcreteClass")
@SuppressionReason(SuppressionReason.Reason.BUILDER_PATTERN)
public final class RandomDataCreatorBuilder {

    private MathBoundary mathBoundary;
    private LetterWrangler letterWrangler;

    public RandomDataCreatorBuilder() {
        mathBoundary = new MathBoundaryImpl();
        letterWrangler = new LetterWranglerImpl();
    }

    public RandomDataCreatorBuilder withMathBoundary(final MathBoundary mathBoundary) {
        this.mathBoundary = mathBoundary;
        return this;
    }

    public RandomDataCreatorBuilder withLetterWrangler(final LetterWrangler letterWrangler) {
        this.letterWrangler = letterWrangler;
        return this;
    }

    public RandomDataCreator build() {
        return new RandomDataCreatorImpl(mathBoundary, letterWrangler);
    }
}
