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

package org.dmfs.rfc3986.queries;

import org.dmfs.optional.Optional;
import org.dmfs.optional.Present;
import org.dmfs.rfc3986.Query;
import org.dmfs.rfc3986.UriEncoded;
import org.dmfs.rfc3986.utils.Parsed;

import java.util.NoSuchElementException;

import static org.dmfs.optional.Absent.absent;
import static org.dmfs.rfc3986.validation.CharSets.QUERY_CHAR;


/**
 * An {@link Optional}, lazily parsed and validated {@link Query}.
 *
 * @author Marten Gajda
 */
public final class OptionalLazyQuery implements Optional<Query>, Parsed
{
    private final UriEncoded mUriEncoded;
    private Optional<Query> mDelegate;
    private int mEnd;


    /**
     * Creates an {@link Optional} lazily evaluated {@link Query}. The given input is considered to contain a {@link Query} if it starts with a {@code "?"}
     * character. The {@code "?"} will not be part of the {@link Query}.
     * <p>
     * If the input doesn't start with a {@code "?"}, the optional {@link Query} will not be "present".
     *
     * @param uriEncoded
     */
    public OptionalLazyQuery(UriEncoded uriEncoded)
    {
        mUriEncoded = uriEncoded;
    }


    @Override
    public boolean isPresent()
    {
        return query().isPresent();
    }


    @Override
    public Query value(Query defaultValue)
    {
        return query().value(defaultValue);
    }


    @Override
    public Query value() throws NoSuchElementException
    {
        return query().value();
    }


    private Optional<Query> query()
    {
        if (mDelegate == null)
        {
            mDelegate = parsedQuery();
        }
        return mDelegate;
    }


    private Optional<Query> parsedQuery()
    {
        final UriEncoded encoded = mUriEncoded;
        final int count = encoded.length();

        if (count == 0 || encoded.charAt(0) != '?')
        {
            // not a query
            return absent();
        }

        int i = 1;
        while (i < count && QUERY_CHAR.contains(encoded.charAt(i)))
        {
            ++i;
        }

        if (i < count && encoded.charAt(i) != '#')
        {
            // the query can only be terminated by the end of the string or a fragment
            throw new IllegalArgumentException(
                    String.format("Query %s contains illegal char %c at position %d", encoded.toString(), encoded.charAt(i), i));
        }

        mEnd = i;
        return new Present<Query>(new SimpleQuery(encoded.subSequence(1 /* don't include the "?" */, i)));
    }


    @Override
    public int parsedLength()
    {
        // make sure the query has been parsed
        query();
        return mEnd;
    }
}
