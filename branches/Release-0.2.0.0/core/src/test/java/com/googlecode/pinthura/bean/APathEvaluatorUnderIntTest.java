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

import com.googlecode.pinthura.annotation.SuppressionReason;
import com.googlecode.pinthura.boundary.java.lang.ClassBoundaryFactoryImpl;
import com.googlecode.pinthura.data.Access;
import com.googlecode.pinthura.data.Employee;
import com.googlecode.pinthura.util.LetterWranglerImpl;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;
import org.junit.Before;
import org.junit.Test;

public final class APathEvaluatorUnderIntTest {

    private PathEvaluator pathEvaluator;

    @Before
    public void setup() {
        pathEvaluator = new PathEvaluatorImpl(new PropertyFinderImpl(), new ClassBoundaryFactoryImpl());
    }

    @Test
    public void shouldEvaluateASinglePathElement() {
        expectEvaluation("package.name", Class.class, "java.lang");
    }

    @Test
    public void shouldEvaluateAMultiPathElementWithNonStandardGetter() {
        expectEvaluation("toString.length", 1000, 4);
    }

    @Test
    public void shouldEvaluateAMultiParthElement() {
        expectEvaluation("lowerCaseLetters.size", new LetterWranglerImpl(), 26);
    }

    @Test
    public void shouldEvaluateAnotherMultiPathElement() {
        expectEvaluation("authentication.authorization.access.code", new Employee(), Access.Code.PARTIAL);
    }

    @SuppressWarnings("unchecked")
    @SuppressionReason(SuppressionReason.Reason.CANT_INFER_GENERICS)
    private <I, R> void expectEvaluation(final String path, final I instance, final R expectedValue) {
        R result = (R) pathEvaluator.evaluate(path, instance);
        assertThat(result, equalTo(expectedValue));
    }
}
