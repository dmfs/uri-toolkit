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
public class ResolvedTest
{
    @Test
    public void testIsEmpty() throws Exception
    {
        assertTrue(new Resolved(EmptyPath.INSTANCE, EmptyPath.INSTANCE).isEmpty());
        assertFalse(new Resolved(EmptyPath.INSTANCE, new EncodedPath(new Precoded("abc/def"))).isEmpty());
        assertFalse(new Resolved(new EncodedPath(new Precoded("abc/def")), EmptyPath.INSTANCE).isEmpty());
        assertFalse(new Resolved(new EncodedPath(new Precoded("abc/def")), new EncodedPath(new Precoded("123"))).isEmpty());
        assertFalse(new Resolved(EmptyPath.INSTANCE, new EncodedPath(new Precoded("/abc"))).isEmpty());
        assertFalse(new Resolved(new EncodedPath(new Precoded("/abc")), EmptyPath.INSTANCE).isEmpty());
        assertFalse(new Resolved(new EncodedPath(new Precoded("/abc")), new EncodedPath(new Precoded("/def"))).isEmpty());
        assertFalse(new Resolved(new EncodedPath(new Precoded("abc")), new EncodedPath(new Precoded("/def"))).isEmpty());
        assertFalse(new Resolved(new EncodedPath(new Precoded("/abc")), new EncodedPath(new Precoded("def"))).isEmpty());
    }


    @Test
    public void testIsAbsolute() throws Exception
    {
        assertFalse(new Resolved(EmptyPath.INSTANCE, EmptyPath.INSTANCE).isAbsolute());
        assertFalse(new Resolved(EmptyPath.INSTANCE, new EncodedPath(new Precoded("abc/def"))).isAbsolute());
        assertFalse(new Resolved(new EncodedPath(new Precoded("abc/def")), EmptyPath.INSTANCE).isAbsolute());
        assertFalse(new Resolved(new EncodedPath(new Precoded("abc/def")), new EncodedPath(new Precoded("123"))).isAbsolute());
        assertTrue(new Resolved(EmptyPath.INSTANCE, new EncodedPath(new Precoded("/abc"))).isAbsolute());
        assertTrue(new Resolved(new EncodedPath(new Precoded("/abc")), EmptyPath.INSTANCE).isAbsolute());
        assertTrue(new Resolved(new EncodedPath(new Precoded("/abc")), new EncodedPath(new Precoded("/def"))).isAbsolute());
        assertTrue(new Resolved(new EncodedPath(new Precoded("abc")), new EncodedPath(new Precoded("/def"))).isAbsolute());
        assertTrue(new Resolved(new EncodedPath(new Precoded("/abc")), new EncodedPath(new Precoded("def"))).isAbsolute());
    }


