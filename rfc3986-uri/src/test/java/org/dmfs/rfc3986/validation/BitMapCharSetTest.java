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

package org.dmfs.rfc3986.validation;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


/**
 */
public class BitMapCharSetTest
{
    @Test
    public void testContains() throws Exception
    {
        assertFalse(new BitMapCharSet().contains(' '));
        assertFalse(new BitMapCharSet(0).contains(' '));
        assertFalse(new BitMapCharSet(0, 0).contains(' '));
        assertFalse(new BitMapCharSet(0, 0, 0).contains(' '));
        assertTrue(new BitMapCharSet(0, 1).contains(' '));
        assertTrue(new BitMapCharSet(0, 1, 0).contains(' '));
        assertTrue(new BitMapCharSet(0, 0x80000000).contains((char) 63));
        assertTrue(new BitMapCharSet(0, 0x80000000, 0).contains((char) 63));
        assertFalse(new BitMapCharSet(0, 0x80000000, 0).contains((char) 62));
        assertFalse(new BitMapCharSet(0, 0x80000000, 0).contains((char) 64));
    }

}