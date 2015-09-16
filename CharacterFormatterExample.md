This example demonstrates a simple `CollectionElementWithIndexHandler` that returns a formatted String with the contents of the supplied list.

```
public final class CharacterFormatter implements CollectionElementWithIndexHandler<Character, String> {

    private final StringBuilder builder = new StringBuilder();

    public void handle(final Character element, final boolean first, final boolean last, final Long index) {
        if (first) {
            builder.append("[");
        }

        builder.append(element).append(index);

        if (!last) {
            builder.append(", ");
        }

        if (last) {
            builder.append("]");
        }
    }

    public String getResult() {
        return builder.toString();
    }
}
```

This can be run as a [JUnit](http://www.junit.org) test:

```
public final class ACollectionTraverserUnderIntTest {

    private CollectionTraverser traverser;

    @Before
    public void setup() {
        traverser = new CollectionTraverserImpl(new PathEvaluatorImpl(new PropertyFinderImpl()));
    }

    ...

    @Test
    public void shouldDisplayAListOfFormattedCharacters() {
        Collection<Character> characters = Arrays.asList('A', 'B', 'C', 'D', 'E');

        String result = traverser.forEach(characters, new CharacterFormatter());
        assertThat(result, equalTo("[A0, B1, C2, D3, E4]"));
    }
}
```