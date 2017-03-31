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

package org.dmfs.rfc3986.authorities;

import org.dmfs.optional.Optional;
import org.dmfs.optional.Present;
import org.dmfs.rfc3986.UriEncoded;
import org.dmfs.rfc3986.utils.Parsed;

import java.util.NoSuchElementException;

import static org.dmfs.optional.Absent.absent;
import static org.dmfs.rfc3986.validation.CharSets.REG_NAME_CHAR;


/**
 * An {@link Optional} {@link UriEncoded} user info.
 *
 * @author Marten Gajda
 */
public final class OptionalLazyUserInfo implements Optional<UriEncoded>, Parsed
{
    private final UriEncoded mEncodedAuthority;
    private Optional<UriEncoded> mUserInfo;
    private int mEnd;


    public OptionalLazyUserInfo(UriEncoded encodedAuthority)
    {
        mEncodedAuthority = encodedAuthority;
    }


    @Override
    public boolean isPresent()
    {
        return userInfo().isPresent();
    }


    @Override
    public UriEncoded value(UriEncoded defaultValue)
    {
        return userInfo().value(defaultValue);
    }


    @Override
    public UriEncoded value() throws NoSuchElementException
    {
        return userInfo().value();
    }


    private Optional<UriEncoded> userInfo()
    {
        if (mUserInfo == null)
        {
            mUserInfo = parsedUserInfo();
        }
        return mUserInfo;
    }


    private Optional<UriEncoded> parsedUserInfo()
    {
        final UriEncoded encodedAuthority = mEncodedAuthority;
        final int count = encodedAuthority.length();
        int i = 0;
        while (i < count && (REG_NAME_CHAR.contains(encodedAuthority.charAt(i)) || encodedAuthority.charAt(i) == ':'))
        {
            ++i;
        }

        if (i == count || encodedAuthority.charAt(i) != '@')
        {
            // no @ -> no user info
            return absent();
        }

        mEnd = i + 1 /* account for the "@" which is not part of the user info */;
        return new Present<>(encodedAuthority.subSequence(0, i));
    }


    @Override
    public int parsedLength()
    {
        return mEnd;
    }
}
