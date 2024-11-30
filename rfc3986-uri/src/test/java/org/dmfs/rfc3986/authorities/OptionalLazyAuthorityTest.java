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

package org.dmfs.rfc3986.authorities;

import org.dmfs.rfc3986.encoding.Precoded;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


/**
 */
public class OptionalLazyAuthorityTest
{
    @Test
    public void testIsPresent() throws Exception
    {
        assertTrue(new OptionalLazyAuthority(new Precoded("//")).isPresent());
        assertTrue(new OptionalLazyAuthority(new Precoded("//test")).isPresent());
        assertTrue(new OptionalLazyAuthority(new Precoded("//test:123")).isPresent());
        assertTrue(new OptionalLazyAuthority(new Precoded("//test@example.com")).isPresent());
        assertTrue(new OptionalLazyAuthority(new Precoded("//test@example.com:123")).isPresent());
        assertTrue(new OptionalLazyAuthority(new Precoded("///")).isPresent());
        assertTrue(new OptionalLazyAuthority(new Precoded("//test/path")).isPresent());
        assertTrue(new OptionalLazyAuthority(new Precoded("//test:123/path")).isPresent());
        assertTrue(new OptionalLazyAuthority(new Precoded("//test@example.com/path")).isPresent());
        assertTrue(new OptionalLazyAuthority(new Precoded("//test@example.com:123/path")).isPresent());
        assertFalse(new OptionalLazyAuthority(new Precoded("")).isPresent());
        assertFalse(new OptionalLazyAuthority(new Precoded("/")).isPresent());
        assertFalse(new OptionalLazyAuthority(new Precoded("test")).isPresent());
        assertFalse(new OptionalLazyAuthority(new Precoded("about:")).isPresent());
    }


    @Test
    public void testValue() throws Exception
    {
        // check host

        assertEquals("", new OptionalLazyAuthority(new Precoded("//")).value().host().toString());
        assertEquals("test", new OptionalLazyAuthority(new Precoded("//test")).value().host().toString());
        assertEquals("test", new OptionalLazyAuthority(new Precoded("//test:123")).value().host().toString());
        assertEquals("example.com", new OptionalLazyAuthority(new Precoded("//test@example.com")).value().host().toString());
        assertEquals("example.com", new OptionalLazyAuthority(new Precoded("//test@example.com:123")).value().host().toString());

        assertEquals("", new OptionalLazyAuthority(new Precoded("///path")).value().host().toString());
        assertEquals("test", new OptionalLazyAuthority(new Precoded("//test/path")).value().host().toString());
        assertEquals("test", new OptionalLazyAuthority(new Precoded("//test:123/path")).value().host().toString());
        assertEquals("example.com", new OptionalLazyAuthority(new Precoded("//test@example.com/path")).value().host().toString());
        assertEquals("example.com", new OptionalLazyAuthority(new Precoded("//test@example.com:123/path")).value().host().toString());

        // check user info

        assertFalse(new OptionalLazyAuthority(new Precoded("//")).value().userInfo().isPresent());
        assertFalse(new OptionalLazyAuthority(new Precoded("//test")).value().userInfo().isPresent());
        assertFalse(new OptionalLazyAuthority(new Precoded("//test:123")).value().userInfo().isPresent());
        assertTrue(new OptionalLazyAuthority(new Precoded("//test@example.com")).value().userInfo().isPresent());
        assertEquals("test", new OptionalLazyAuthority(new Precoded("//test@example.com")).value().userInfo().value().toString());
        assertEquals("te:st", new OptionalLazyAuthority(new Precoded("//te:st@example.com")).value().userInfo().value().toString());
        assertTrue(new OptionalLazyAuthority(new Precoded("//test@example.com:123")).value().userInfo().isPresent());
        assertEquals("test", new OptionalLazyAuthority(new Precoded("//test@example.com:123")).value().userInfo().value().toString());
        assertEquals("te:st", new OptionalLazyAuthority(new Precoded("//te:st@example.com:123")).value().userInfo().value().toString());

        assertFalse(new OptionalLazyAuthority(new Precoded("///path")).value().userInfo().isPresent());
        assertFalse(new OptionalLazyAuthority(new Precoded("//test/path")).value().userInfo().isPresent());
        assertFalse(new OptionalLazyAuthority(new Precoded("//test:123/path")).value().userInfo().isPresent());
        assertTrue(new OptionalLazyAuthority(new Precoded("//test@example.com/path")).value().userInfo().isPresent());
        assertEquals("test", new OptionalLazyAuthority(new Precoded("//test@example.com/path")).value().userInfo().value().toString());
        assertEquals("te:st", new OptionalLazyAuthority(new Precoded("//te:st@example.com/path")).value().userInfo().value().toString());
        assertTrue(new OptionalLazyAuthority(new Precoded("//test@example.com:123/path")).value().userInfo().isPresent());
        assertEquals("test", new OptionalLazyAuthority(new Precoded("//test@example.com:123/path")).value().userInfo().value().toString());
        assertEquals("te:st", new OptionalLazyAuthority(new Precoded("//te:st@example.com:123/path")).value().userInfo().value().toString());

        // check port

        assertFalse(new OptionalLazyAuthority(new Precoded("//")).value().port().isPresent());
        assertFalse(new OptionalLazyAuthority(new Precoded("//test")).value().port().isPresent());
        assertEquals(123, (int) new OptionalLazyAuthority(new Precoded("//test:123")).value().port().value());
        assertFalse(new OptionalLazyAuthority(new Precoded("//test@example.com")).value().port().isPresent());
        assertFalse(new OptionalLazyAuthority(new Precoded("//test@example.com")).value().port().isPresent());
        assertFalse(new OptionalLazyAuthority(new Precoded("//te:st@example.com")).value().port().isPresent());
        assertEquals(123, (int) new OptionalLazyAuthority(new Precoded("//test@example.com:123")).value().port().value());
        assertEquals(123, (int) new OptionalLazyAuthority(new Precoded("//test@example.com:123")).value().port().value());
        assertEquals(123, (int) new OptionalLazyAuthority(new Precoded("//te:st@example.com:123")).value().port().value());

        assertFalse(new OptionalLazyAuthority(new Precoded("///path")).value().port().isPresent());
        assertFalse(new OptionalLazyAuthority(new Precoded("//test/path")).value().port().isPresent());
        assertEquals(123, (int) new OptionalLazyAuthority(new Precoded("//test:123/path")).value().port().value());
        assertFalse(new OptionalLazyAuthority(new Precoded("//test@example.com/path")).value().port().isPresent());
        assertFalse(new OptionalLazyAuthority(new Precoded("//test@example.com/path")).value().port().isPresent());
        assertFalse(new OptionalLazyAuthority(new Precoded("//te:st@example.com/path")).value().port().isPresent());
        assertEquals(123, (int) new OptionalLazyAuthority(new Precoded("//test@example.com:123/path")).value().port().value());
        assertEquals(123, (int) new OptionalLazyAuthority(new Precoded("//test@example.com:123/path")).value().port().value());
        assertEquals(123, (int) new OptionalLazyAuthority(new Precoded("//te:st@example.com:123/path")).value().port().value());
    }
}