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

package org.dmfs.rfc3986;

import java.io.UnsupportedEncodingException;


/**
 * Interface of a percent-encoded {@link CharSequence}.
 * <p>
 * Note that two {@link UriEncoded} objects are considered equal if their encoded and normalized values are equal.
 * <p>
 * All implementations of this must also implement {@link #toString()} to return a {@link String} representation of the encoded value.
 *
 * @author Marten Gajda
 */
public interface UriEncoded extends CharSequence
{
    /**
     * Returns a normalized version of this {@link UriEncoded}. In particular that means any percent-encoded unreserved characters are decoded and
     * percent-encoding sequences are converted to uppercase.
     *
     * @return The normalized version of this {@link UriEncoded}.
     */
    UriEncoded normalized();

    /**
     * Returns the decoded text, using the given charset to decode the non-ASCII characters.
     *
     * @param charset
     *         The name of the charset.
     *
     * @return
     */
    CharSequence decoded(String charset) throws UnsupportedEncodingException;

    /**
     * Returns the decoded text assuming UTF-8 encoding.
     *
     * @return
     */
    CharSequence decoded();

    @Override
    UriEncoded subSequence(int startIndex, int endIndex);

    @Override
    String toString();
}
