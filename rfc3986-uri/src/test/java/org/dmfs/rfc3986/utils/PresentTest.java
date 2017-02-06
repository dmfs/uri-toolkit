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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


/**
 * @author Marten Gajda
 */
public class PresentTest
{
    @Test(expected = IllegalArgumentException.class)
    public void testConstructor()
    {
        new Present<>(null);
    }


    @Test
    public void testIsPresent() throws Exception
    {
        assertTrue(new Present<>("Value").isPresent());
    }


    @Test
    public void testValueWithDefault() throws Exception
    {
        assertEquals("Value", new Present<>("Value").value("default"));
    }


    @Test
    public void testValue() throws Exception
    {
        assertEquals("Value", new Present<>("Value").value());
    }

}