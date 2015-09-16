# Introduction #

The `FilterChainImpl` is an implementation of the (Gof) [Chain of Responsibility](http://dofactory.com/Patterns/PatternChain.aspx) pattern.

The basic premise is, that, one of many handlers, may handle a given input. At runtime each handler will be given a chance to handle the input. If it can't handle the input, it passes it to the next handler in the chain, until a handler is found or all handlers are exhausted.

# Why use the Chain of Responsibility? #

  1. Decouples the sender of a request from the object that handles the request.
  1. Dynamically updating the chain of handlers for a given request. (no if-else nastiness)


# How does `FilterChainImpl` implement the Chain of Responsibility? #

The `FilterChainImpl` class and the `FilterLink` interface are used to implement the COR pattern.

`public final class FilterChainImpl<Input, Output> implements FilterLink<Input, Output>`

`public interface FilterLink<Input, Output>`

In this setting the _Handlers_ are called `FilterLinks`. The `FilterChainImpl` is initialized through a constructor that takes in a `List<FilterLink>`.

**Guidelines for implementing the `FilterLink` interface**:
  1. Each implementation in the List of `FilterLink`s have to accept the same input and same output types.
  1. If the `FilterLink` can handle the input, it must return the result.
  1. A `FilterLink` must throw a `MatchNotFoundException` to indicate it can't handle the input.
  1. Each `FilterLink` will be called multiple times. The state of the `FilterLink` implementation should remain constant between calls.

**Guidelines for using the `FilterChainImpl` class**:

  1. The `FilterChainImpl` class is an implementation of the `FilterLink` interface and may throw a `MatchNotFoundException` if none of the supplied `FilterLink`s can handle the input. In such a situation, catching the `MatchNotFoundException` and rethrowing a more specific exception is encouraged:

Eg.

```

try {
  filterChain.filter(fileName);
} catch (MatchNotFoundException e) {
   throw new ConfigFileNotFoundException(e);
}

```

# Examples #

  * [Mail Filter](FilterChainImplMailFilter.md)
  * [XML Version Filter](FilterChainImplXMLVersionFilter.md)