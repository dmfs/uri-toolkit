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

import static org.junit.jupiter.api.Assertions.assertEquals;


/**
 * @author marten
 */
public class TextTest
{
    @Test
    public void testLength() throws Exception
    {
        assertEquals(0, new Text(new StructuredAuthority(new Precoded(""))).length());
        assertEquals(4, new Text(new StructuredAuthority(new Precoded("host"))).length());
        assertEquals(8, new Text(new StructuredAuthority(new Precoded("host"), 123)).length());
        assertEquals(9, new Text(new StructuredAuthority(new Precoded("user"), new Precoded("host"))).length());
        assertEquals(13, new Text(new StructuredAuthority(new Precoded("user"), new Precoded("host"), 123)).length());
        assertEquals(5, new Text(new StructuredAuthority(new Precoded("user"), new Precoded(""))).length());
        assertEquals(4, new Text(new StructuredAuthority(new Precoded(""), 123)).length());
        assertEquals(9, new Text(new StructuredAuthority(new Precoded("user"), new Precoded(""), 123)).length());
    }

    @Test
    public void testNormalized() throws Exception
    {
        assertEquals("user@host:123", new Text(new StructuredAuthority(new Precoded("user"), new Precoded("host"), 123)).normalized().toString());
        assertEquals("user%40host@host:123",
            new Text(new StructuredAuthority(new Precoded("%75%73%65%72%40%68%6F%73%74"), new Precoded("%68%6F%73%74"), 123)).normalized().toString());
    }
}