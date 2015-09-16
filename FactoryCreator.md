## A simple example ##

Assume you have a `Square` interface:

```
public interface Square {

    int getLength();
    int getArea();
}
```

And an implementation:

```
public final class SquareImpl implements Square {

    private static final int SIDES_OF_SQUARE = 4;

    private final int length;

    public SquareImpl(final int length) {
        this.length = length;
    }

    public int getLength() {
        return SIDES_OF_SQUARE * length;
    }

    public int getArea() {
        return length * length;
    }
}
```

You now want to hide the instantiation of the `SquareImpl` behind a factory interface:

```
public interface SquareFactory {

    Square createSquare(int length);
 }
```

The `SquareFactory` is used in the `ShapeCanvas`:

```
public final class ShapeCanvas {

    private final SquareFactory squareFactory;
    private int canvasArea;

    public ShapeCanvas(final int canvasArea, final SquareFactory squareFactory) {
        this.canvasArea = canvasArea;
        this.squareFactory = squareFactory;
    }

    public int calcSquaresOnCanvas(final int size) {
        Square square = squareFactory.create(size);
        return canvasArea / square.getArea();
    }
}
```

The `ShapeCanvas` is run through the `ShapeCanvasRunner`:

```
public final class ShapeCanvasRunner {

    private static final int CANVAS_AREA = 40;

    private ShapeCanvasRunner() {
        //Main class.
    }

    public static void main(final String[] args) {
        FactoryCreator factoryCreator = new FactoryCreatorBuilder().build();
        SquareFactory squareFactory = factoryCreator.create(SquareFactory.class);

        ShapeCanvas canvas = new ShapeCanvas(CANVAS_AREA, squareFactory);
        System.out.println("Number of squares: " + canvas.calcSquaresOnCanvas(2));
    }
}
```

This gives the following output:

```
Number of squares: 10
```

You have now created an instance of `SquareImpl` which is used in the `ShapeCanvas#calcSquaresOnCanvas` method, without using the `new` keyword or implementing the `SquareFactory` interface.

# Why hide object creation in a factory? #

Here is an implementation of the `ShapeCanvas` where a `SqaureImpl` is created directly with `new` instead of using a factory:
```
public final class ShapeCanvas {

    ...

    public int calcSquaresOnCanvas(int size) {
        Square square = new SquareImpl(size);
        int area = square.getArea();
        return canvasArea / area;
    }
}

```
1. Dependencies on implementations - By directly `new`ing up an instance of a `SquareImpl`, the `ShapeCanvas` class now depends on the `SqaureImpl` class. Thus changing the the `SquareImpl` class directly affects the `ShapeCanvas`. This is not the case when using a factory as the implementation is hidden behind the `Square` inteface.

We also can't swap out the `SquareImpl` for another implementation of the `Sqaure` interface. We can do this via a factory as the factory returns a `Square` interface.

