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

package org.dmfs.rfc3986.encoding;

import org.dmfs.rfc3986.encoding.Normalized;
import org.dmfs.rfc3986.encoding.Precoded;
import org.junit.Test;

import static org.junit.Assert.assertEquals;


/**
 * @author marten
 */
public class NormalizedTest
{
    @Test
    public void testToString() throws Exception
    {
        assertEquals("", new Normalized(new Precoded("")).normalized().toString());
        assertEquals("0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ",
                new Precoded("0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ").normalized().toString());
        assertEquals(".-_~", new Normalized(new Precoded(".-_~")).normalized().toString());
        assertEquals("%25%2F%22%26%3D%C3%9C%C3%96%C3%84a%C2%B9%C2%B2%C2%B3%C2%BC%C2%BD%C2%AC%40" +
                        "%C3%A6%C5%BF%C3%B0%C4%91%C5%8B%C4%A7%C5%82%C5%82%E2%82%AC%C2%B6%C5" +
                        "%A7%E2%86%90%E2%86%93%E2%86%92%C3%B8%C3%BE%C2%AB%C2%A2%E2%80%9E%E2" +
                        "%80%9C%E2%80%9D%C2%B5%E2%80%A6%C2%B7%C3%A2%C3%B4%C3%AA%C3%A0%C3%A8%C3%B2%C3%B9%C3%A2%20+",
                new Normalized(new Precoded("%25%2F%22%26%3D%C3%9C%C3%96%C3%84a%C2%B9%C2%B2%C2%B3%C2%BC%C2%BD%C2%AC%40" +
                        "%C3%A6%C5%BF%C3%B0%C4%91%C5%8B%C4%A7%C5%82%C5%82%E2%82%AC%C2%B6%C5" +
                        "%A7%E2%86%90%E2%86%93%E2%86%92%C3%B8%C3%BE%C2%AB%C2%A2%E2%80%9E%E2" +
                        "%80%9C%E2%80%9D%C2%B5%E2%80%A6%C2%B7%C3%A2%C3%B4%C3%AA%C3%A0%C3%A8%C3%B2%C3%B9%C3%A2%20+")).toString());
        assertEquals("%25%2F%22%26%3D%C3%9C%C3%96%C3%84a%C2%B9%C2%B2%C2%B3%C2%BC%C2%BD%C2%AC%40" +
                        "%C3%A6%C5%BF%C3%B0%C4%91%C5%8B%C4%A7%C5%82%C5%82%E2%82%AC%C2%B6%C5" +
                        "%A7%E2%86%90%E2%86%93%E2%86%92%C3%B8%C3%BE%C2%AB%C2%A2%E2%80%9E%E2" +
                        "%80%9C%E2%80%9D%C2%B5%E2%80%A6%C2%B7%C3%A2%C3%B4%C3%AA%C3%A0%C3%A8%C3%B2%C3%B9%C3%A2%20+ab0",
                new Normalized(new Precoded("%25%2f%22%26%3D%c3%9C%C3%96%C3%84a%C2%B9%C2%B2%C2%B3%C2%BC%C2%BD%C2%AC%40" +
                        "%C3%A6%C5%BF%C3%B0%C4%91%C5%8B%C4%A7%C5%82%C5%82%E2%82%ac%C2%B6%C5" +
                        "%A7%E2%86%90%E2%86%93%E2%86%92%C3%B8%C3%BE%C2%AB%C2%A2%E2%80%9E%E2" +
                        "%80%9C%E2%80%9D%C2%B5%E2%80%A6%C2%B7%C3%A2%C3%B4%C3%AA%c3%A0%C3%A8%C3%b2%C3%b9%c3%a2%20+%61%62%30")).toString());
    }

}