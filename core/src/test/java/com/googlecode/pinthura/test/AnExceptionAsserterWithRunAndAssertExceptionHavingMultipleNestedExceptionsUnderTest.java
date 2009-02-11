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
        final String message1 = randomDataCreator.createString(10);
        final String message2 = randomDataCreator.createString(14);
        final String message3 = randomDataCreator.createString(12);
        exceptionAsserter.runAndAssertException(new ExceptionInfoImpl(RuntimeException.class, message1,
                                                new ExceptionInfoImpl(MatchNotFoundException.class, message2,
                                                        new ExceptionInfoImpl(ResolvedFactorySorterException.class, message3))),
               new Exceptional() {
           @Override
           public void run() {
               throw new RuntimeException(message1,
                       new MatchNotFoundException(message2,
                               new ResolvedFactorySorterException(message3))); 
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
        final String message1 = randomDataCreator.createString(20);
        final String message2 = randomDataCreator.createString(4);
        final String message3 = randomDataCreator.createString(7);
        String message4 = randomDataCreator.createString(9);
        final String message4Error = message4 +"Error";
        try {
            exceptionAsserter.runAndAssertException(new ExceptionInfoImpl(MatchNotFoundException.class, message1,
                                                    new ExceptionInfoImpl(ResolvedFactorySorterException.class, message2,
                                                            new ExceptionInfoImpl(RuntimeException.class, message3,
                                                                    new ExceptionInfoImpl(IllegalArgumentException.class, message4)))),
                   new Exceptional() {
               @Override
               public void run() {
                   throw new MatchNotFoundException(message1,
                           new ResolvedFactorySorterException(message2,
                                   new RuntimeException(message3,
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
}
