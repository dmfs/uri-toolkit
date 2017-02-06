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

package org.dmfs.rfc3986.paths;

import org.dmfs.rfc3986.UriEncoded;
import org.dmfs.rfc3986.encoding.Encoded;
import org.dmfs.rfc3986.encoding.IdempotentEncoded;
import org.dmfs.rfc3986.encoding.Precoded;
import org.hamcrest.Matchers;
import org.hamcrest.collection.IsEmptyIterable;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;


/**
 * @author Marten Gajda
 */
public class NormalizedTest
{

    @Test
    public void testIsEmpty() throws Exception
    {
        assertTrue(new Normalized(new EncodedPath(new Precoded(""))).isEmpty());
        assertFalse(new Normalized(new EncodedPath(new Precoded("/"))).isEmpty());
    }


    @Test
    public void testIsAbsolute() throws Exception
    {
        assertFalse(new Normalized(new EncodedPath(new Precoded("abc"))).isAbsolute());
        assertTrue(new Normalized(new EncodedPath(new Precoded("/"))).isAbsolute());
    }


    @Test
    public void testIterator() throws Exception
    {
        assertThat(new Normalized(EmptyPath.INSTANCE), new IsEmptyIterable());
        assertThat(new Normalized(new EncodedPath(new Precoded("."))), Matchers.contains((UriEncoded) IdempotentEncoded.CURRENT, IdempotentEncoded.EMPTY));
        assertThat(new Normalized(new EncodedPath(new Precoded(".."))), Matchers.contains((UriEncoded) IdempotentEncoded.PARENT, IdempotentEncoded.EMPTY));
        assertThat(new Normalized(new EncodedPath(new Precoded("/"))), Matchers.contains((UriEncoded) new Encoded(""), new Encoded("")));
        assertThat(new Normalized(new EncodedPath(new Precoded("/a"))), Matchers.contains((UriEncoded) IdempotentEncoded.EMPTY, new Encoded("a")));
        assertThat(new Normalized(new EncodedPath(new Precoded("/a/"))),
                Matchers.contains((UriEncoded) IdempotentEncoded.EMPTY, new Encoded("a"), IdempotentEncoded.EMPTY));
        assertThat(new Normalized(new EncodedPath(new Precoded("/a/b"))),
                Matchers.contains((UriEncoded) IdempotentEncoded.EMPTY, new Encoded("a"), new Encoded("b")));
        assertThat(new Normalized(new EncodedPath(new Precoded("a"))), Matchers.contains((UriEncoded) new Encoded("a")));
        assertThat(new Normalized(new EncodedPath(new Precoded("a/"))), Matchers.contains((UriEncoded) new Encoded("a"), IdempotentEncoded.EMPTY));
        assertThat(new Normalized(new EncodedPath(new Precoded("a/b"))), Matchers.contains((UriEncoded) new Encoded("a"), new Encoded("b")));

        assertThat(new Normalized(new EncodedPath(new Precoded("/../a/b"))),
                Matchers.contains((UriEncoded) IdempotentEncoded.EMPTY, new Encoded("a"), new Encoded("b")));
        assertThat(new Normalized(new EncodedPath(new Precoded("/../a/../b"))), Matchers.contains((UriEncoded) IdempotentEncoded.EMPTY, new Encoded("b")));
        assertThat(new Normalized(new EncodedPath(new Precoded("../a/b"))),
                Matchers.contains((UriEncoded) IdempotentEncoded.PARENT, new Encoded("a"), new Encoded("b")));
        assertThat(new Normalized(new EncodedPath(new Precoded("../a/../b"))), Matchers.contains((UriEncoded) IdempotentEncoded.PARENT, new Encoded("b")));
        assertThat(new Normalized(new EncodedPath(new Precoded("/../../a/../b/"))),
                Matchers.contains((UriEncoded) IdempotentEncoded.EMPTY, new Encoded("b"), IdempotentEncoded.EMPTY));
        assertThat(new Normalized(new EncodedPath(new Precoded("../../c"))),
                Matchers.contains((UriEncoded) IdempotentEncoded.PARENT, IdempotentEncoded.PARENT, new Encoded("c")));
        assertThat(new Normalized(new EncodedPath(new Precoded("/../../c/"))),
                Matchers.contains((UriEncoded) IdempotentEncoded.EMPTY, new Encoded("c"), IdempotentEncoded.EMPTY));
        assertThat(new Normalized(new EncodedPath(new Precoded("./a/b/./././c"))),
                Matchers.contains((UriEncoded) new Encoded("a"), new Encoded("b"), new Encoded("c")));

        assertThat(new Normalized(new EncodedPath(new Precoded("a/../b"))), Matchers.contains((UriEncoded) new Encoded("b")));
        assertThat(new Normalized(new EncodedPath(new Precoded("a/b/../.."))),
                Matchers.contains((UriEncoded) IdempotentEncoded.CURRENT, IdempotentEncoded.EMPTY));
        assertThat(new Normalized(new EncodedPath(new Precoded("a/b/../../"))),
                Matchers.contains((UriEncoded) IdempotentEncoded.CURRENT, IdempotentEncoded.EMPTY));
        assertThat(new Normalized(new EncodedPath(new Precoded("a/b/../../.."))),
                Matchers.contains((UriEncoded) IdempotentEncoded.PARENT, IdempotentEncoded.EMPTY));
        assertThat(new Normalized(new EncodedPath(new Precoded("a/b/../../../"))),
                Matchers.contains((UriEncoded) IdempotentEncoded.PARENT, IdempotentEncoded.EMPTY));
        assertThat(new Normalized(new EncodedPath(new Precoded("a/b/../../../c"))), Matchers.contains((UriEncoded) IdempotentEncoded.PARENT, new Encoded("c")));
        assertThat(new Normalized(new EncodedPath(new Precoded("../a/../../b"))),
                Matchers.contains((UriEncoded) IdempotentEncoded.PARENT, IdempotentEncoded.PARENT, new Encoded("b")));
        assertThat(new Normalized(new EncodedPath(new Precoded("./a/./../b"))), Matchers.contains((UriEncoded) new Encoded("b")));
        assertThat(new Normalized(new EncodedPath(new Precoded("../a/../../b/../"))),
                Matchers.contains((UriEncoded) IdempotentEncoded.PARENT, IdempotentEncoded.PARENT, IdempotentEncoded.EMPTY));
    }
}