    @Test
    public void testIterator() throws Exception
    {
        assertThat(new Resolved(EmptyPath.INSTANCE, EmptyPath.INSTANCE), new IsEmptyIterable<UriEncoded>());
        assertThat(new Resolved(EmptyPath.INSTANCE, new EncodedPath(new Precoded("/"))),
                Matchers.contains((UriEncoded) IdempotentEncoded.EMPTY, IdempotentEncoded.EMPTY));
        assertThat(new Resolved(new EncodedPath(new Precoded("/")), EmptyPath.INSTANCE),
                Matchers.contains((UriEncoded) IdempotentEncoded.EMPTY, IdempotentEncoded.EMPTY));

        assertThat(new Resolved(new EncodedPath(new Precoded("b")), new EncodedPath(new Precoded("g"))), Matchers.contains((UriEncoded) new Encoded("g")));
        assertThat(new Resolved(new EncodedPath(new Precoded("b")), new EncodedPath(new Precoded("./g"))), Matchers.contains((UriEncoded) new Encoded("g")));
        assertThat(new Resolved(new EncodedPath(new Precoded("b")), new EncodedPath(new Precoded("../g"))),
                Matchers.contains((UriEncoded) IdempotentEncoded.PARENT, new Encoded("g")));
        assertThat(new Resolved(new EncodedPath(new Precoded("b/")), new EncodedPath(new Precoded("g"))),
                Matchers.contains((UriEncoded) new Encoded("b"), new Encoded("g")));
        assertThat(new Resolved(new EncodedPath(new Precoded("b/")), new EncodedPath(new Precoded("./g"))),
                Matchers.contains((UriEncoded) new Encoded("b"), new Encoded("g")));
        assertThat(new Resolved(new EncodedPath(new Precoded("b/")), new EncodedPath(new Precoded("../g"))), Matchers.contains((UriEncoded) new Encoded("g")));
        assertThat(new Resolved(new EncodedPath(new Precoded(".")), new EncodedPath(new Precoded("."))),
                Matchers.contains((UriEncoded) IdempotentEncoded.CURRENT, IdempotentEncoded.EMPTY));

        // the next set of tests is taken from the examples in RFC 3986 Section 5.4

        assertThat(new Resolved(new EncodedPath(new Precoded("/b/c/d;p")), new EncodedPath(new Precoded("g"))),
                Matchers.contains((UriEncoded) IdempotentEncoded.EMPTY, new Encoded("b"), new Encoded("c"), new Encoded("g")));
        assertThat(new Resolved(new EncodedPath(new Precoded("/b/c/d;p")), new EncodedPath(new Precoded("./g"))),
                Matchers.contains((UriEncoded) IdempotentEncoded.EMPTY, new Encoded("b"), new Encoded("c"), new Encoded("g")));
        assertThat(new Resolved(new EncodedPath(new Precoded("/b/c/d;p")), new EncodedPath(new Precoded("g/"))),
                Matchers.contains((UriEncoded) IdempotentEncoded.EMPTY, new Encoded("b"), new Encoded("c"), new Encoded("g"), IdempotentEncoded.EMPTY));
        assertThat(new Resolved(new EncodedPath(new Precoded("/b/c/d;p")), new EncodedPath(new Precoded("/g"))),
                Matchers.contains((UriEncoded) IdempotentEncoded.EMPTY, new Encoded("g")));
        assertThat(new Resolved(new EncodedPath(new Precoded("/b/c/d;p")), new EncodedPath(new Precoded(";x"))),
                Matchers.contains((UriEncoded) IdempotentEncoded.EMPTY, new Encoded("b"), new Encoded("c"), new Precoded(";x")));
        assertThat(new Resolved(new EncodedPath(new Precoded("/b/c/d;p")), new EncodedPath(new Precoded(""))),
                Matchers.contains((UriEncoded) IdempotentEncoded.EMPTY, new Encoded("b"), new Encoded("c"), new Precoded("d;p")));
        assertThat(new Resolved(new EncodedPath(new Precoded("/b/c/d;p")), new EncodedPath(new Precoded("."))),
                Matchers.contains((UriEncoded) IdempotentEncoded.EMPTY, new Encoded("b"), new Encoded("c"), IdempotentEncoded.EMPTY));
        assertThat(new Resolved(new EncodedPath(new Precoded("/b/c/d;p")), new EncodedPath(new Precoded("./"))),
                Matchers.contains((UriEncoded) IdempotentEncoded.EMPTY, new Encoded("b"), new Encoded("c"), IdempotentEncoded.EMPTY));
        assertThat(new Resolved(new EncodedPath(new Precoded("/b/c/d;p")), new EncodedPath(new Precoded(".."))),
                Matchers.contains((UriEncoded) IdempotentEncoded.EMPTY, new Encoded("b"), IdempotentEncoded.EMPTY));
        assertThat(new Resolved(new EncodedPath(new Precoded("/b/c/d;p")), new EncodedPath(new Precoded("../"))),
                Matchers.contains((UriEncoded) IdempotentEncoded.EMPTY, new Encoded("b"), IdempotentEncoded.EMPTY));
        assertThat(new Resolved(new EncodedPath(new Precoded("/b/c/d;p")), new EncodedPath(new Precoded("../g"))),
                Matchers.contains((UriEncoded) IdempotentEncoded.EMPTY, new Encoded("b"), new Encoded("g")));
        assertThat(new Resolved(new EncodedPath(new Precoded("/b/c/d;p")), new EncodedPath(new Precoded("../.."))),
                Matchers.contains((UriEncoded) IdempotentEncoded.EMPTY, IdempotentEncoded.EMPTY));
        assertThat(new Resolved(new EncodedPath(new Precoded("/b/c/d;p")), new EncodedPath(new Precoded("../../"))),
                Matchers.contains((UriEncoded) IdempotentEncoded.EMPTY, IdempotentEncoded.EMPTY));
        assertThat(new Resolved(new EncodedPath(new Precoded("/b/c/d;p")), new EncodedPath(new Precoded("../../g"))),
                Matchers.contains((UriEncoded) IdempotentEncoded.EMPTY, new Encoded("g")));

        assertThat(new Resolved(new EncodedPath(new Precoded("/b/c/d;p")), new EncodedPath(new Precoded("../../../g"))),
                Matchers.contains((UriEncoded) IdempotentEncoded.EMPTY, new Encoded("g")));
        assertThat(new Resolved(new EncodedPath(new Precoded("/b/c/d;p")), new EncodedPath(new Precoded("../../../../g"))),
                Matchers.contains((UriEncoded) IdempotentEncoded.EMPTY, new Encoded("g")));

        assertThat(new Resolved(new EncodedPath(new Precoded("/b/c/d;p")), new EncodedPath(new Precoded("/./g"))),
                Matchers.contains((UriEncoded) IdempotentEncoded.EMPTY, new Encoded("g")));
        assertThat(new Resolved(new EncodedPath(new Precoded("/b/c/d;p")), new EncodedPath(new Precoded("/../g"))),
                Matchers.contains((UriEncoded) IdempotentEncoded.EMPTY, new Encoded("g")));
        assertThat(new Resolved(new EncodedPath(new Precoded("/b/c/d;p")), new EncodedPath(new Precoded("g."))),
                Matchers.contains((UriEncoded) IdempotentEncoded.EMPTY, new Encoded("b"), new Encoded("c"), new Encoded("g.")));
        assertThat(new Resolved(new EncodedPath(new Precoded("/b/c/d;p")), new EncodedPath(new Precoded(".g"))),
                Matchers.contains((UriEncoded) IdempotentEncoded.EMPTY, new Encoded("b"), new Encoded("c"), new Encoded(".g")));
        assertThat(new Resolved(new EncodedPath(new Precoded("/b/c/d;p")), new EncodedPath(new Precoded("g.."))),
                Matchers.contains((UriEncoded) IdempotentEncoded.EMPTY, new Encoded("b"), new Encoded("c"), new Encoded("g..")));
        assertThat(new Resolved(new EncodedPath(new Precoded("/b/c/d;p")), new EncodedPath(new Precoded("..g"))),
                Matchers.contains((UriEncoded) IdempotentEncoded.EMPTY, new Encoded("b"), new Encoded("c"), new Encoded("..g")));

        assertThat(new Resolved(new EncodedPath(new Precoded("/b/c/d;p")), new EncodedPath(new Precoded("./../g"))),
                Matchers.contains((UriEncoded) IdempotentEncoded.EMPTY, new Encoded("b"), new Encoded("g")));
        assertThat(new Resolved(new EncodedPath(new Precoded("/b/c/d;p")), new EncodedPath(new Precoded("./g/."))),
                Matchers.contains((UriEncoded) IdempotentEncoded.EMPTY, new Encoded("b"), new Encoded("c"), new Encoded("g"), IdempotentEncoded.EMPTY));
        assertThat(new Resolved(new EncodedPath(new Precoded("/b/c/d;p")), new EncodedPath(new Precoded("g/./h"))),
                Matchers.contains((UriEncoded) IdempotentEncoded.EMPTY, new Encoded("b"), new Encoded("c"), new Encoded("g"), new Encoded("h")));
        assertThat(new Resolved(new EncodedPath(new Precoded("/b/c/d;p")), new EncodedPath(new Precoded("g/../h"))),
                Matchers.contains((UriEncoded) IdempotentEncoded.EMPTY, new Encoded("b"), new Encoded("c"), new Encoded("h")));
        assertThat(new Resolved(new EncodedPath(new Precoded("/b/c/d;p")), new EncodedPath(new Precoded("g;x=1/./y"))),
                Matchers.contains((UriEncoded) IdempotentEncoded.EMPTY, new Encoded("b"), new Encoded("c"), new Precoded("g;x=1"), new Encoded("y")));
        assertThat(new Resolved(new EncodedPath(new Precoded("/b/c/d;p")), new EncodedPath(new Precoded("g;x=1/../y"))),
                Matchers.contains((UriEncoded) IdempotentEncoded.EMPTY, new Encoded("b"), new Encoded("c"), new Encoded("y")));

        // the next set of tests is like the previous one but with a relative base

        assertThat(new Resolved(new EncodedPath(new Precoded("b/c/d;p")), new EncodedPath(new Precoded("g"))),
                Matchers.contains((UriEncoded) new Encoded("b"), new Encoded("c"), new Encoded("g")));
        assertThat(new Resolved(new EncodedPath(new Precoded("b/c/d;p")), new EncodedPath(new Precoded("./g"))),
                Matchers.contains((UriEncoded) new Encoded("b"), new Encoded("c"), new Encoded("g")));
        assertThat(new Resolved(new EncodedPath(new Precoded("b/c/d;p")), new EncodedPath(new Precoded("g/"))),
                Matchers.contains((UriEncoded) new Encoded("b"), new Encoded("c"), new Encoded("g"), IdempotentEncoded.EMPTY));
        assertThat(new Resolved(new EncodedPath(new Precoded("b/c/d;p")), new EncodedPath(new Precoded("/g"))),
                Matchers.contains((UriEncoded) IdempotentEncoded.EMPTY, new Encoded("g")));
        assertThat(new Resolved(new EncodedPath(new Precoded("b/c/d;p")), new EncodedPath(new Precoded(";x"))),
                Matchers.contains((UriEncoded) new Encoded("b"), new Encoded("c"), new Precoded(";x")));
        assertThat(new Resolved(new EncodedPath(new Precoded("b/c/d;p")), new EncodedPath(new Precoded(""))),
                Matchers.contains((UriEncoded) new Encoded("b"), new Encoded("c"), new Precoded("d;p")));
        assertThat(new Resolved(new EncodedPath(new Precoded("b/c/d;p")), new EncodedPath(new Precoded("."))),
                Matchers.contains((UriEncoded) new Encoded("b"), new Encoded("c"), IdempotentEncoded.EMPTY));
        assertThat(new Resolved(new EncodedPath(new Precoded("b/c/d;p")), new EncodedPath(new Precoded("./"))),
                Matchers.contains((UriEncoded) new Encoded("b"), new Encoded("c"), IdempotentEncoded.EMPTY));
        assertThat(new Resolved(new EncodedPath(new Precoded("b/c/d;p")), new EncodedPath(new Precoded(".."))),
                Matchers.contains((UriEncoded) new Encoded("b"), IdempotentEncoded.EMPTY));
        assertThat(new Resolved(new EncodedPath(new Precoded("b/c/d;p")), new EncodedPath(new Precoded("../"))),
                Matchers.contains((UriEncoded) new Encoded("b"), IdempotentEncoded.EMPTY));
        assertThat(new Resolved(new EncodedPath(new Precoded("b/c/d;p")), new EncodedPath(new Precoded("../g"))),
                Matchers.contains((UriEncoded) new Encoded("b"), new Encoded("g")));
        assertThat(new Resolved(new EncodedPath(new Precoded("b/c/d;p")), new EncodedPath(new Precoded("../.."))),
                Matchers.contains((UriEncoded) IdempotentEncoded.CURRENT, IdempotentEncoded.EMPTY));
        assertThat(new Resolved(new EncodedPath(new Precoded("b/c/d;p")), new EncodedPath(new Precoded("../../"))),
                Matchers.contains((UriEncoded) IdempotentEncoded.CURRENT, IdempotentEncoded.EMPTY));
        assertThat(new Resolved(new EncodedPath(new Precoded("b/c/d;p")), new EncodedPath(new Precoded("../../g"))),
                Matchers.contains((UriEncoded) new Encoded("g")));

        assertThat(new Resolved(new EncodedPath(new Precoded("b/c/d;p")), new EncodedPath(new Precoded("../../../g"))),
                Matchers.contains((UriEncoded) IdempotentEncoded.PARENT, new Encoded("g")));
        assertThat(new Resolved(new EncodedPath(new Precoded("b/c/d;p")), new EncodedPath(new Precoded("../../../../g"))),
                Matchers.contains((UriEncoded) IdempotentEncoded.PARENT, IdempotentEncoded.PARENT, new Encoded("g")));

        assertThat(new Resolved(new EncodedPath(new Precoded("b/c/d;p")), new EncodedPath(new Precoded("/./g"))),
                Matchers.contains((UriEncoded) IdempotentEncoded.EMPTY, new Encoded("g")));
        assertThat(new Resolved(new EncodedPath(new Precoded("b/c/d;p")), new EncodedPath(new Precoded("/../g"))),
                Matchers.contains((UriEncoded) IdempotentEncoded.EMPTY, new Encoded("g")));
        assertThat(new Resolved(new EncodedPath(new Precoded("b/c/d;p")), new EncodedPath(new Precoded("g."))),
                Matchers.contains((UriEncoded) new Encoded("b"), new Encoded("c"), new Encoded("g.")));
        assertThat(new Resolved(new EncodedPath(new Precoded("b/c/d;p")), new EncodedPath(new Precoded(".g"))),
                Matchers.contains((UriEncoded) new Encoded("b"), new Encoded("c"), new Encoded(".g")));
        assertThat(new Resolved(new EncodedPath(new Precoded("b/c/d;p")), new EncodedPath(new Precoded("g.."))),
                Matchers.contains((UriEncoded) new Encoded("b"), new Encoded("c"), new Encoded("g..")));
        assertThat(new Resolved(new EncodedPath(new Precoded("b/c/d;p")), new EncodedPath(new Precoded("..g"))),
                Matchers.contains((UriEncoded) new Encoded("b"), new Encoded("c"), new Encoded("..g")));

        assertThat(new Resolved(new EncodedPath(new Precoded("b/c/d;p")), new EncodedPath(new Precoded("./../g"))),
                Matchers.contains((UriEncoded) new Encoded("b"), new Encoded("g")));
        assertThat(new Resolved(new EncodedPath(new Precoded("b/c/d;p")), new EncodedPath(new Precoded("./g/."))),
                Matchers.contains((UriEncoded) new Encoded("b"), new Encoded("c"), new Encoded("g"), IdempotentEncoded.EMPTY));
        assertThat(new Resolved(new EncodedPath(new Precoded("b/c/d;p")), new EncodedPath(new Precoded("g/./h"))),
                Matchers.contains((UriEncoded) new Encoded("b"), new Encoded("c"), new Encoded("g"), new Encoded("h")));
        assertThat(new Resolved(new EncodedPath(new Precoded("b/c/d;p")), new EncodedPath(new Precoded("g/../h"))),
                Matchers.contains((UriEncoded) new Encoded("b"), new Encoded("c"), new Encoded("h")));
        assertThat(new Resolved(new EncodedPath(new Precoded("b/c/d;p")), new EncodedPath(new Precoded("g;x=1/./y"))),
                Matchers.contains((UriEncoded) new Encoded("b"), new Encoded("c"), new Precoded("g;x=1"), new Encoded("y")));
        assertThat(new Resolved(new EncodedPath(new Precoded("b/c/d;p")), new EncodedPath(new Precoded("g;x=1/../y"))),
                Matchers.contains((UriEncoded) new Encoded("b"), new Encoded("c"), new Encoded("y")));
    }

}