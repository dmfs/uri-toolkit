package org.dmfs.rfc3986.paths;

import org.dmfs.rfc3986.encoding.Precoded;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


/**
 */
public class EncodedPathTest
{
    @Test
    public void isEmpty() throws Exception
    {
        assertTrue(new EncodedPath(new Precoded("")).isEmpty());
        assertFalse(new EncodedPath(new Precoded(".")).isEmpty());
        assertFalse(new EncodedPath(new Precoded("abc")).isEmpty());
        assertFalse(new EncodedPath(new Precoded("abc/123")).isEmpty());

    }


    @Test
    public void isAbsolute() throws Exception
    {
        assertFalse(new EncodedPath(new Precoded("")).isAbsolute());
        assertFalse(new EncodedPath(new Precoded(".")).isAbsolute());
        assertFalse(new EncodedPath(new Precoded("./")).isAbsolute());
        assertFalse(new EncodedPath(new Precoded("abc")).isAbsolute());
        assertFalse(new EncodedPath(new Precoded("abc/")).isAbsolute());
        assertFalse(new EncodedPath(new Precoded("abc/123")).isAbsolute());
        assertFalse(new EncodedPath(new Precoded("abc/123/")).isAbsolute());

        assertTrue(new EncodedPath(new Precoded("/")).isAbsolute());
        assertTrue(new EncodedPath(new Precoded("/.")).isAbsolute());
        assertTrue(new EncodedPath(new Precoded("/./")).isAbsolute());
        assertTrue(new EncodedPath(new Precoded("/abc")).isAbsolute());
        assertTrue(new EncodedPath(new Precoded("/abc/")).isAbsolute());
        assertTrue(new EncodedPath(new Precoded("/abc/123")).isAbsolute());
        assertTrue(new EncodedPath(new Precoded("/abc/123/")).isAbsolute());
    }

//
//    @Test
//    public void resolved() throws Exception
//    {
//        // the first set of tests is taken from the examples in RFC 3986 Section 5.4
//        assertEquals("/b/c/g",new StringPath("/b/c/d;p").resolved(new StringPath("g")).toString());
//        assertEquals("/b/c/g",new StringPath("/b/c/d;p").resolved(new StringPath("./g")).toString());
//        assertEquals("/b/c/g/",new StringPath("/b/c/d;p").resolved(new StringPath("g/")).toString());
//        assertEquals("/g",new StringPath("/b/c/d;p").resolved(new StringPath("/g")).toString());
//        assertEquals("/b/c/;x",new StringPath("/b/c/d;p").resolved(new StringPath(";x")).toString());
//        assertEquals("/b/c/g;x",new StringPath("/b/c/d;p").resolved(new StringPath("g;x")).toString());
//        assertEquals("/b/c/d;p",new StringPath("/b/c/d;p").resolved(new StringPath("")).toString());
//        assertEquals("/b/c/",new StringPath("/b/c/d;p").resolved(new StringPath(".")).toString());
//        assertEquals("/b/c/",new StringPath("/b/c/d;p").resolved(new StringPath("./")).toString());
//        assertEquals("/b/",new StringPath("/b/c/d;p").resolved(new StringPath("..")).toString());
//        assertEquals("/b/",new StringPath("/b/c/d;p").resolved(new StringPath("../")).toString());
//        assertEquals("/b/g",new StringPath("/b/c/d;p").resolved(new StringPath("../g")).toString());
//        assertEquals("/",new StringPath("/b/c/d;p").resolved(new StringPath("../..")).toString());
//        assertEquals("/",new StringPath("/b/c/d;p").resolved(new StringPath("../../")).toString());
//        assertEquals("/g",new StringPath("/b/c/d;p").resolved(new StringPath("../../g")).toString());
//
//        assertEquals("/g",new StringPath("/b/c/d;p").resolved(new StringPath("../../../g")).toString());
//        assertEquals("/g",new StringPath("/b/c/d;p").resolved(new StringPath("../../../../g")).toString());
//
//        assertEquals("/g",new StringPath("/b/c/d;p").resolved(new StringPath("/./g")).toString());
//        assertEquals("/g",new StringPath("/b/c/d;p").resolved(new StringPath("/../g")).toString());
//        assertEquals("/b/c/g.",new StringPath("/b/c/d;p").resolved(new StringPath("g.")).toString());
//        assertEquals("/b/c/.g",new StringPath("/b/c/d;p").resolved(new StringPath(".g")).toString());
//        assertEquals("/b/c/g..",new StringPath("/b/c/d;p").resolved(new StringPath("g..")).toString());
//        assertEquals("/b/c/..g",new StringPath("/b/c/d;p").resolved(new StringPath("..g")).toString());
//
//        assertEquals("/b/g",new StringPath("/b/c/d;p").resolved(new StringPath("./../g")).toString());
//        assertEquals("/b/c/g/",new StringPath("/b/c/d;p").resolved(new StringPath("./g/.")).toString());
//        assertEquals("/b/c/g/h",new StringPath("/b/c/d;p").resolved(new StringPath("g/./h")).toString());
//        assertEquals("/b/c/h",new StringPath("/b/c/d;p").resolved(new StringPath("g/../h")).toString());
//        assertEquals("/b/c/g;x=1/y",new StringPath("/b/c/d;p").resolved(new StringPath("g;x=1/./y")).toString());
//        assertEquals("/b/c/y",new StringPath("/b/c/d;p").resolved(new StringPath("g;x=1/../y")).toString());
//
//        // the next set of tests is like the first one but with a relative base
//
//        assertEquals("b/c/g",new StringPath("b/c/d;p").resolved(new StringPath("g")).toString());
//        assertEquals("b/c/g",new StringPath("b/c/d;p").resolved(new StringPath("./g")).toString());
//        assertEquals("b/c/g/",new StringPath("b/c/d;p").resolved(new StringPath("g/")).toString());
//        assertEquals("/g",new StringPath("b/c/d;p").resolved(new StringPath("/g")).toString());
//        assertEquals("b/c/;x",new StringPath("b/c/d;p").resolved(new StringPath(";x")).toString());
//        assertEquals("b/c/g;x",new StringPath("b/c/d;p").resolved(new StringPath("g;x")).toString());
//        assertEquals("b/c/d;p",new StringPath("b/c/d;p").resolved(new StringPath("")).toString());
//        assertEquals("b/c/",new StringPath("b/c/d;p").resolved(new StringPath(".")).toString());
//        assertEquals("b/c/",new StringPath("b/c/d;p").resolved(new StringPath("./")).toString());
//        assertEquals("b/",new StringPath("b/c/d;p").resolved(new StringPath("..")).toString());
//        assertEquals("b/",new StringPath("b/c/d;p").resolved(new StringPath("../")).toString());
//        assertEquals("b/g",new StringPath("b/c/d;p").resolved(new StringPath("../g")).toString());
//        assertEquals("./",new StringPath("b/c/d;p").resolved(new StringPath("../..")).toString());
//        assertEquals("./",new StringPath("b/c/d;p").resolved(new StringPath("../../")).toString());
//        assertEquals("g",new StringPath("b/c/d;p").resolved(new StringPath("../../g")).toString());
//
//        assertEquals("../g",new StringPath("b/c/d;p").resolved(new StringPath("../../../g")).toString());
//        assertEquals("../../g",new StringPath("b/c/d;p").resolved(new StringPath("../../../../g")).toString());
//
//        assertEquals("/g",new StringPath("b/c/d;p").resolved(new StringPath("/./g")).toString());
//        assertEquals("/g",new StringPath("b/c/d;p").resolved(new StringPath("/../g")).toString());
//        assertEquals("b/c/g.",new StringPath("b/c/d;p").resolved(new StringPath("g.")).toString());
//        assertEquals("b/c/.g",new StringPath("b/c/d;p").resolved(new StringPath(".g")).toString());
//        assertEquals("b/c/g..",new StringPath("b/c/d;p").resolved(new StringPath("g..")).toString());
//        assertEquals("b/c/..g",new StringPath("b/c/d;p").resolved(new StringPath("..g")).toString());
//
//        assertEquals("b/g",new StringPath("b/c/d;p").resolved(new StringPath("./../g")).toString());
//        assertEquals("b/c/g/",new StringPath("b/c/d;p").resolved(new StringPath("./g/.")).toString());
//        assertEquals("b/c/g/h",new StringPath("b/c/d;p").resolved(new StringPath("g/./h")).toString());
//        assertEquals("b/c/h",new StringPath("b/c/d;p").resolved(new StringPath("g/../h")).toString());
//        assertEquals("b/c/g;x=1/y",new StringPath("b/c/d;p").resolved(new StringPath("g;x=1/./y")).toString());
//        assertEquals("b/c/y",new StringPath("b/c/d;p").resolved(new StringPath("g;x=1/../y")).toString());
//
//        // now for some custom test cases
//
//        assertEquals("g",new StringPath("b").resolved(new StringPath("g")).toString());
//        assertEquals("../g",new StringPath("b").resolved(new StringPath("../g")).toString());
//        assertEquals("g",new StringPath("b").resolved(new StringPath("./g")).toString());
//        assertEquals("b/g",new StringPath("b/").resolved(new StringPath("g")).toString());
//        assertEquals("./",new StringPath(".").resolved(new StringPath(".")).toString());
//
//    }
//
//
//    @Test
//    public void normalized() throws Exception
//    {
//        assertTrue(new StringPath("").normalized().isEmpty());
//        assertEquals("", new StringPath("").normalized().toString());
//        assertEquals("./", new StringPath(".").normalized().toString());
//        assertEquals("abc", new StringPath("abc").normalized().toString());
//        assertEquals("abc/123", new StringPath("abc/123").normalized().toString());
//        assertEquals("../abc/123", new StringPath("../abc/123").normalized().toString());
//        assertEquals("123", new StringPath("abc/../123").normalized().toString());
//        assertEquals("./", new StringPath("abc/123/../..").normalized().toString());
//        assertEquals("../", new StringPath("abc/123/../../..").normalized().toString());
//        assertEquals("../../123", new StringPath("../abc/../../123").normalized().toString());
//        assertEquals("123", new StringPath("./abc/./../123").normalized().toString());
//        assertEquals("../../", new StringPath("../abc/../../123/..").normalized().toString());
//
//        assertEquals("/", new StringPath("/").normalized().toString());
//        assertEquals("/", new StringPath("/.").normalized().toString());
//        assertEquals("/abc", new StringPath("/abc").normalized().toString());
//        assertEquals("/abc/123", new StringPath("/abc/123").normalized().toString());
//        assertEquals("/abc/123", new StringPath("/../abc/123").normalized().toString());
//        assertEquals("/123", new StringPath("/abc/../123").normalized().toString());
//        assertEquals("/", new StringPath("/abc/123/../..").normalized().toString());
//        assertEquals("/", new StringPath("/abc/123/../../..").normalized().toString());
//        assertEquals("/123", new StringPath("/../abc/../../123").normalized().toString());
//        assertEquals("/123", new StringPath("/./abc/./../123").normalized().toString());
//        assertEquals("/abc/123", new StringPath("/../abc/./123").normalized().toString());
//        assertEquals("/", new StringPath("/../abc/../../123/..").normalized().toString());
//    }
//


    @Test
    public void length() throws Exception
    {

    }


    @Test
    public void charAt() throws Exception
    {

    }


    @Test
    public void subSequence() throws Exception
    {

    }


    @Test
    public void iterator() throws Exception
    {

    }

}