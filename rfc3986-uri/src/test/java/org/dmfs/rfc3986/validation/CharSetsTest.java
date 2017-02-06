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

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


/**
 * @author Marten Gajda
 */
public class CharSetsTest
{

    @Test
    public void testALPHA() throws Exception
    {
        String unreserved = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

        for (int i = 0; i < 512; ++i)
        {
            if (unreserved.indexOf(i) >= 0)
            {
                assertTrue(CharSets.ALPHA.contains((char) i));
            }
            else
            {
                assertFalse(CharSets.ALPHA.contains((char) i));
            }
        }
    }


    @Test
    public void testDIGIT() throws Exception
    {
        String unreserved = "0123456789";

        for (int i = 0; i < 512; ++i)
        {
            if (unreserved.indexOf(i) >= 0)
            {
                assertTrue(CharSets.DIGIT.contains((char) i));
            }
            else
            {
                assertFalse(CharSets.DIGIT.contains((char) i));
            }
        }
    }


    @Test
    public void testUNRESERVED() throws Exception
    {
        String unreserved = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ.-_~";

        for (int i = 0; i < 512; ++i)
        {
            if (unreserved.indexOf(i) >= 0)
            {
                assertTrue(CharSets.UNRESERVED.contains((char) i));
            }
            else
            {
                assertFalse(CharSets.UNRESERVED.contains((char) i));
            }
        }
    }


    @Test
    public void testSCHEME_CHAR() throws Exception
    {
        String scheme = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ.+-";

        for (int i = 0; i < 512; ++i)
        {
            if (scheme.indexOf(i) >= 0)
            {
                assertTrue(CharSets.SCHEME_CHAR.contains((char) i));
            }
            else
            {
                assertFalse(CharSets.SCHEME_CHAR.contains((char) i));
            }
        }
    }


    @Test
    public void testPCT_ENCODED() throws Exception
    {
        String pctEncoded = "%0123456789abcdefABCDEF";

        for (int i = 0; i < 512; ++i)
        {
            if (pctEncoded.indexOf(i) >= 0)
            {
                assertTrue(CharSets.PCT_ENCODED.contains((char) i));
            }
            else
            {
                assertFalse(CharSets.PCT_ENCODED.contains((char) i));
            }
        }
    }


    @Test
    public void testGEN_DELIMS() throws Exception
    {
        String genDelims = ":/?#[]@";

        for (int i = 0; i < 512; ++i)
        {
            if (genDelims.indexOf(i) >= 0)
            {
                assertTrue(CharSets.GEN_DELIMS.contains((char) i));
            }
            else
            {
                assertFalse(CharSets.GEN_DELIMS.contains((char) i));
            }
        }
    }


    @Test
    public void testSUB_DELIMS() throws Exception
    {
        String subDelims = "!$&'()*+,;=";

        for (int i = 0; i < 512; ++i)
        {
            if (subDelims.indexOf(i) >= 0)
            {
                assertTrue(CharSets.SUB_DELIMS.contains((char) i));
            }
            else
            {
                assertFalse(CharSets.SUB_DELIMS.contains((char) i));
            }
        }
    }


    @Test
    public void testREG_NAME_CHAR() throws Exception
    {
        String unreserved = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ.-_~";
        String pctEncoded = "%0123456789abcdefABCDEF";
        String subDelims = "!$&'()*+,;=";

        String regNameChars = unreserved + pctEncoded + subDelims;

        for (int i = 0; i < 512; ++i)
        {
            if (regNameChars.indexOf(i) >= 0)
            {
                assertTrue(CharSets.REG_NAME_CHAR.contains((char) i));
            }
            else
            {
                assertFalse(CharSets.REG_NAME_CHAR.contains((char) i));
            }
        }
    }


    @Test
    public void testPCHAR() throws Exception
    {
        String unreserved = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ.-_~";
        String pctEncoded = "%0123456789abcdefABCDEF";
        String subDelims = "!$&'()*+,;=";

        String pchar = unreserved + pctEncoded + subDelims + ":@";

        for (int i = 0; i < 512; ++i)
        {
            if (pchar.indexOf(i) >= 0)
            {
                assertTrue(CharSets.PCHAR.contains((char) i));
            }
            else
            {
                assertFalse(CharSets.PCHAR.contains((char) i));
            }
        }
    }


    @Test
    public void testQUERY_CHAR() throws Exception
    {
        String unreserved = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ.-_~";
        String pctEncoded = "%0123456789abcdefABCDEF";
        String subDelims = "!$&'()*+,;=";
        String pchar = unreserved + pctEncoded + subDelims + ":@";

        String queryChar = pchar + "/?";

        for (int i = 0; i < 512; ++i)
        {
            if (queryChar.indexOf(i) >= 0)
            {
                assertTrue(CharSets.QUERY_CHAR.contains((char) i));
            }
            else
            {
                assertFalse(CharSets.QUERY_CHAR.contains((char) i));
            }
        }
    }


    @Test
    public void testFRAGMENT_CHAR() throws Exception
    {
        String unreserved = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ.-_~";
        String pctEncoded = "%0123456789abcdefABCDEF";
        String subDelims = "!$&'()*+,;=";
        String pchar = unreserved + pctEncoded + subDelims + ":@";

        String fragmentChar = pchar + "/?";

        for (int i = 0; i < 512; ++i)
        {
            if (fragmentChar.indexOf(i) >= 0)
            {
                assertTrue(CharSets.FRAGMENT_CHAR.contains((char) i));
            }
            else
            {
                assertFalse(CharSets.FRAGMENT_CHAR.contains((char) i));
            }
        }
    }
}