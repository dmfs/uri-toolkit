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
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;
import static org.saynotobugs.confidence.Assertion.assertThat;
import static org.saynotobugs.confidence.core.quality.Grammar.is;
import static org.saynotobugs.confidence.core.quality.Object.throwing;


/**
 *
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


    @Test
    public void valueFail1() throws Exception
    {
        assertThat(new OptionalLazyScheme(new Precoded("//example.com:123"))::value, is(throwing(NoSuchElementException.class)));
    }


    @Test
    public void valueFail2() throws Exception
    {
        assertThat(new OptionalLazyScheme(new Precoded(""))::value, is(throwing(NoSuchElementException.class)));
    }


    @Test
    public void valueFail3() throws Exception
    {
        assertThat(new OptionalLazyScheme(new Precoded("test@example.com:123"))::value, is(throwing(NoSuchElementException.class)));
    }

}