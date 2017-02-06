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
public class ExtendedTest
{
    @Test
    public void isEmpty() throws Exception
    {
        assertTrue(new Extended(EmptyPath.INSTANCE, EmptyPath.INSTANCE).isEmpty());
        assertFalse(new Extended(EmptyPath.INSTANCE, new EncodedPath(new Precoded("abc/def"))).isEmpty());
        assertFalse(new Extended(new EncodedPath(new Precoded("abc/def")), EmptyPath.INSTANCE).isEmpty());
        assertFalse(new Extended(new EncodedPath(new Precoded("abc/def")), new EncodedPath(new Precoded("123"))).isEmpty());
        assertFalse(new Extended(EmptyPath.INSTANCE, new EncodedPath(new Precoded("/abc"))).isEmpty());
        assertFalse(new Extended(new EncodedPath(new Precoded("/abc")), EmptyPath.INSTANCE).isEmpty());
        assertFalse(new Extended(new EncodedPath(new Precoded("/abc")), new EncodedPath(new Precoded("/def"))).isEmpty());
        assertFalse(new Extended(new EncodedPath(new Precoded("abc")), new EncodedPath(new Precoded("/def"))).isEmpty());
        assertFalse(new Extended(new EncodedPath(new Precoded("/abc")), new EncodedPath(new Precoded("def"))).isEmpty());
    }


    @Test
    public void isAbsolute() throws Exception
    {
        assertFalse(new Extended(EmptyPath.INSTANCE, EmptyPath.INSTANCE).isAbsolute());
        assertFalse(new Extended(EmptyPath.INSTANCE, new EncodedPath(new Precoded("abc/def"))).isAbsolute());
        assertFalse(new Extended(new EncodedPath(new Precoded("abc/def")), EmptyPath.INSTANCE).isAbsolute());
        assertFalse(new Extended(new EncodedPath(new Precoded("abc/def")), new EncodedPath(new Precoded("123"))).isAbsolute());
        assertFalse(new Extended(EmptyPath.INSTANCE, new EncodedPath(new Precoded("/abc"))).isAbsolute());
        assertFalse(new Extended(new EncodedPath(new Precoded("abc")), new EncodedPath(new Precoded("/def"))).isAbsolute());
        assertTrue(new Extended(new EncodedPath(new Precoded("/abc")), EmptyPath.INSTANCE).isAbsolute());
        assertTrue(new Extended(new EncodedPath(new Precoded("/abc")), new EncodedPath(new Precoded("/def"))).isAbsolute());
        assertTrue(new Extended(new EncodedPath(new Precoded("/abc")), new EncodedPath(new Precoded("def"))).isAbsolute());
    }


    @Test
    public void iterator() throws Exception
    {
        assertThat(new Extended(EmptyPath.INSTANCE, EmptyPath.INSTANCE), new IsEmptyIterable<UriEncoded>());
        assertThat(new Extended(EmptyPath.INSTANCE, new EncodedPath(new Precoded("/"))),
                Matchers.contains((UriEncoded) IdempotentEncoded.EMPTY, IdempotentEncoded.EMPTY));
        assertThat(new Extended(new EncodedPath(new Precoded("/")), EmptyPath.INSTANCE),
                Matchers.contains((UriEncoded) IdempotentEncoded.EMPTY, IdempotentEncoded.EMPTY));

        assertThat(new Extended(new EncodedPath(new Precoded("/")), new EncodedPath(new Precoded("c"))),
                Matchers.contains((UriEncoded) IdempotentEncoded.EMPTY, new Encoded("c")));
        assertThat(new Extended(new EncodedPath(new Precoded("/")), new EncodedPath(new Precoded("./c"))),
                Matchers.contains((UriEncoded) IdempotentEncoded.EMPTY, new Encoded("c")));
        assertThat(new Extended(new EncodedPath(new Precoded("/")), new EncodedPath(new Precoded("../c"))),
                Matchers.contains((UriEncoded) IdempotentEncoded.EMPTY, new Encoded("c")));
        assertThat(new Extended(new EncodedPath(new Precoded("a")), new EncodedPath(new Precoded("c"))),
                Matchers.contains((UriEncoded) new Encoded("a"), new Encoded("c")));
        assertThat(new Extended(new EncodedPath(new Precoded("a")), new EncodedPath(new Precoded("./c"))),
                Matchers.contains((UriEncoded) new Encoded("a"), new Encoded("c")));
        assertThat(new Extended(new EncodedPath(new Precoded("a")), new EncodedPath(new Precoded("../c"))), Matchers.contains((UriEncoded) new Encoded("c")));
        assertThat(new Extended(new EncodedPath(new Precoded("/a")), new EncodedPath(new Precoded("c"))),
                Matchers.contains((UriEncoded) IdempotentEncoded.EMPTY, new Encoded("a"), new Encoded("c")));
        assertThat(new Extended(new EncodedPath(new Precoded("/a")), new EncodedPath(new Precoded("./c"))),
                Matchers.contains((UriEncoded) IdempotentEncoded.EMPTY, new Encoded("a"), new Encoded("c")));
        assertThat(new Extended(new EncodedPath(new Precoded("/a")), new EncodedPath(new Precoded("../c"))),
                Matchers.contains((UriEncoded) IdempotentEncoded.EMPTY, new Encoded("c")));
        assertThat(new Extended(new EncodedPath(new Precoded("a/b")), new EncodedPath(new Precoded("c"))),
                Matchers.contains((UriEncoded) new Encoded("a"), new Encoded("b"), new Encoded("c")));
        assertThat(new Extended(new EncodedPath(new Precoded("a/b")), new EncodedPath(new Precoded("./c"))),
                Matchers.contains((UriEncoded) new Encoded("a"), new Encoded("b"), new Encoded("c")));
        assertThat(new Extended(new EncodedPath(new Precoded("a/b")), new EncodedPath(new Precoded("../c"))),
                Matchers.contains((UriEncoded) new Encoded("a"), new Encoded("c")));
        assertThat(new Extended(new EncodedPath(new Precoded("a/b")), new EncodedPath(new Precoded("../../."))),
                Matchers.contains((UriEncoded) IdempotentEncoded.CURRENT, IdempotentEncoded.EMPTY));
        assertThat(new Extended(new EncodedPath(new Precoded("a/b")), new EncodedPath(new Precoded("../../.."))),
                Matchers.contains((UriEncoded) IdempotentEncoded.PARENT, IdempotentEncoded.EMPTY));

        assertThat(new Extended(new StructuredPath(new Encoded("a"), new Encoded("b")), IdempotentEncoded.CURRENT, new Encoded("c")),
                Matchers.contains((UriEncoded) new Encoded("a"), new Encoded("b"), new Encoded("c")));
        assertThat(new Extended(new StructuredPath(new Encoded("a"), new Encoded("b")), IdempotentEncoded.PARENT, new Encoded("c")),
                Matchers.contains((UriEncoded) new Encoded("a"), new Encoded("c")));
    }

}