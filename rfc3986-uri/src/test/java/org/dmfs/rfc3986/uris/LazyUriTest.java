/*
 * Copyright 2017 dmfs GmbH
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.dmfs.rfc3986.uris;

import org.dmfs.rfc3986.encoding.Precoded;
import org.dmfs.rfc3986.paths.Text;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


/**
 */
public class LazyUriTest
{
    @Test
    public void scheme() throws Exception
    {
        assertEquals("http", new LazyUri(new Precoded("http://example.com")).scheme().value().toString());
        assertEquals("http", new LazyUri(new Precoded("http://example.com/path")).scheme().value().toString());
        assertEquals("http", new LazyUri(new Precoded("http://example.com:123")).scheme().value().toString());
        assertEquals("http", new LazyUri(new Precoded("http://example.com:123/path")).scheme().value().toString());
        assertEquals("urn", new LazyUri(new Precoded("urn:isbn:3827370191")).scheme().value().toString());
        assertEquals("file", new LazyUri(new Precoded("file:///test.txt")).scheme().value().toString());
        assertEquals("mailto", new LazyUri(new Precoded("mailto:test@example.com")).scheme().value().toString());
        assertFalse(new LazyUri(new Precoded("//example.com")).scheme().isPresent());
        assertFalse(new LazyUri(new Precoded("/absolute/path")).scheme().isPresent());
        assertFalse(new LazyUri(new Precoded("relative/path")).scheme().isPresent());
        assertFalse(new LazyUri(new Precoded("?q=:query:")).scheme().isPresent());
    }


    @Test
    public void authority() throws Exception
    {
        assertEquals("example.com", new LazyUri(new Precoded("http://example.com")).authority().value().host().toString());
        assertEquals("example.com", new LazyUri(new Precoded("http://example.com/path")).authority().value().host().toString());
        assertEquals("example.com", new LazyUri(new Precoded("http://example.com:123")).authority().value().host().toString());
        assertEquals("example.com", new LazyUri(new Precoded("http://example.com:123/path")).authority().value().host().toString());
        assertEquals("example.com", new LazyUri(new Precoded("//example.com")).authority().value().host().toString());
        assertEquals("", new LazyUri(new Precoded("file:///test.txt")).authority().value().host().toString());
        assertFalse(new LazyUri(new Precoded("mailto:test@example.com")).authority().isPresent());
        assertFalse(new LazyUri(new Precoded("urn:isbn:3827370191")).authority().isPresent());
    }


    @Test
    public void testPath() throws Exception
    {
        assertTrue(new LazyUri(new Precoded("http://www.example.com")).path().isEmpty());
        assertTrue(new LazyUri(new Precoded("http://www.example.com?q=test")).path().isEmpty());
        assertTrue(new LazyUri(new Precoded("http://www.example.com#fragment")).path().isEmpty());
        assertTrue(new LazyUri(new Precoded("http://www.example.com:123")).path().isEmpty());
        assertTrue(new LazyUri(new Precoded("http://www.example.com:123?q=test")).path().isEmpty());
        assertTrue(new LazyUri(new Precoded("http://www.example.com:123#fragment")).path().isEmpty());
        assertEquals("/", new Text(new LazyUri(new Precoded("http://www.example.com/")).path()).toString());
        assertEquals("/test/123", new Text(new LazyUri(new Precoded("http://www.example.com/test/123")).path()).toString());
        assertEquals("/", new Text(new LazyUri(new Precoded("http://www.example.com/?q=test")).path()).toString());
        assertEquals("/test/123", new Text(new LazyUri(new Precoded("http://www.example.com/test/123?q=test")).path()).toString());
        assertEquals("test@example.com", new Text(new LazyUri(new Precoded("mailto:test@example.com")).path()).toString());
        assertEquals("/test/123", new Text(new LazyUri(new Precoded("file:///test/123")).path()).toString());
    }


    @Test
    public void query() throws Exception
    {
        assertFalse(new LazyUri(new Precoded("http://www.example.com")).query().isPresent());
        assertTrue(new LazyUri(new Precoded("http://www.example.com/?")).query().isPresent());
        assertTrue(new LazyUri(new Precoded("http://www.example.com/?q=test")).query().isPresent());
        assertEquals("", new LazyUri(new Precoded("http://www.example.com/?")).query().value().toString());
        assertEquals("q=test", new LazyUri(new Precoded("http://www.example.com/?q=test")).query().value().toString());
    }


    @Test
    public void fragment() throws Exception
    {
        assertFalse(new LazyUri(new Precoded("http://www.example.com")).fragment().isPresent());
        assertTrue(new LazyUri(new Precoded("http://www.example.com/#")).fragment().isPresent());
        assertTrue(new LazyUri(new Precoded("http://www.example.com/#q=test")).fragment().isPresent());
        assertEquals("", new LazyUri(new Precoded("http://www.example.com/#")).fragment().value().toString());
        assertEquals("q=test", new LazyUri(new Precoded("http://www.example.com/#q=test")).fragment().value().toString());

    }


    @Test
    public void isHierarchical() throws Exception
    {
        assertFalse(new LazyUri(new Precoded("mailto:test@example.com")).isHierarchical());
        assertTrue(new LazyUri(new Precoded("http://www.example.com/")).isHierarchical());
    }


    @Test
    public void isAbsolute() throws Exception
    {
        assertFalse(new LazyUri(new Precoded("/some/path")).isAbsolute());
        assertTrue(new LazyUri(new Precoded("http://www.example.com/")).isAbsolute());
    }
}