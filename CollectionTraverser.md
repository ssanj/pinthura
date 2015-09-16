# Introduction #

A `CollectionTraverser` abstracts the navigation of a `Collection` and allows the user to work on the elements of that `Collection`.

# A simple example #

_This is a contrived example and you wouldn't normally use the `CollectionTraverser` to write something this simple_

Take a simple function that sums the `Integers` of a supplied `Collection`:

```
   Collection<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);
   Integer result = sum(numbers);
   assertThat(result, equalTo(15));

   ...
 
   public int sum(Collection<Integer> collection) {
      int total = 0;
      for (Integer number : collection) {
         total += number;
      }
      
      return total;
   }   
```

We could rewrite this with a `CollectionTraverser` as:

```
Collection<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);
Integer result = traverser.forEach(numbers, new TotalHandler());
assertThat(result, equalTo(15));
```

If we have a look at `TotalHandler`:

```
public final class TotalHandler implements CollectionElementHandler<Integer, Integer> {

    private int total = 0;

    public void handle(final Integer element) {
        total += element;
    }

    public Integer getResult() {
        return total;
    }
}
```

and `CollectionElementHandler`:

```
public interface CollectionElementHandler<Input, Output> {

    void handle(Input element);
    Output getResult();
}
```

we see that there is no explicit code for navigating the `Collection`. Every `Collection` element (in this case an `Integer`) is passed to the 'handle(...)'  method of the `CollectionElementHandler`, via [Inversion Of Control](http://martinfowler.com/bliki/InversionOfControl.html)

# Why use a `CollectionTraverser`? #

Some of the main reasons for using a `CollectionTraverser` are:
  1. To stop you from writing or generating the same code over and over - Granted that many IDES such as [Intellij](http://www.jetbrains.com/idea/) and [Eclipse](http://www.eclipse.org/) can automate the creation of template code, for loops. That still does not prevent you from repeating the same iterative code.
  1. To make your life easier by being given each `Collection` element.
  1. Not having to worry about the looping mechanism and focus on the logic.
  1. To help in testing - A loop is a natural logic boundary (everything within the loop usually has a single purpose) and is a good candidate for extracting a (cohesive) class. Loops that exist within private methods can't be tested directly. They must be tested through public entry points. Extracting separate classes, makes them easier to test.

# How about a more complex example? #

Let's take a `DynamicMBean` implementation as an example:

```
public final class ContextAwareMBean implements DynamicMBean
    ...
    public MBeanInfo getMBeanInfo() {
        String name = getClass().getName();
        String description = this.description;
        MBeanConstructorInfo[] constructors = {};
        MBeanOperationInfo[] operations = {};
        MBeanNotificationInfo[] notifications = {};
        
        List<MBeanAttributeInfo> attributesList = buildAttributeList();
        MBeanAttributeInfo[] attributes = attributesList.toArray(new MBeanAttributeInfo[0]);
        return new MBeanInfo(name, description, attributes, constructors, 
                operations, notifications);
    }

    private List<MBeanAttributeInfo> buildAttributeList() {
        List<MBeanAttributeInfo> attributesList = new ArrayList<MBeanAttributeInfo>();
        for (Iterator it = properties.keySet().iterator(); it.hasNext();) {
            String propertyName = (String) it.next();
            if (propertyName.startsWith(context + "_")) {
                String attributeName = propertyName.substring(context.length() + 1);
                MBeanAttributeInfo attribute = new MBeanAttributeInfo(attributeName, String.class.getName(), "", true, true, false);
                attributesList.add(attribute);
            }
        }
        return attributesList;
    }

    ...
```

The entry point for testing this class is at the `getMBeanInfo()` method. The `buildAttributeList()` method which does the bulk of the work can't be tested directly - as it is a private method.

Using the `CollectionTraverser` we could do this:

```
public final class AttributeListBuilder implements CollectionElementHandler<MBeanAttributeInfo, List<MBeanAttributeInfo>> {

    private final List<MBeanAttributeInfo> attributesList = new ArrayList<MBeanAttributeInfo>();

    public AttributeListBuilder(final String context, final MBeanFactory factory) {
       ...
    }

    public void handle(final MBeanAttributeInfo info) {
            String propertyName = info.toString();
            if (propertyName.startsWith(context + "_")) {
                String attributeName = propertyName.substring(context.length() + 1);
                MBeanAttributeInfo attribute = factory.createMBeabAttributeInfo(attributeName, String.class.getName());
                attributesList.add(attribute);
            }
    }

    public List<MBeanAttributeInfo>> getResult() {
       return attributesList;
    }
}
```

The `AttributeListBuilder` class is quite simple and it is easier to test. The `ContextAwareMBean` is also simpler because it delegates building of the attribute list to the `AttributeListBuilder`. I've also hidden instantiation behind a `MBeanFactory`:

```
public final class ContextAwareMBean implements DynamicMBean

    public ContextAwareBean(CollectionTraverser traverser, MBeanFactory factory) {
       ...
    }

    ...

    public MBeanInfo getMBeanInfo() {
        List<MBeanAttributeInfo> attributesList = traverser.forEach(properties.keySet(), factory.createAttributeBuilder());
        //TODO: move this to the AttributeBuilder.
        MBeanAttributeInfo[] attributes = attributesList.toArray(new MBeanAttributeInfo[0]);

        return factory.createMBeanInfo(getClass().getName(), description, attributes);
    }

    ...
}
```

# How do I use paths on `Collections` ? #

All supported handlers (`CollectionElementHandler, CollectionElementWithIndexHandler and CollectionElementWithResultHandler`) support the use of bean paths. A bean path allows you to selected a nested bean property of an element within a `Collection`.

If for example, you wanted the `Package` name of a `Class` you could use "package.name" as the path, if the `Collection` contained `Class` elements:

```
List<Class<?>> classes = Arrays.<Class<?>>asList(UrlBoundary.class, Connection.class, String.class, Arrays.class);
List<String> result = traverser.forEach(classes, "package.name", new PackageNameRetreiver());
```

This would resolve to Class.getPackage().getName() for each element and the String value of the package name would be passed to the handler:
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

This saves you from having to call `getPackage().getName()` on each element manually:

```
public final class PackageNameRetreiver implements CollectionElementHandler<Class<?>, List<String>> {

   ...

    public void handle(final Class<?> element) {
        packageNames.add(element.getPackage().getName());
    }

    ...
}
```


> See [PackageNameRetrieverExample](PackageNameRetrieverExample.md) for the full example.

In addition to properties which would follow the standard getProperty or isProperty nomenclature, any arbitratry parameterless method is supported as long as it is fully specified.

For example, to call the `toString()` on a `Package` in the above example, you would use `"package.toString"`.

# What if I need the current index of an iteration? #

You should implement the `CollectionElementWithIndexHandler`. This will give you access to the index and whether the element is the first or last element:

```
package com.googlecode.pinthura.traverser.collection;

public interface CollectionElementWithIndexHandler<Input, Output> {

    void handle(Input element, boolean first, boolean last, Long index);
    Output getResult();
}
```

It can be called with the following methods on `CollectionTraverser`:

```
<Input, Output> Output forEachWithIndex(Collection<? extends Input> collection,
                                            CollectionElementWithIndexHandler<Input, Output> handler);

<Input, Target, Output> Output forEachWithIndex(Collection<? extends Input> collection, String path,
                                   CollectionElementWithIndexHandler<Target, Output> handler);
```

See CharacterFormatterExample for a full example.

# What if I need the previous result of an iteration? #

You should implement the `CollectionElementWithResultHandler`. This will give you access to the previous result:

```
package com.googlecode.pinthura.traverser.collection;

public interface CollectionElementWithResult<Input, Output> {

    Output handle(Input element, Output prevResult);
}
```

It can be called with the following methods on `CollectionTraverser`:

```
<Input, Output> Output forEachWithResult(Collection<? extends Input> collection,
                                             CollectionElementWithResult<Input, Output> handler, Output prevResult);

<Input, Target, Output> Output forEachWithResult(Collection<? extends Input> collection, String path,
                                             CollectionElementWithResult<Target, Output> handler, Output prevResult);
```

See TotalHandlerWithResultExample for a full example.

# What if I want to filter elements that are handled? #

# Examples #
  1. [Package name retriever](PackageNameRetrieverExample.md)
  1. [Character formatter](CharacterFormatterExample.md)
  1. [Total handler](TotalHandlerWithResultExample.md)