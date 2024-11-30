package org.dmfs.rfc3986.paths;

import org.dmfs.rfc3986.encoding.Encoded;
import org.dmfs.rfc3986.encoding.IdempotentEncoded;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.saynotobugs.confidence.Assertion.assertThat;
import static org.saynotobugs.confidence.core.quality.Iterable.emptyIterable;
import static org.saynotobugs.confidence.core.quality.Iterable.iterates;


/**
 */
public class StructuredPathTest
{
    @Test
    public void testIsEmpty() throws Exception
    {
        assertTrue(new StructuredPath().isEmpty());
        assertFalse(new StructuredPath(IdempotentEncoded.CURRENT).isEmpty());
        assertFalse(new StructuredPath(new Encoded("abc")).isEmpty());
        assertFalse(new StructuredPath(new Encoded("abc"), new Encoded("123")).isEmpty());
    }


    @Test
    public void testIsAbsolute() throws Exception
    {
        assertFalse(new StructuredPath().isAbsolute());
        assertFalse(new StructuredPath(IdempotentEncoded.CURRENT).isAbsolute());
        assertFalse(new StructuredPath(new Encoded("abc")).isAbsolute());
        assertFalse(new StructuredPath(new Encoded("abc"), new Encoded("123")).isAbsolute());
    }


    @Test
    public void testIterator() throws Exception
    {
        assertThat(new StructuredPath(), emptyIterable());
        assertThat(new StructuredPath(IdempotentEncoded.EMPTY), iterates(IdempotentEncoded.EMPTY));
        assertThat(new StructuredPath(IdempotentEncoded.CURRENT), iterates(IdempotentEncoded.CURRENT));
        assertThat(new StructuredPath(IdempotentEncoded.PARENT), iterates(IdempotentEncoded.PARENT));
        assertThat(new StructuredPath(IdempotentEncoded.PARENT, IdempotentEncoded.PARENT),
            iterates(IdempotentEncoded.PARENT, IdempotentEncoded.PARENT));
        assertThat(new StructuredPath(IdempotentEncoded.EMPTY, new Encoded("a"), new Encoded("b")),
            iterates(IdempotentEncoded.EMPTY, new Encoded("a"), new Encoded("b")));
        assertThat(new StructuredPath(IdempotentEncoded.CURRENT, new Encoded("a"), new Encoded("b")),
            iterates(IdempotentEncoded.CURRENT, new Encoded("a"), new Encoded("b")));
        assertThat(new StructuredPath(IdempotentEncoded.PARENT, new Encoded("a"), new Encoded("b")),
            iterates(IdempotentEncoded.PARENT, new Encoded("a"), new Encoded("b")));
    }
}