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

package org.dmfs.rfc3986.schemes;

import org.junit.Test;

import static org.junit.Assert.assertEquals;


/**
 * @author marten
 */
public class StringSchemeTest
{
    @Test
    public void testLength() throws Exception
    {
        assertEquals(4, new StringScheme("http").length());
    }


    @Test
    public void testCharAt() throws Exception
    {
        assertEquals('h', new StringScheme("http").charAt(0));
        assertEquals('t', new StringScheme("http").charAt(1));
        assertEquals('t', new StringScheme("http").charAt(2));
        assertEquals('p', new StringScheme("http").charAt(3));
    }


    @Test
    public void testSubSequence() throws Exception
    {
        assertEquals("tt", new StringScheme("http").subSequence(1, 3).toString());
    }


    @Test
    public void testToString() throws Exception
    {
        assertEquals("http", new StringScheme("http").toString());
    }


    @Test(expected = IllegalArgumentException.class)
    public void testLengthIllegalChar() throws Exception
    {
        new StringScheme("http_").length();
    }


    @Test(expected = IllegalArgumentException.class)
    public void testCharAtIllegalChar() throws Exception
    {
        new StringScheme("http_").charAt(0);
    }


    @Test(expected = IllegalArgumentException.class)
    public void testSubSequenceIllegalChar() throws Exception
    {
        new StringScheme("http_").subSequence(1, 2);
    }


    @Test(expected = IllegalArgumentException.class)
    public void testToStringIllegalChar() throws Exception
    {
        new StringScheme("http_").toString();
    }
}