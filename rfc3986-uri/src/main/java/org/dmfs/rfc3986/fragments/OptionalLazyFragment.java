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

package org.dmfs.rfc3986.fragments;

import org.dmfs.optional.Optional;
import org.dmfs.optional.Present;
import org.dmfs.rfc3986.Fragment;
import org.dmfs.rfc3986.UriEncoded;

import java.util.NoSuchElementException;

import static org.dmfs.optional.Absent.absent;
import static org.dmfs.rfc3986.validation.CharSets.FRAGMENT_CHAR;


/**
 * An {@link Optional} {@link Fragment} parsed lazily.
 *
 * @author Marten Gajda
 */
public final class OptionalLazyFragment implements Optional<Fragment>
{
    private final UriEncoded mUriEncoded;
    private Optional<Fragment> mDelegate;


    public OptionalLazyFragment(UriEncoded uriEncoded)
    {
        mUriEncoded = uriEncoded;
    }


    @Override
    public boolean isPresent()
    {
        return fragment().isPresent();
    }


    @Override
    public Fragment value(Fragment defaultValue)
    {
        return fragment().value(defaultValue);
    }


    @Override
    public Fragment value() throws NoSuchElementException
    {
        return fragment().value();
    }


    private Optional<Fragment> fragment()
    {
        if (mDelegate == null)
        {
            mDelegate = parsedFragment();
        }
        return mDelegate;
    }


    private Optional<Fragment> parsedFragment()
    {
        final UriEncoded uriEncoded = mUriEncoded;
        final int count = uriEncoded.length();
        if (count == 0 || uriEncoded.charAt(0) != '#')
        {
            // not a fragment
            return absent();
        }

        int i = 1;
        while (i < count && FRAGMENT_CHAR.contains(uriEncoded.charAt(i)))
        {
            ++i;
        }

        if (i != count)
        {
            // illegal character in fragment
            throw new IllegalArgumentException(
                    String.format("Query %s contains illegal char %c at position %d", uriEncoded.toString(), uriEncoded.charAt(i), i));
        }

        return new Present<Fragment>(new SimpleFragment(uriEncoded.subSequence(1, count)));
    }
}
