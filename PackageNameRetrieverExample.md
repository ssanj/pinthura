This example demonstrates a simple `CollectionElementHandler` that returns a list of packages when supplied with a list of classes.

```
package com.googlecode.pinthura.traverser.collection;

import java.util.ArrayList;
import java.util.List;

public final class PackageNameRetreiver implements CollectionElementHandler<String, List<String>> {

    private final List<String> packageNames = new ArrayList<String>();

    public void handle(final String element) {
        packageNames.add(element);
    }

    public List<String> getResult() {
        return new ArrayList<String>(packageNames);
    }
}
```

This can be run as a [JUnit](http://www.junit.org) test:

```
public final class ACollectionTraverserUnderIntTest {

    private static final int NO_OF_PACKAGES = 4;

    private CollectionTraverser traverser;

    @Before
    public void setup() {
        traverser = new CollectionTraverserImpl(new PathEvaluatorImpl(new PropertyFinderImpl()));
    }

    ...
 
    @Test
    public void shouldDisplayAListOfPackageNames() {
        List<Class<?>> classes = Arrays.<Class<?>>asList(UrlBoundary.class, Connection.class, String.class, Arrays.class);

        List<String> result = traverser.forEach(classes, "package.name", new PackageNameRetreiver());

        assertThat(result.size(), equalTo(NO_OF_PACKAGES));
        assertThat(result.get(0), equalTo("com.googlecode.pinthura.data"));
        assertThat(result.get(1), equalTo("java.sql"));
        assertThat(result.get(2), equalTo("java.lang"));
        assertThat(result.get(3), equalTo("java.util"));
    }
}
```