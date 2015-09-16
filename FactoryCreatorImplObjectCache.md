# An Object Cache Example #

This examples demonstrates dynamic factory injection. In `ObjectCacheFactory` listed below, one `create` method takes a `ObjectCacheEventFactory` as a parameter. The other `create` method does not and automatically injects the `ObjectCacheEventFactory` into the constructor of the `SingleInstanceCache` class by using information in the `@InjectedFactory` annotation. The `ObjectCacheRunner` demonstrates both cases.

# Details #

```
public interface ObjectCacheFactory {

    @Implementation(SingleInstanceCache.class)
    @InjectedFactory(@Factory(factoryClass = ObjectCacheEventFactory.class, index = 0))
    ObjectCache create();

    @Implementation(SingleInstanceCache.class)
    ObjectCache create(ObjectCacheEventFactory objectCacheEventFactory);
}
```

```
public interface ObjectCacheEventFactory {

    @Implementation(ObjectCacheEventImpl.class)
    ObjectCacheEvent create(Object obj);
}
```

```
public interface ObjectCacheEvent {

    Object getInstance();
}
```

```
public interface ObjectCache {

    void addListener(ObjectCacheListener listener);
    void setInstance(Object obj);
}
```

```
public final class ObjectCacheEventImpl implements ObjectCacheEvent {

    private final Object instance;

    public ObjectCacheEventImpl(final Object instance) {
        this.instance = instance;
    }

    public Object getInstance() {
        return instance;
    }
}
```

```
public interface ObjectCacheListener {

    void instanceCreated(ObjectCacheEvent event);
}
```

```
public final class SingleInstanceCache implements ObjectCache {

    private final List<ObjectCacheListener> listeners;
    private final ObjectCacheEventFactory factory;

    public SingleInstanceCache(final ObjectCacheEventFactory factory) {
        this.factory = factory;
        listeners = new ArrayList<ObjectCacheListener>();
    }

    public void addListener(final ObjectCacheListener listener) {
        listeners.add(listener);
    }

    public void setInstance(final Object obj) {
        for (ObjectCacheListener listener : listeners) {
            listener.instanceCreated(factory.create(obj));
        }
    }
}
```


The runner:

```
public final class ObjectCacheRunner {

    private ObjectCacheRunner() { }

    public static void main(final String[] args) {
        System.out.println("Without Dynamic Factories");
        exerciseCache(withDynamicFactories());
        System.out.println();
        System.out.println("With Dynamic Factories");
        exerciseCache(withoutDynamicFactories());
    }

    private static ObjectCache withoutDynamicFactories() {
        FactoryCreator factoryCreator = new FactoryCreatorBuilder().build();
        ObjectCacheFactory cacheFactory = factoryCreator.create(ObjectCacheFactory.class);
        ObjectCacheEventFactory eventFactory = factoryCreator.create(ObjectCacheEventFactory.class);
        return cacheFactory.create(eventFactory);
    }

    private static ObjectCache withDynamicFactories() {
        FactoryCreator factoryCreator = new FactoryCreatorBuilder().build();
        return factoryCreator.create(ObjectCacheFactory.class).create();
    }

    private static void exerciseCache(final ObjectCache cache) {
        cache.addListener(new ObjectCacheListener() {
            public void instanceCreated(final ObjectCacheEvent event) {
                System.out.println("The following instance was cached: " + event.getInstance());
            }
        });

        cache.setInstance("Hello World!");
        cache.setInstance(1);
        cache.setInstance(Arrays.asList("3", "2", "1"));
    }

}
```

The output from running the above:

```
Without Dynamic Factories
The following instance was cached: Hello World!
The following instance was cached: 1
The following instance was cached: [3, 2, 1]

With Dynamic Factories
The following instance was cached: Hello World!
The following instance was cached: 1
The following instance was cached: [3, 2, 1]
```