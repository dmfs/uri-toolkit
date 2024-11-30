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

import org.dmfs.rfc3986.encoding.Encoded;
import org.dmfs.rfc3986.encoding.IdempotentEncoded;
import org.dmfs.rfc3986.encoding.Precoded;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.saynotobugs.confidence.Assertion.assertThat;
import static org.saynotobugs.confidence.core.quality.Iterable.emptyIterable;
import static org.saynotobugs.confidence.core.quality.Iterable.iterates;


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
        assertThat(new Normalized(EmptyPath.INSTANCE), emptyIterable());
        assertThat(new Normalized(new EncodedPath(new Precoded("."))), iterates(IdempotentEncoded.CURRENT, IdempotentEncoded.EMPTY));
        assertThat(new Normalized(new EncodedPath(new Precoded(".."))), iterates(IdempotentEncoded.PARENT, IdempotentEncoded.EMPTY));
        assertThat(new Normalized(new EncodedPath(new Precoded("/"))), iterates(new Encoded(""), new Encoded("")));
        assertThat(new Normalized(new EncodedPath(new Precoded("/a"))), iterates(IdempotentEncoded.EMPTY, new Encoded("a")));
        assertThat(new Normalized(new EncodedPath(new Precoded("/a/"))),
            iterates(IdempotentEncoded.EMPTY, new Encoded("a"), IdempotentEncoded.EMPTY));
        assertThat(new Normalized(new EncodedPath(new Precoded("/a/b"))),
            iterates(IdempotentEncoded.EMPTY, new Encoded("a"), new Encoded("b")));
        assertThat(new Normalized(new EncodedPath(new Precoded("a"))), iterates(new Encoded("a")));
        assertThat(new Normalized(new EncodedPath(new Precoded("a/"))), iterates(new Encoded("a"), IdempotentEncoded.EMPTY));
        assertThat(new Normalized(new EncodedPath(new Precoded("a/b"))), iterates(new Encoded("a"), new Encoded("b")));

        assertThat(new Normalized(new EncodedPath(new Precoded("/../a/b"))),
            iterates(IdempotentEncoded.EMPTY, new Encoded("a"), new Encoded("b")));
        assertThat(new Normalized(new EncodedPath(new Precoded("/../a/../b"))), iterates(IdempotentEncoded.EMPTY, new Encoded("b")));
        assertThat(new Normalized(new EncodedPath(new Precoded("../a/b"))),
            iterates(IdempotentEncoded.PARENT, new Encoded("a"), new Encoded("b")));
        assertThat(new Normalized(new EncodedPath(new Precoded("../a/../b"))), iterates(IdempotentEncoded.PARENT, new Encoded("b")));
        assertThat(new Normalized(new EncodedPath(new Precoded("/../../a/../b/"))),
            iterates(IdempotentEncoded.EMPTY, new Encoded("b"), IdempotentEncoded.EMPTY));
        assertThat(new Normalized(new EncodedPath(new Precoded("../../c"))),
            iterates(IdempotentEncoded.PARENT, IdempotentEncoded.PARENT, new Encoded("c")));
        assertThat(new Normalized(new EncodedPath(new Precoded("/../../c/"))),
            iterates(IdempotentEncoded.EMPTY, new Encoded("c"), IdempotentEncoded.EMPTY));
        assertThat(new Normalized(new EncodedPath(new Precoded("./a/b/./././c"))),
            iterates(new Encoded("a"), new Encoded("b"), new Encoded("c")));

        assertThat(new Normalized(new EncodedPath(new Precoded("a/../b"))), iterates(new Encoded("b")));
        assertThat(new Normalized(new EncodedPath(new Precoded("a/b/../.."))),
            iterates(IdempotentEncoded.CURRENT, IdempotentEncoded.EMPTY));
        assertThat(new Normalized(new EncodedPath(new Precoded("a/b/../../"))),
            iterates(IdempotentEncoded.CURRENT, IdempotentEncoded.EMPTY));
        assertThat(new Normalized(new EncodedPath(new Precoded("a/b/../../.."))),
            iterates(IdempotentEncoded.PARENT, IdempotentEncoded.EMPTY));
        assertThat(new Normalized(new EncodedPath(new Precoded("a/b/../../../"))),
            iterates(IdempotentEncoded.PARENT, IdempotentEncoded.EMPTY));
        assertThat(new Normalized(new EncodedPath(new Precoded("a/b/../../../c"))), iterates(IdempotentEncoded.PARENT, new Encoded("c")));
        assertThat(new Normalized(new EncodedPath(new Precoded("../a/../../b"))),
            iterates(IdempotentEncoded.PARENT, IdempotentEncoded.PARENT, new Encoded("b")));
        assertThat(new Normalized(new EncodedPath(new Precoded("./a/./../b"))), iterates(new Encoded("b")));
        assertThat(new Normalized(new EncodedPath(new Precoded("../a/../../b/../"))),
            iterates(IdempotentEncoded.PARENT, IdempotentEncoded.PARENT, IdempotentEncoded.EMPTY));
    }
}