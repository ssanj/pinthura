This example demonstrates the use of the `CollectionElementWithResult` interface to sum a list of numbers.

```
package com.googlecode.pinthura.traverser.collection;

public final class TotalHandlerWithResult implements CollectionElementWithResult<Integer, Integer> {

    public Integer handle(final Integer element, final Integer prevResult) {
        return prevResult + element;
    }
}
```

This can be run as a [JUnit](http://www.junit.org) test:

```
public final class ACollectionTraverserUnderIntTest {

     private static final int SUM_TOTAL = 15;

    private CollectionTraverser traverser;

    @Before
    public void setup() {
        traverser = new CollectionTraverserImpl(new PathResolverImpl(new PathEvaluatorImpl(new PropertyFinderImpl())));
    }

    ...

    @Test
    public void shouldSumAListOfNumbersWithResult() {
        Collection<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);
        Integer result = traverser.forEachWithResult(numbers, new TotalHandlerWithResult(), 0);
        assertThat(result, equalTo(15));
     }
}
```