2. We can't test in isolation - We have to resort to state-bases tests as we can't [mock](http://www.easymock.org/) [out](http://www.jmock.org/) the `SquareImpl` class since it is created within the `calcSquaresOnCanvas()` method . When testing the `calcSquaresOnCanvas()` method, we would be testing both the `SquareImpl` class's `getArea()` method as well as the `calcSquaresOnCanvas()` method in the same test.

If we use a factory to handle creation of `Square`s:

```
public final class ShapeCanvas {

    ...

    public ShapeCanvas(final int canvasArea, final SquareFactory squareFactory) {
       ...
    }

    public int calcSquaresOnCanvas(int size) {
        Square square = squareFactory.create(size);
        ...
    }
}

```

We can mock out the `SquareFactory` interface passed to the constructor very easily.

# What does the FactoryCreator give me? #

At some point we need a concrete implementation of the factory interface. In many instances these factory implementations are not tested or have rudimentary tests that simply create a new instance of an object.

1. The `FactoryCreator` automates the implementation of a factory interface and saves you having to write an implementation class for each factory interface you use.

In the above scenario the manual implementation of `SquareFactoryImpl` would be:
```
public final class SquareFactoryImpl implements SquareFactory {

    public Square createSquare(int length) {
       return new SquareImpl(length);
    }
}
```

When using the `FactoryCreator` you can simply use:

```
SquareFactory squareFactory = factoryCreator.create(SquareFactory.class);
```

# Creating factories is easy. Why bother automating it? #

Although factory implementations are usually simple, they can be cumbersome because:

  1. Hard to test - The instances created through the factory may be hard to setup and test.
  1. Unnecessary classes - An implementation class needs to be written for every factory interface. This is tedious in and of itself. If you are tdding/[bdding](http://code.google.com/p/instinct/), each implementation also needs at least one [Test](http://www.junit.org/) [Case](http://testng.org/doc/).
  1. Time wasted - All implementations need to pass the usual quality checks (eg. [Checkstyle](http://checkstyle.sourceforge.net/), [Code Coverage](http://cobertura.sourceforge.net/) etc)

# How does FactoryCreator locate implementations? #

Each method defined in a factory interface has the following form:

> `Returned_Interface Method_Name(Constructor_Arguments);`

Each implementation of the `Returned_Interface` is located as follows:

![http://pinthura.googlecode.com/svn/wiki/images/FactoryCreator2.png](http://pinthura.googlecode.com/svn/wiki/images/FactoryCreator2.png)

If we take the `SquareFactory` as an example:

```
public interface SquareFactory {

    Square createSquare(int length);
}
```

```
Returned_Interface -> Square
Method_Name -> createSquare
Constructor_Arguments -> length
```

An implementation of `Square` will be located using an instantiation strategy. In general, the constructor of the located class will then be called with `length`.

At the moment, the `FactoryCreator` finds implementations using three instantiation strategies:

  1. `AnnotationInstantiator` - Use the `@Implementation` to define the target class to instantiate.
  1. `DynamicFactoryInstantiator` - Uses the `@Implementation` and `@InjectedFactory` to find the target class and automatically inject any required factories.
  1. `SimpleInstantiator` - Uses a `ClassNameDeriver` instance to find the target class to instantiate.

# How do I use the `AnnotationInstantiator` strategy? #

Taking a `ObjectCacheEventFactory` as an example:

```
public interface ObjectCacheEventFactory {

    @Implementation(ObjectCacheEventImpl.class)
    ObjectCacheEvent create(Object obj);
}

 ...

 ObjectCacheEventFactory  factory = factoryCreator.create(ObjectCacheEventFactory.class);

```

When the `ObjectCacheEventFactory` interface is passed to the `FactoryCreator` it uses the `AnnotationInstantiator` to find an instance of a class implementing the `ObjectCacheEvent` interface. The above example uses the `@Implementation` annotation to define its implementation class: `ObjectCacheEventImpl`.

# How do I use the `DynamicFactoryInstantiator` strategy? #

Taking `ObjectCacheFactory` as an example:

```
public interface ObjectCacheFactory {

    @Implementation(SingleInstanceCache.class)
    @InjectedFactory(@Factory(factoryClass = ObjectCacheEventFactory.class, index = 0))
    ObjectCache create();
}

 ...

 ObjectCacheFactory  factory = factoryCreator.create(ObjectCacheFactory.class);
```

When the `ObjectCacheFactory` interface is passed to the `FactoryCreator` it uses the `DynamicFactoryInstantiator` to find an instance of a class implementing the `ObjectCache` interface. The above example uses the `@Implementation` annotation to define its implementation class: `SingleInstanceCache`. The `SingleInstanceCache` constructor takes in the `ObjectCacheEventFactory` factory interface as it's first parameter. This is defined through the `@InjectedFactory` and the `@Factory` which specifies the factory class and its position within the constructor call to the implementation. Dependent factories can be injected in this manner without passing them to the implementation classes' constructor through the `create` method.

You could use the `AnnotationInstantiator` to create the above factory but it would look like:

```
public interface ObjectCacheFactory {

    @Implementation(SingleInstanceCache.class)
    ObjectCache create(ObjectCacheEventFactory objectCacheEventFactory);
}
```

When the create method is call, you now have to manually pass in an instance of `ObjectCacheEventFactory` which was dynamically created for you when the `@Factory` was used.

See the `ObjectCache` example for more details.

# How do I use the `SimpleInstantiator` strategy? #

Taking the `SquareFactory` as an example:

```
public interface SquareFactory {

    Square createSquare(int length);
}

 ...

 SquareFactory squareFactory = factoryCreator.create(SquareFactory.class);

```

When the `SquareFactory` interface is passed to the `FactoryCreator` it uses the `SimpleInstantiator` to find an instance of a class implementing the `Square` interface. The default naming strategy for the `SimpleInstantiator` is the `ImplSuffixingDeriver`, which simply appends an **Impl** to the name of the interface (`Square` -> `SquareImpl`). In the above example a new instance is created as -> `new SquareImpl(length)`.

# How do I extend the `SimpleInstantiator` to use another strategy instead of `ImplSuffixingDeriver`? #

Create a custom implementation of the `ClassNameDeriver` interface and pass that into the constructor of a `SimpleInstantiator` instance.

`ClassNameDeriver` takes the form:

```
public interface ClassNameDeriver {

    String derive(MethodParam methodParam);
}
```

# Where can I update the default instantiation strategies? #

The above default strategies are initialized in the the `InstanceCreatorImpl`
class:
```
public final class InstanceCreatorImpl implements InstanceCreator {

    private final FilterLink<MethodParam, Object> filterChain;

    public InstanceCreatorImpl(final FilterLink<MethodParam, Object> filterChain) {
        this.filterChain = filterChain;
    }

    public Object createInstance(final MethodParam param) {
        try {
            return filterChain.filter(param);
        } catch (MatchNotFoundException e) {
            throw new InstanceCreationException("Could not create instance of " + param.getReturnType(), e);
        }
    }
}
```

Any required instantiators can be passed in as `FilterChain` of `InstantiationStrategy`:

```
public interface InstantiationStrategy extends FilterLink<MethodParam, Object> {
    //This interface has been introduced to make the generics easier to use.
}
```

See the `InstanceCreatorBuilder` class to see how the defaults are built.

# Examples #

  * [Report Generator](FactoryCreatorImplReportGenerator.md)
  * [Object Cache](FactoryCreatorImplObjectCache.md)
  * [Schema Parser](FactoryCreatorImplSchemaParser.md)