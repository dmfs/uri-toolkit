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

package org.dmfs.rfc3986.paths;

import org.dmfs.rfc3986.encoding.Precoded;
import org.junit.Test;

import static org.junit.Assert.assertEquals;


/**
 * @author Marten Gajda
 */
public class TextTest
{
    @Test
    public void testLength() throws Exception
    {
        assertEquals(0, new Text(new EncodedPath(new Precoded(""))).length());
        assertEquals(1, new Text(new EncodedPath(new Precoded("/"))).length());
        assertEquals(3, new Text(new EncodedPath(new Precoded("abc"))).length());
        assertEquals(7, new Text(new EncodedPath(new Precoded("abc/def"))).length());
        assertEquals(12, new Text(new EncodedPath(new Precoded("../abc/./def"))).length());
    }


    @Test
    public void testCharAt() throws Exception
    {
        assertEquals('/', new Text(new EncodedPath(new Precoded("/"))).charAt(0));
        assertEquals('a', new Text(new EncodedPath(new Precoded("abc"))).charAt(0));
        assertEquals('b', new Text(new EncodedPath(new Precoded("abc"))).charAt(1));
        assertEquals('c', new Text(new EncodedPath(new Precoded("abc"))).charAt(2));
        assertEquals('a', new Text(new EncodedPath(new Precoded("abc/def"))).charAt(0));
        assertEquals('/', new Text(new EncodedPath(new Precoded("abc/def"))).charAt(3));
        assertEquals('f', new Text(new EncodedPath(new Precoded("abc/def"))).charAt(6));
        assertEquals('.', new Text(new EncodedPath(new Precoded("../abc/./def"))).charAt(0));
        assertEquals('/', new Text(new EncodedPath(new Precoded("../abc/./def"))).charAt(6));
        assertEquals('f', new Text(new EncodedPath(new Precoded("../abc/./def"))).charAt(11));
    }


    @Test
    public void testSubSequence() throws Exception
    {
        assertEquals("", new Text(new EncodedPath(new Precoded(""))).subSequence(0, 0).toString());
        assertEquals("/", new Text(new EncodedPath(new Precoded("/"))).subSequence(0, 1).toString());
        assertEquals("b", new Text(new EncodedPath(new Precoded("abc"))).subSequence(1, 2).toString());
        assertEquals("c/d", new Text(new EncodedPath(new Precoded("abc/def"))).subSequence(2, 5).toString());
        assertEquals("bc/./d", new Text(new EncodedPath(new Precoded("../abc/./def"))).subSequence(4, 10).toString());
    }


    @Test
    public void testToString() throws Exception
    {
        assertEquals("", new Text(new EncodedPath(new Precoded(""))).toString());
        assertEquals("/", new Text(new EncodedPath(new Precoded("/"))).toString());
        assertEquals("abc", new Text(new EncodedPath(new Precoded("abc"))).toString());
        assertEquals("abc/def", new Text(new EncodedPath(new Precoded("abc/def"))).toString());
        assertEquals("../abc/./def", new Text(new EncodedPath(new Precoded("../abc/./def"))).toString());
    }
}