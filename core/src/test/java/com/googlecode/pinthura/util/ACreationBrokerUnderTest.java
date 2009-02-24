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

import com.googlecode.pinthura.annotation.SuppressionReason;
import com.googlecode.pinthura.data.Shape;
import com.googlecode.pinthura.data.SquareImpl;
import com.googlecode.pinthura.data.UrlBoundary;
import com.googlecode.pinthura.data.UrlBoundaryImpl;
import com.googlecode.pinthura.reflection.FieldSetter;
import com.googlecode.pinthura.reflection.FieldSetterImpl;
import com.googlecode.pinthura.test.ExceptionAsserter;
import com.googlecode.pinthura.test.ExceptionAsserterImpl;
import com.googlecode.pinthura.test.ExceptionInfoImpl;
import com.googlecode.pinthura.test.Exceptional;
import com.googlecode.pinthura.test.types.Deux;
import com.googlecode.pinthura.util.builder.RandomDataChooserBuilder;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;
import org.junit.Before;
import org.junit.Test;

public final class ACreationBrokerUnderTest {

    private CreationBroker creationBroker;
    private Deux<Class,Object> classObjectDeux;
    private ExceptionAsserter exceptionAsserter;

    @SuppressWarnings("unchecked")
    @SuppressionReason(SuppressionReason.Reason.CANT_CREATE_GENERIC_ARRAY)
    @Before
    public void setup() {
        RandomDataChooser chooser = new RandomDataChooserBuilder().build();
        classObjectDeux = chooser.chooseOneOf(
                            new Deux<Class, Object>(Shape.class, new SquareImpl(1)),
                            new Deux<Class, Object>(FieldSetter.class, new FieldSetterImpl()),
                            new Deux<Class, Object>(UrlBoundary.class, new UrlBoundaryImpl()));
        creationBroker = new CreationBrokerImpl();
        exceptionAsserter = new ExceptionAsserterImpl();
    }

    @Test
    public void shouldStoreAndRetrieveAType() {
        expectInstance();
    }

    @Test
    public void shouldThrowACouldNotFindInstanceForClassExceptionWhenRetrievingAnUnstoredType() {
        exceptionAsserter.runAndAssertException(
                new ExceptionInfoImpl(CouldNotFindInstanceForClassException.class, "Could not find instance for class java.lang.String"),
                new Exceptional() {@Override public void run() { creationBroker.getInstanceFor(String.class); }});
    }

    @SuppressWarnings("unchecked")
    @SuppressionReason(SuppressionReason.Reason.CANT_INFER_GENERICS)
    private  void expectInstance() {
        creationBroker.setInstance(classObjectDeux.one(), classObjectDeux.two());
        assertThat(creationBroker.getInstanceFor(classObjectDeux.one()), equalTo(classObjectDeux.two()));
    }
}
