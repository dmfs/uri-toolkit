[![Build Status](https://travis-ci.org/dmfs/uri-toolkit.svg?branch=master)](https://travis-ci.org/dmfs/uri-toolkit)

# uri-toolkit

An [RFC 3986](https://tools.ietf.org/html/rfc3986) compliant Java URI implementation.

# Rationale

Java's [`URI`](https://docs.oracle.com/javase/7/docs/api/java/net/URI.html) class is quite old
and based on the obsoleted [RFC 2396](https://tools.ietf.org/html/rfc2396) which is
quite [different from RFC 3986 in a couple of points](https://tools.ietf.org/html/rfc3986#appendix-D).
One of the differences is that RFC 3986 allows URIs with only a scheme like `about:`, which Java's URI
class fails to parse. Also the definition of reserved and unreserved characters has been updated.

In addition the design of Java's URI class can be confusing and makes it easy to use it in a wrong way.
For instance, it's easy to confuse `getPath()` with `getRawPath()` and from the name it's hard to grasp which one returns the decoded path
(based on the name, the author of this would expect the reverse of the actual behavior).
Not to mention that decoding the path as a whole can lead to wrong results.

Also, there is no easy way to operate on URIs.
Appending path components or query parameters the right way can be a challenging task.

This library aims to make it easy to work with URIs and to make it hard to get it wrong.

Note, when we write *URI* we actually mean [URI Reference](https://tools.ietf.org/html/rfc3986#section-4.1), but for brevity and as per common usage we just call
it URI. Also the `Uri` interface is actually the interface of a URI reference. Because a URI is just a special case of a URI reference this doesn't add any limitations.
Instead it represents the real world use cases much better, which often do not distinguish between URI and URI reference.

# Usage

## Parse a URI

One of the most common use cases is to parse a URI from a String.

To create a `Uri` instance from a `String` call
```java
Uri uri = new LazyUri(new Precoded("https://example.com/path/?q=me%40example.com&key=some%20value&key=value2#fragment"));
```
Note that `LazyUri` expects a `UriEncoded` char sequence. Normally you just wrap a `String` in `Precoded` like in the example.

As the name indicates, `LazyUri` parses the URI lazily. That means it only parses as much as it has to. Calling `uri.scheme().value()` will only parse the scheme component, given there is any.
The line above, in particular, doesn't parse anything. This lazy behavior reduces overhead if you're not interested in the latter parts of the URI.
However, it also means that the URI hasn't been fully validated until `uri.fragment().isPresent()` has been called, because
otherwise not everything may have been parsed and invalid trailing characters may not have been found yet.

### Reading `Uri` components

You can read the individual components with the respective methods of the `Uri` interface. Note that most of the return `Optional`s because
they are actually optional in a URI-reference. Reading the host (of the URI above) can be done as follows:
```java
Optional<Authority> optAuthority = uri.authority();
if (optAuthority.isPresent())
{
   UriEncoded host = optAuthority.host();
}
```

`Path` is the only component that's always present and not returned as an `Optional`. But the path can be empty.
```
Path path = uri.path();
```

## Parsing `x-www-form-urlencoded` query parameters and fragments

As per RFC 3986 the query value is just an encoded character sequence. However, since the early days of the Internet it's
common to structure the query as `x-www-form-urlencoded` key-value pairs. This requires some special treatment, because the encoding is slightly different in this case.

The easiest way to read these parameters is by using one of the available adapters like so (again using the `uri` from above):

```java
// if the q parameter is mandatory, just declare a TextParam that represents the value.
// if the q paramter is not present in the query, an exception will be thrown when you *use* the object "q"
CharSequence q = new TextParam("q", new FormUrlEncoded(uri.query()));

// if the q parameter is optional, create an OptionalParameter that can be checked for its presence before using it
Optional<CharSequence> q = new OptionalParameter(new TextParamType(new Encoded("q")), new FormUrlEncoded(uri.query()));

// to get repeated parameters, declare an Iterable that returns the values.
Iterable<CharSequence> keys = new MultiParameter(new TextParamType(new Encoded("key")), new FormUrlEncoded(uri.query()));
```
Note that these adapters return decoded values, but by implementing your own `ParamType` you easily return the encoded form instead.

Parsing an `x-www-from-urlencoded` fragment works exactly the same way, just pass the fragment to `FormUrlEncoded`.

## Building URIs

Building `Uri`s works similar to Java's `URI` class, though this handles encoding a little bit differently. When you create a `Uri` from its components,
you have to make sure they are properly encoded. `Uri` doesn't automatically encode anything. You see that by the fact that all constructors
take `UriEncoded` char sequences instead of plain `CharSequence`s or `String`s.

Creating a simple HTTP URL works like this:

```java
Uri newUri = new StructuredUri(Schemes.HTTPS,
                 new StructuredAuthority(new Encoded("www.google.com")),
                 new StructuredPath(IdempotentEncoded.EMPTY, new Encoded("search"))
                 new SimpleQuery(new Precoded("q=uri+rfc+3986")))
```

Note the `IdempotentEncoded.EMPTY` in the path. This represents the root directory of the path. So the path results in `/search`.

To convert this `Uri` into a `CharSequence` you use the `Text` adapter like so:
```java
CharSequence uriText = new Text(newUri);
// to retrieve a String you call
String uriString = new Text(newUri).toString();
```

## Resolving URIs

To resolve a URI-reference against a base URI you use `Resolved` compositor like so:

```java
Uri resolved = new Resolved(
        new LazyUri(new Encoded("https://www.google.com/search?q=uri+rfc+3986")),
        new LazyUri(new Encoded("/images/branding/googlelogo/2x/googlelogo_color_120x44dp.png")));
```
which results in the URI `https://www.google.com/images/branding/googlelogo/2x/googlelogo_color_120x44dp.png`.

## Normalizing URIs

URIs can be normalized with the `Normalized` decorator like so:

```java
Uri normalized = new Normalized(new LazyUri(new Encoded("https://example.com/123/../%61%40%62@c")));
```
Which results in `https://example.com/a%40b@c`


# Design principles & goals

The following sections give a brief overview over the most important design goals and principles of this library.
Note that the list is certainly not exhaustive and we don't intend to discuss these principles to the full extend.
There is certainly a lot more to say about each of them.

## Dedicated type for encoded CharSequences

One of the major issues that make it hard to work with most URI/URL implementations is encoding. Often it's not clear when or how or what to encode or decode.

To make it easier to handle encoded character sequences, this library uses the dedicated type `UriEncoded` (a subtype of `CharSequence`).
This type (or a subtype of it) is used whenever an encoded character sequence is expected or returned.

`UriEncoded` provides methods to decode the value into a plain `CharSequence` and to return a normalized version (which decodes encoded characters from the
unreserved range and converts percent encoded char sequences to upper case).

## Single responsibility

Most classes in this library are designed to have a single responsibility. That means most classed don't even implement `toString()` and leave the conversion
to a character sequence to an adapter class. For instance, to "convert" a `Path` to a `CharSequence` you use the `Text` adapter.
Classes that implement `Path` are not required to return a `String` representation of the path.

This keeps the classes small and focused and helps to reduce code duplication. Ideally all classes would have only a single responsibility,
which mean no two classes need to have the same responsibility, otherwise one of them would be redundant.

It also makes it much easier to write unit tests, because you don't need to test side effects between these responsibilities.

## No inheritance

The "no inheritance" principle is almost implied by the single responsibility principle, because inheritance is often used to add more functionality to a class.
Instead of extending the functionality of classes by inheritance this library makes extensive use of Adapter, Decorator and Composite Patterns.

All interfaces are designed for "no inheritance" (e.g. params and return types are interfaces instead of classes, interfaces declare only very few methods),
which makes it easy to add more functionality by writing new adapters or decorators.

To enforce the "no inheritance" principle all classes are `final`.

This also makes it easier to test classes, because you don't need to test for "regression" bugs in the inherited behavior.

## Designed for extensibility

Every public method in this library implements an interface. So it's easy to write adapters and decorators to extend the functionality of a class without
having to worry about breaking existing functionality.

Extending functionality without having to touch existing code reduces the chances of introducing regression bugs.

## Immutability

Most classes of this library are immutable. The only exceptions are a couple of `Iterators` that we use.

Immutability helps to avoid side effects through concurrent modification or temporal coupling. You don't have to worry if there might be any other
object that holds a reference to the object that you want to modify (because you can't modify it).

In addition immutability makes it easier to test classes, because there is no mutable state to be considered in the tests.


## TODO

The library is still in an early stage. In addition to possible changes in design and architecture, some planned features are not implemented yet, like

* Additional validation of encoded input
* Validate IPv6 & Future-IP addresses
* Support for [URI templates](https://tools.ietf.org/html/rfc6570)
* Efficient support for [Data URIs](https://tools.ietf.org/html/rfc2397)
* Fluent builders for `Uri`, `Path` and `Parametrized` objects

# License

Copyright dmfs GmbH 2017, licensed under Apache2.


