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

package org.dmfs.rfc3986.utils;

import org.junit.jupiter.api.Test;

import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;
import static org.saynotobugs.confidence.Assertion.assertThat;
import static org.saynotobugs.confidence.core.quality.Grammar.is;
import static org.saynotobugs.confidence.core.quality.Object.throwing;


public class SplitTest
{

    @Test
    public void testEmptyString()
    {
        // there is always at least one element
        Iterator<CharSequence> iterator = new Split("", ';');
        assertTrue(iterator.hasNext());
        assertEquals("", iterator.next());
        assertFalse(iterator.hasNext());
    }


    @Test
    public void testTrivialString()
    {
        Iterator<CharSequence> iterator = new Split("abcde", ';');
        assertTrue(iterator.hasNext());
        assertEquals("abcde", iterator.next());
        assertFalse(iterator.hasNext());
    }


    @Test
    public void testSimpleString()
    {
        Iterator<CharSequence> iterator = new Split("abc;de", ';');
        assertTrue(iterator.hasNext());
        assertEquals("abc", iterator.next());
        assertTrue(iterator.hasNext());
        assertEquals("de", iterator.next());
        assertFalse(iterator.hasNext());
    }


    @Test
    public void testSimpleString2()
    {
        Iterator<CharSequence> iterator = new Split("abc;de", ';');
        // don't call hasNext(), which should still yield the correct results
        assertEquals("abc", iterator.next());
        assertTrue(iterator.hasNext());
        assertEquals("de", iterator.next());
        assertFalse(iterator.hasNext());
    }


    @Test
    public void testSimpleSeparatorString()
    {
        Iterator<CharSequence> iterator = new Split(";", ';');
        assertTrue(iterator.hasNext());
        assertEquals("", iterator.next());
        assertTrue(iterator.hasNext());
        assertEquals("", iterator.next());
        assertFalse(iterator.hasNext());
    }


    @Test
    public void testSimpleSeparatorString2()
    {
        Iterator<CharSequence> iterator = new Split(";;", ';');
        assertTrue(iterator.hasNext());
        assertEquals("", iterator.next());
        assertTrue(iterator.hasNext());
        assertEquals("", iterator.next());
        assertTrue(iterator.hasNext());
        assertEquals("", iterator.next());
        assertFalse(iterator.hasNext());
    }


    @Test
    public void testSeparatorString()
    {
        Iterator<CharSequence> iterator = new Split("abcde;fghi;jkl", ';');
        assertTrue(iterator.hasNext());
        assertEquals("abcde", iterator.next());
        assertTrue(iterator.hasNext());
        assertEquals("fghi", iterator.next());
        assertTrue(iterator.hasNext());
        assertEquals("jkl", iterator.next());
        assertFalse(iterator.hasNext());
    }


    @Test
    public void testSeparatorString2()
    {
        Iterator<CharSequence> iterator = new Split(";abcde;fghi;jkl", ';');
        assertTrue(iterator.hasNext());
        assertEquals("", iterator.next());
        assertTrue(iterator.hasNext());
        assertEquals("abcde", iterator.next());
        assertTrue(iterator.hasNext());
        assertEquals("fghi", iterator.next());
        assertTrue(iterator.hasNext());
        assertEquals("jkl", iterator.next());
        assertFalse(iterator.hasNext());
    }


    @Test
    public void testSeparatorString3()
    {
        Iterator<CharSequence> iterator = new Split(";abcde;fghi;jkl;", ';');
        assertTrue(iterator.hasNext());
        assertEquals("", iterator.next());
        assertTrue(iterator.hasNext());
        assertEquals("abcde", iterator.next());
        assertTrue(iterator.hasNext());
        assertEquals("fghi", iterator.next());
        assertTrue(iterator.hasNext());
        assertEquals("jkl", iterator.next());
        assertTrue(iterator.hasNext());
        assertEquals("", iterator.next());
        assertFalse(iterator.hasNext());
    }


    @Test
    public void testSeparatorString4()
    {
        Iterator<CharSequence> iterator = new Split(";abcde;fghi;jkl;", ';');
        assertTrue(iterator.hasNext());
        assertEquals("", iterator.next());
        assertTrue(iterator.hasNext());
        assertEquals("abcde", iterator.next());
        assertTrue(iterator.hasNext());
        assertEquals("fghi", iterator.next());
        assertTrue(iterator.hasNext());
        assertEquals("jkl", iterator.next());
        assertTrue(iterator.hasNext());
        assertEquals("", iterator.next());
        assertThat(iterator::next, is(throwing(NoSuchElementException.class)));
    }


    @Test
    public void testSeparatorWithLimit()
    {
        Iterator<CharSequence> iterator = new Split(";abcde;fghi;jkl;", ';', 2);
        assertTrue(iterator.hasNext());
        assertEquals("", iterator.next());
        assertTrue(iterator.hasNext());
        assertEquals("abcde", iterator.next());
        assertTrue(iterator.hasNext());
        assertEquals("fghi;jkl;", iterator.next());
    }

}
