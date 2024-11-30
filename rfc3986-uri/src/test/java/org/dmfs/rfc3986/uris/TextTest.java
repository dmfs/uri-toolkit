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

package org.dmfs.rfc3986.uris;

import org.dmfs.rfc3986.authorities.EncodedAuthority;
import org.dmfs.rfc3986.encoding.Precoded;
import org.dmfs.rfc3986.fragments.SimpleFragment;
import org.dmfs.rfc3986.paths.EncodedPath;
import org.dmfs.rfc3986.queries.SimpleQuery;
import org.dmfs.rfc3986.schemes.StringScheme;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


/**
 */
public class TextTest
{
    @Test
    public void testToString() throws Exception
    {
        assertEquals("http://example.com/test?x=y#123", new Text(
                new StructuredUri(new StringScheme("http"), new EncodedAuthority(new Precoded("example.com")), new EncodedPath(new Precoded("/test")),
                        new SimpleQuery(new Precoded("x=y")),
                        new SimpleFragment(new Precoded("123")))).toString());
        assertEquals("http://example.com/test?x=y", new Text(
                new StructuredUri(new StringScheme("http"), new EncodedAuthority(new Precoded("example.com")), new EncodedPath(new Precoded("/test")),
                        new SimpleQuery(new Precoded("x=y")))).toString());
        assertEquals("/test?x=y", new Text(
                new RelativeUri(new EncodedPath(new Precoded("/test")), new SimpleQuery(new Precoded("x=y")))).toString());
    }

}