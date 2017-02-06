package org.dmfs.rfc3986.paths;

import org.dmfs.rfc3986.encoding.Encoded;
import org.dmfs.rfc3986.encoding.IdempotentEncoded;
import org.dmfs.rfc3986.UriEncoded;
import org.hamcrest.Matchers;
import org.hamcrest.collection.IsEmptyIterable;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;


/**
 * @author marten
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
        assertThat(new StructuredPath(), new IsEmptyIterable());
        assertThat(new StructuredPath(IdempotentEncoded.EMPTY), Matchers.contains((UriEncoded) IdempotentEncoded.EMPTY));
        assertThat(new StructuredPath(IdempotentEncoded.CURRENT), Matchers.contains((UriEncoded) IdempotentEncoded.CURRENT));
        assertThat(new StructuredPath(IdempotentEncoded.PARENT), Matchers.contains((UriEncoded) IdempotentEncoded.PARENT));
        assertThat(new StructuredPath(IdempotentEncoded.PARENT, IdempotentEncoded.PARENT), Matchers.contains((UriEncoded) IdempotentEncoded.PARENT, IdempotentEncoded.PARENT));
        assertThat(new StructuredPath(IdempotentEncoded.EMPTY, new Encoded("a"), new Encoded("b")), Matchers.contains((UriEncoded) IdempotentEncoded.EMPTY, new Encoded("a"), new Encoded("b")));
        assertThat(new StructuredPath(IdempotentEncoded.CURRENT, new Encoded("a"), new Encoded("b")), Matchers.contains((UriEncoded) IdempotentEncoded.CURRENT, new Encoded("a"), new Encoded("b")));
        assertThat(new StructuredPath(IdempotentEncoded.PARENT, new Encoded("a"), new Encoded("b")), Matchers.contains((UriEncoded) IdempotentEncoded.PARENT, new Encoded("a"), new Encoded("b")));
    }
}