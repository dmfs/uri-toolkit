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

import org.dmfs.rfc3986.encoding.Precoded;
import org.junit.Test;

import java.util.NoSuchElementException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


/**
 * @author Marten Gajda
 */
public class OptionalLazySchemeTest
{
    @Test
    public void isPresent() throws Exception
    {
        assertTrue(new OptionalLazyScheme(new Precoded("http://example.com")).isPresent());
        assertTrue(new OptionalLazyScheme(new Precoded("http://example.com:123")).isPresent());
        assertTrue(new OptionalLazyScheme(new Precoded("mailto:test@exampe.com")).isPresent());
        assertTrue(new OptionalLazyScheme(new Precoded("example.com:123")).isPresent());
        assertFalse(new OptionalLazyScheme(new Precoded("//example.com:123")).isPresent());
        assertFalse(new OptionalLazyScheme(new Precoded("")).isPresent());
        assertFalse(new OptionalLazyScheme(new Precoded(":")).isPresent());
        assertFalse(new OptionalLazyScheme(new Precoded("123abc:")).isPresent());
        assertFalse(new OptionalLazyScheme(new Precoded("test@example.com:123")).isPresent());
    }


    @Test
    public void value() throws Exception
    {
        assertEquals("http", new OptionalLazyScheme(new Precoded("http://example.com")).value().toString());
        assertEquals("http", new OptionalLazyScheme(new Precoded("http://example.com:123")).value().toString());
        assertEquals("mailto", new OptionalLazyScheme(new Precoded("mailto:test@exampe.com")).value().toString());
        assertEquals("example.com", new OptionalLazyScheme(new Precoded("example.com:123")).value().toString());
    }


    @Test(expected = NoSuchElementException.class)
    public void valueFail1() throws Exception
    {
        new OptionalLazyScheme(new Precoded("//example.com:123")).value();
    }


    @Test(expected = NoSuchElementException.class)
    public void valueFail2() throws Exception
    {
        new OptionalLazyScheme(new Precoded("")).value();
    }


    @Test(expected = NoSuchElementException.class)
    public void valueFail3() throws Exception
    {
        new OptionalLazyScheme(new Precoded("test@example.com:123")).value();
    }


    @Test
    public void valueWithDefault() throws Exception
    {
        assertEquals("http", new OptionalLazyScheme(new Precoded("http://example.com")).value(new StringScheme("default")).toString());
        assertEquals("http", new OptionalLazyScheme(new Precoded("http://example.com:123")).value(new StringScheme("default")).toString());
        assertEquals("mailto", new OptionalLazyScheme(new Precoded("mailto:test@exampe.com")).value(new StringScheme("default")).toString());
        assertEquals("example.com", new OptionalLazyScheme(new Precoded("example.com:123")).value(new StringScheme("default")).toString());
        assertEquals("default", new OptionalLazyScheme(new Precoded("//example.com:123")).value(new StringScheme("default")).toString());
        assertEquals("default", new OptionalLazyScheme(new Precoded("")).value(new StringScheme("default")).toString());
        assertEquals("default", new OptionalLazyScheme(new Precoded("test@example.com:123")).value(new StringScheme("default")).toString());
    }

}