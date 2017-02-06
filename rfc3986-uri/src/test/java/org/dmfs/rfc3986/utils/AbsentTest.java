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

import org.junit.Test;

import java.util.NoSuchElementException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;


/**
 * @author Marten Gajda
 */
public class AbsentTest
{
    @Test
    public void testNone() throws Exception
    {
        assertNotNull(Absent.absent());
    }


    @Test
    public void testIsPresent() throws Exception
    {
        assertFalse(Absent.absent().isPresent());
        assertFalse(new Absent<>().isPresent());
    }


    @Test
    public void testValueWithDefault() throws Exception
    {
        assertEquals("default", Absent.absent().value("default"));
        assertEquals("default", new Absent<>().value("default"));
    }


    @Test(expected = NoSuchElementException.class)
    public void testValue1() throws Exception
    {
        new Absent<>().value();
    }


    @Test(expected = NoSuchElementException.class)
    public void testValue2() throws Exception
    {
        Absent.absent().value();
    }
}