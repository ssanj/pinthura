package com.googlecode.pinthura.test;

import com.googlecode.pinthura.bean.PathEvaluatorException;
import com.googlecode.pinthura.bean.PropertyFinderException;
import com.googlecode.pinthura.annotation.SuppressionReason;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.Test;

import java.io.FileNotFoundException;

@SuppressWarnings({"ThrowableInstanceNeverThrown"})
@SuppressionReason(SuppressionReason.Reason.TEST_VALUE)
public final class AnExceptionAsserterWithRunAndAssertExceptionHavingAnExceptionAndNestedExceptionUnderTest {

    private ExceptionAsserter asserter;

    @Before
    public void setup() {
        asserter = new ExceptionAsserterImpl();
    }

    @Test
    public void shouldPassWhenExceptionsAreValid() {
        asserter.runAndAssertException(new ExceptionInfoImpl(PathEvaluatorException.class, new ExceptionInfoImpl(ArrayIndexOutOfBoundsException.class)),
                new Exceptional() { @Override public void run() {
                    throw new PathEvaluatorException(new ArrayIndexOutOfBoundsException());
                }
        });
    }

    @Test
    public void shouldFailForAnInvalidException() {
        try {
            asserter.runAndAssertException(new ExceptionInfoImpl(ArrayIndexOutOfBoundsException.class, new ExceptionInfoImpl(PathEvaluatorException.class)),
                    new Exceptional() { @Override public void run() {
                        throw new PathEvaluatorException(new ArrayIndexOutOfBoundsException());
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
        try {
            asserter.runAndAssertException(new ExceptionInfoImpl(PropertyFinderException.class, new ExceptionInfoImpl(NullPointerException.class)),
                    new Exceptional() { @Override public void run() {
                        throw new PropertyFinderException(new FileNotFoundException());
                    }
            });
            fail("Expected AssertionError.");
        } catch (AssertionError e) {
            assertThat(e.getMessage(), equalTo("\nExpected: <class java.lang.NullPointerException>" +
                    "\n     got: <class java.io.FileNotFoundException>\n"));
        }
    }

    @Test
    public void shouldFailIfAnExceptionIsNotThrown() {
        try {
            asserter.runAndAssertException(new ExceptionInfoImpl(PropertyFinderException.class, new ExceptionInfoImpl(NullPointerException.class)),
                    new Exceptional() { @Override public void run() { /*do nothing.*/ }});
            fail("Expected AssertionError.");
        } catch (AssertionError e) {
            assertThat(e.getMessage(), equalTo("Expected [com.googlecode.pinthura.bean.PropertyFinderException] was not thrown."));
        }
    }
}
