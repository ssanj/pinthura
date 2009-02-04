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

import java.io.FileNotFoundException;

@SuppressWarnings({"ThrowableInstanceNeverThrown"})
@SuppressionReason(SuppressionReason.Reason.TEST_VALUE)
public final class AnExceptionAsserterWithRunAndAssertExceptionHavingAnExceptionNestedExceptionAndMessageUnderTest {

    private ExceptionAsserter asserter;
    private RandomDataCreator randomDataCreator;

    @Before
    public void setup() {
        asserter = new ExceptionAsserterImpl();
        randomDataCreator = new RandomDataCreatorBuilder().build();
    }

    @Test
    public void shouldPassWhenExceptionsAndMessageAreValid() {
        final String message = randomDataCreator.createString(10);
        asserter.runAndAssertException(PathEvaluatorException.class, ArrayIndexOutOfBoundsException.class, message,
                new Exceptional() { @Override public void run() {
                    throw new PathEvaluatorException(new ArrayIndexOutOfBoundsException(message));
                }
        });
    }

    @Test
    public void shouldFailForAnInvalidException() {
        final String message = randomDataCreator.createString(10);
        try {
            asserter.runAndAssertException(ArrayIndexOutOfBoundsException.class, PathEvaluatorException.class, message,
                    new Exceptional() { @Override public void run() {
                        throw new PathEvaluatorException(new ArrayIndexOutOfBoundsException(message));
                    }
            });
            fail("Expected AssertionError.");
        } catch (AssertionError e) {
            assertThat(e.getMessage(), equalTo("\nExpected: <class java.lang.ArrayIndexOutOfBoundsException>" +
                    "\n     got: <class com.googlecode.pinthura.bean.PathEvaluatorException>\n"));

        }
    }
    
    @Test
    public void shouldFailForAnInvalidNestedException() {
        final String message = randomDataCreator.createString(10);
        try {
            asserter.runAndAssertException(PropertyFinderException.class, NullPointerException.class, message,
                    new Exceptional() { @Override public void run() {
                        throw new PropertyFinderException(new FileNotFoundException(message));
                    }
            });
            fail("Expected AssertionError.");
        } catch (AssertionError e) {
            assertThat(e.getMessage(), equalTo("\nExpected: <class java.lang.NullPointerException>" +
                    "\n     got: <class java.io.FileNotFoundException>\n"));            
        }
    }

    @Test
    public void shouldFailForAnInvalidMessage() {
        String message = randomDataCreator.createString(10);
        final String invalidMessage = message + "blah";
        try {
            asserter.runAndAssertException(PropertyFinderException.class, NullPointerException.class, message,
                    new Exceptional() { @Override public void run() {
                        throw new PropertyFinderException(new NullPointerException(invalidMessage));
                    }
            });
            fail("Expected AssertionError.");
        } catch (AssertionError e) {
             assertThat(e.getMessage(), equalTo("\nExpected: \"" + message + "\"\n     got: \"" + invalidMessage + "\"\n"));
        }
    }
    
    @Test
    public void shouldFailIfAnExceptionIsNotThrown() {
        String message = randomDataCreator.createString(10);
        try {
            asserter.runAndAssertException(PropertyFinderException.class, NullPointerException.class, message,
                    new Exceptional() { @Override public void run() { /*do nothing.*/ }});
            fail("Expected AssertionError.");
        } catch (AssertionError e) {
            assertThat(e.getMessage(), equalTo("Expected [com.googlecode.pinthura.bean.PropertyFinderException] was not thrown."));
        }
    }   
}
