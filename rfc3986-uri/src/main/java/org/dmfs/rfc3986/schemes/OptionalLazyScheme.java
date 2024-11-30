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

package org.dmfs.rfc3986.schemes;

import org.dmfs.jems2.Optional;
import org.dmfs.jems2.optional.Present;
import org.dmfs.rfc3986.Scheme;
import org.dmfs.rfc3986.UriEncoded;
import org.dmfs.rfc3986.utils.Parsed;

import java.util.NoSuchElementException;

import static org.dmfs.jems2.optional.Absent.absent;
import static org.dmfs.rfc3986.validation.CharSets.ALPHA;
import static org.dmfs.rfc3986.validation.CharSets.SCHEME_CHAR;


/**
 * The {@link Optional} {@link Scheme} of a {@link UriEncoded} URI.
 * <p>
 * Note that the presence of a {@link Scheme} in the result doesn't guarantee a valid URI. It only guarantees, if the input data represents a valid URI, this is
 * the Scheme of it.
 */
public final class OptionalLazyScheme implements Optional<Scheme>, Parsed
{
    private final UriEncoded mUriEncoded;
    private Optional<Scheme> mOptionalScheme;
    private int mEnd;


    public OptionalLazyScheme(UriEncoded uriEncoded)
    {
        mUriEncoded = uriEncoded;
    }


    @Override
    public boolean isPresent()
    {
        return scheme().isPresent();
    }

    @Override
    public Scheme value() throws NoSuchElementException
    {
        return scheme().value();
    }


    private Optional<Scheme> scheme()
    {
        if (mOptionalScheme == null)
        {
            mOptionalScheme = parsedScheme();
        }
        return mOptionalScheme;
    }


    private Optional<Scheme> parsedScheme()
    {
        final UriEncoded uriEncoded = mUriEncoded;
        final int count = uriEncoded.length();
        if (count < 2 || !ALPHA.contains(uriEncoded.charAt(0)))
        {
            // too short (to hold an ALPHA and a colon) or doesn't start with an ALPHA, not a scheme
            return absent();
        }

        int i = 1;
        while (i < count && SCHEME_CHAR.contains(uriEncoded.charAt(i)))
        {
            ++i;
        }

        if (i < 2 || i == count || uriEncoded.charAt(i) != ':')
        {
            // a scheme needs to be at least one character long and has to be terminated by a colon
            return absent();
        }
        mEnd = i + 1 /* account for the colon which is not part of the actual scheme */;
        return new Present<Scheme>(new ParsedScheme(uriEncoded.subSequence(0, i)));
    }


    @Override
    public int parsedLength()
    {
        scheme();
        return mEnd;
    }


    /**
     * A private class to hold the actual scheme. It doesn't perform any validation, so we don't make it public and use it with validated {@link CharSequence}s
     * only.
     * <p>
     * TODO: implement equals && hashCode
     */
    private final static class ParsedScheme implements Scheme
    {
        private final CharSequence mScheme;


        private ParsedScheme(CharSequence scheme)
        {
            mScheme = scheme;
        }


        @Override
        public int length()
        {
            return mScheme.length();
        }


        @Override
        public char charAt(int i)
        {
            return mScheme.charAt(i);
        }


        @Override
        public CharSequence subSequence(int i, int i1)
        {
            return mScheme.subSequence(i, i1);
        }


        @Override
        public String toString()
        {
            return mScheme.toString();
        }
    }
}
