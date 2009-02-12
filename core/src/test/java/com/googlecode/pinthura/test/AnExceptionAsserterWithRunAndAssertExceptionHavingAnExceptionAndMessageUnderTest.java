package com.googlecode.pinthura.test;

import com.googlecode.pinthura.annotation.SuppressionReason;
import com.googlecode.pinthura.bean.PathEvaluatorException;
import com.googlecode.pinthura.bean.PropertyFinderException;
import com.googlecode.pinthura.util.RandomDataCreator;
import com.googlecode.pinthura.util.builder.RandomDataCreatorBuilder;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.Test;

@SuppressWarnings({"ThrowableInstanceNeverThrown"})
@SuppressionReason(SuppressionReason.Reason.TEST_VALUE)
public final class AnExceptionAsserterWithRunAndAssertExceptionHavingAnExceptionAndMessageUnderTest {

    private ExceptionAsserter asserter;
    private RandomDataCreator randomDataCreator;
    private ExceptionMessageBuilder exceptionMessageBuilder;

    @Before
    public void setup() {
        asserter = new ExceptionAsserterImpl();
        randomDataCreator = new RandomDataCreatorBuilder().build();
        exceptionMessageBuilder = new ExceptionMessageBuilder();
    }

    @Test
    public void shouldPassWhenExceptionsAndMessageAreValid() {
        final String message = randomDataCreator.createString(10);
        asserter.runAndAssertException(new ExceptionInfoImpl(ArrayIndexOutOfBoundsException.class, message),
                new Exceptional() { @Override public void run() {
                    throw new ArrayIndexOutOfBoundsException(message);
                }
        });
    }

    @Test
    public void shouldFailForAnInvalidException() {
        final String message = randomDataCreator.createString(10);
        try {
            asserter.runAndAssertException(new ExceptionInfoImpl(ArrayIndexOutOfBoundsException.class, message),
                    new Exceptional() { @Override public void run() {
                        throw new PathEvaluatorException(new ArrayIndexOutOfBoundsException(message));
                    }
            });
            fail("Expected AssertionError.");
        } catch (AssertionError e) {
            assertThat(e.getMessage(), equalTo(exceptionMessageBuilder.withExpectedClass(ArrayIndexOutOfBoundsException.class).
                    withReceivedClass(PathEvaluatorException.class).build()));
        }
    }

    @Test
    public void shouldFailForAnInvalidMessage() {
        String message = randomDataCreator.createString(10);
        final String invalidMessage = message + "blah";
        try {
            asserter.runAndAssertException(new ExceptionInfoImpl(NullPointerException.class, message),
                    new Exceptional() { @Override public void run() {
                        throw new NullPointerException(invalidMessage);
                    }
            });
            fail("Expected AssertionError.");
        } catch (AssertionError e) {
             assertThat(e.getMessage(), equalTo(exceptionMessageBuilder.withExpectedObject(message).withReceivedObject(invalidMessage).build()));
        }
    }

    @Test
    public void shouldFailIfAnExceptionIsNotThrown() {
        String message = randomDataCreator.createString(10);
        try {
            asserter.runAndAssertException(new ExceptionInfoImpl(PropertyFinderException.class, message),
                    new Exceptional() { @Override public void run() { /*do nothing.*/ }});
            fail("Expected AssertionError.");
        } catch (AssertionError e) {
            assertThat(e.getMessage(), equalTo("Expected [com.googlecode.pinthura.bean.PropertyFinderException] was not thrown."));
        }
    }
}