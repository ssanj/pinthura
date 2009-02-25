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
import com.googlecode.pinthura.boundary.java.lang.ClassBoundary;
import com.googlecode.pinthura.boundary.java.lang.ClassBoundaryFactory;
import com.googlecode.pinthura.boundary.java.lang.reflect.MethodBoundary;
import com.googlecode.pinthura.data.Authentication;
import com.googlecode.pinthura.data.Employee;
import com.googlecode.pinthura.data.UrlBoundaryImpl;
import com.googlecode.pinthura.test.ExceptionAsserter;
import com.googlecode.pinthura.test.ExceptionAsserterImpl;
import com.googlecode.pinthura.test.ExceptionInfoImpl;
import com.googlecode.pinthura.test.Exceptional;
import com.googlecode.pinthura.util.RandomDataChooser;
import com.googlecode.pinthura.util.RandomDataCreator;
import com.googlecode.pinthura.util.builder.RandomDataChooserBuilder;
import com.googlecode.pinthura.util.builder.RandomDataCreatorBuilder;
import org.easymock.EasyMock;
import org.easymock.IMocksControl;

public final class APathEvaluatorWithExceptionsIncubator {

    private final IMocksControl mockControl;
    private final PathEvaluator pathEvaluator;
    private final PropertyFinder mockPropertyFinder;
    private final ExceptionAsserter asserter;
    private final ClassBoundaryFactory mockClassBoundaryFactory;
    private final String navigablePath;
    private final String invalidPath;
    private final String randomExceptionMessage;
    private final Object randomObject;
    private final Object randomInvalidObject;
    private final RuntimeException randomException;
    private ClassBoundary mockInvalidClassBoundary;
    private String fullPath;

    public APathEvaluatorWithExceptionsIncubator() {
        mockControl = EasyMock.createControl();
        mockClassBoundaryFactory = mockControl.createMock(ClassBoundaryFactory.class);
        mockPropertyFinder = mockControl.createMock(PropertyFinder.class);
        RandomDataCreator randomDataCreator = new RandomDataCreatorBuilder().build();
        randomExceptionMessage = randomDataCreator.createString(10);
        navigablePath = randomDataCreator.createString(5);
        invalidPath = randomDataCreator.createString(7);

        RandomDataChooser randomDataChooser = new RandomDataChooserBuilder().build();
        randomObject = randomDataChooser.chooseOneOf(new Employee(), new Authentication(), new UrlBoundaryImpl());
        randomInvalidObject = randomDataChooser.chooseOneOf(new Employee(), new Authentication(), new UrlBoundaryImpl());
        randomException = randomDataChooser.chooseOneOf(new NullPointerException(randomExceptionMessage),
                                                        new IllegalArgumentException(randomExceptionMessage),
                                                        new IllegalStateException(randomExceptionMessage));

        pathEvaluator = new PathEvaluatorImpl(mockPropertyFinder, mockClassBoundaryFactory);
        asserter = new ExceptionAsserterImpl();
    }

    @SuppressWarnings("unchecked")
    @SuppressionReason(SuppressionReason.Reason.CANT_INFER_GENERICS_ON_MOCKS)
    public APathEvaluatorWithExceptionsIncubator supplyAValidPath() {
        ClassBoundary mockValidClassBoundary = mockControl.createMock(ClassBoundary.class);
        EasyMock.expect(mockClassBoundaryFactory.create(randomObject.getClass())).andReturn(mockValidClassBoundary);
        MethodBoundary mockValidMethodBoundary = mockControl.createMock(MethodBoundary.class);
        EasyMock.expect(mockPropertyFinder.findMethodFor(navigablePath, mockValidClassBoundary)).andReturn(mockValidMethodBoundary);
        EasyMock.expect(mockValidMethodBoundary.invoke(randomObject)).andReturn(randomInvalidObject);
        return this;
    }

    @SuppressWarnings({"unchecked", "ThrowableInstanceNeverThrown"})
    @SuppressionReason({SuppressionReason.Reason.CANT_INFER_GENERICS_ON_MOCKS, SuppressionReason.Reason.TEST_VALUE})
    public APathEvaluatorWithExceptionsIncubator supplyAnInvalidPath() {
        mockInvalidClassBoundary = mockControl.createMock(ClassBoundary.class);
        EasyMock.expect(mockClassBoundaryFactory.create(randomInvalidObject.getClass())).andReturn(mockInvalidClassBoundary);
        EasyMock.expect(mockPropertyFinder.findMethodFor(invalidPath, mockInvalidClassBoundary)).
                andThrow(new PropertyFinderException(randomExceptionMessage));
        return this;
    }

    public APathEvaluatorWithExceptionsIncubator performEvaluate() {
        fullPath = navigablePath + "." + invalidPath;
        mockControl.replay();
        return this;
    }

    public APathEvaluatorWithExceptionsIncubator propertyFinderException() {
        asserter.runAndAssertException(new ExceptionInfoImpl(PathEvaluatorException.class,
                new ExceptionInfoImpl(PropertyFinderException.class, randomExceptionMessage)),
                new Exceptional() {public void run() { pathEvaluator.evaluate(fullPath, randomObject); }});
        return this;
    }

    @SuppressWarnings("ThrowableInstanceNeverThrown")
    @SuppressionReason(SuppressionReason.Reason.TEST_VALUE)
    public APathEvaluatorWithExceptionsIncubator supplyAnExceptionalCondition() {
        mockInvalidClassBoundary = mockControl.createMock(ClassBoundary.class);
        EasyMock.expect(mockClassBoundaryFactory.create(randomObject.getClass())).andThrow(randomException);
        return this;
    }

    public APathEvaluatorWithExceptionsIncubator randomException() {
        asserter.runAndAssertException(new ExceptionInfoImpl(PathEvaluatorException.class,
                new ExceptionInfoImpl(randomException.getClass(), randomExceptionMessage)),
                new Exceptional() {public void run() { pathEvaluator.evaluate(fullPath, randomObject); }});
        return this;
    }

    public APathEvaluatorWithExceptionsIncubator isThrown() {
        return this;
    }

    public APathEvaluatorWithExceptionsIncubator observe() {
        return this;
    }

    public APathEvaluatorWithExceptionsIncubator thatA() {
        return this;
    }

    public APathEvaluatorWithExceptionsIncubator expected() {
        return this;
    }

    public APathEvaluatorWithExceptionsIncubator nested() {
        return this;
    }

    public APathEvaluatorWithExceptionsIncubator thatThe() {
        return this;
    }

    public APathEvaluatorWithExceptionsIncubator done() {
        mockControl.verify();
        return this;
    }
}
