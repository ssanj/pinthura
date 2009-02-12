package com.googlecode.pinthura.test;

import com.googlecode.pinthura.annotation.SuppressionReason;
import com.googlecode.pinthura.factory.instantiator.injected.ResolvedFactorySorterException;
import com.googlecode.pinthura.filter.MatchNotFoundException;
import com.googlecode.pinthura.util.builder.RandomDataCreatorBuilder;
import com.googlecode.pinthura.util.RandomDataCreator;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.fail;
import static org.junit.Assert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

@SuppressWarnings("ThrowableInstanceNeverThrown")
@SuppressionReason(SuppressionReason.Reason.TEST_VALUE)
public final class AnExceptionAsserterWithRunAndAssertExceptionHavingMultipleNestedExceptionsUnderTest {

    private ExceptionAsserter exceptionAsserter;
    private RandomDataCreator randomDataCreator;

    @Before
    public void setup() {
        exceptionAsserter = new ExceptionAsserterImpl();
        randomDataCreator = new RandomDataCreatorBuilder().build();
    }

    @Test
    public void shouldPassWhenExceptionsAndMessagesAreValid() {
        final String randomMessage1 = randomDataCreator.createString(10);
        final String randomMessage2 = randomDataCreator.createString(14);
        final String randomMessage3 = randomDataCreator.createString(12);
        exceptionAsserter.runAndAssertException(new ExceptionInfoImpl(RuntimeException.class, randomMessage1,
                                                new ExceptionInfoImpl(MatchNotFoundException.class, randomMessage2,
                                                        new ExceptionInfoImpl(ResolvedFactorySorterException.class, randomMessage3))),
               new Exceptional() {
           @Override
           public void run() {
               throw new RuntimeException(randomMessage1,
                       new MatchNotFoundException(randomMessage2,
                               new ResolvedFactorySorterException(randomMessage3)));
           }
       });
    }

    @Test
    public void shouldPassWhenExceptionsAreValid() {
        exceptionAsserter.runAndAssertException(new ExceptionInfoImpl(RuntimeException.class,
                new ExceptionInfoImpl(MatchNotFoundException.class, new ExceptionInfoImpl(IllegalAccessException.class))),
                new Exceptional() { @Override
            public void run() { throw new RuntimeException(new MatchNotFoundException(new IllegalAccessException())); }});
    }
    
    @Test
    public void shouldFailWhenMessagesAreDifferent() {
        final String randomMessage1 = randomDataCreator.createString(20);
        final String randomMessage2 = randomDataCreator.createString(4);
        final String randomMessage3 = randomDataCreator.createString(7);
        String message4 = randomDataCreator.createString(9);
        final String message4Error = message4 +"Error";
        try {
            exceptionAsserter.runAndAssertException(new ExceptionInfoImpl(MatchNotFoundException.class, randomMessage1,
                                                    new ExceptionInfoImpl(ResolvedFactorySorterException.class, randomMessage2,
                                                            new ExceptionInfoImpl(RuntimeException.class, randomMessage3,
                                                                    new ExceptionInfoImpl(IllegalArgumentException.class, message4)))),
                   new Exceptional() {
               @Override
               public void run() {
                   throw new MatchNotFoundException(randomMessage1,
                           new ResolvedFactorySorterException(randomMessage2,
                                   new RuntimeException(randomMessage3,
                                           new IllegalArgumentException(message4Error))));
               }
           });
            fail("Expected AssertionError.");
        } catch (AssertionError ae) {
            assertThat(ae.getMessage(), equalTo("\nExpected: \"" + message4 + "\"\n     got: \"" + message4Error + "\"\n"));
        }
    }

    @Test
    public void shouldFailWhenExpectedExceptionsAreNotThrown() {
        try {
            exceptionAsserter.runAndAssertException(new ExceptionInfoImpl(RuntimeException.class,
                    new ExceptionInfoImpl(MatchNotFoundException.class, new ExceptionInfoImpl(IllegalAccessException.class,
                    new ExceptionInfoImpl(IllegalArgumentException.class, new ExceptionInfoImpl(IndexOutOfBoundsException.class))))),
                    new Exceptional() { @Override
                public void run() { throw new RuntimeException(new MatchNotFoundException(new IllegalAccessException())); }});
            fail("Expected AssertionError.");
        } catch (AssertionError ae) {
            assertThat(ae.getMessage(),
                    equalTo("Exception is null. Expected java.lang.IllegalArgumentException.\nExpected: not null\n     got: null\n"));
        }
    }
    
    @Test
    public void shouldFailWhenExceptionTypesAreIncorrect() {
        try {
            exceptionAsserter.runAndAssertException(new ExceptionInfoImpl(RuntimeException.class,
                    new ExceptionInfoImpl(MatchNotFoundException.class, new ExceptionInfoImpl(IllegalArgumentException.class,
                    new ExceptionInfoImpl(IndexOutOfBoundsException.class)))),
                    new Exceptional() { @Override
                public void run() {
                        throw new RuntimeException(
                                new MatchNotFoundException(
                                        new IllegalArgumentException(
                                                new NullPointerException()
                                        )));
                    }});
            fail("Expected AssertionError.");
        } catch (AssertionError ae) {
            assertThat(ae.getMessage(), equalTo("\nExpected: <class java.lang.IndexOutOfBoundsException>" +
                    "\n     got: <class java.lang.NullPointerException>\n"));            
        }
    }
}
