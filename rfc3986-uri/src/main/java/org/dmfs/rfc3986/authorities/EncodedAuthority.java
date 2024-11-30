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

import org.dmfs.jems2.Optional;
import org.dmfs.jems2.optional.Present;
import org.dmfs.rfc3986.Authority;
import org.dmfs.rfc3986.UriEncoded;
import org.dmfs.rfc3986.utils.Parsed;

import static org.dmfs.jems2.optional.Absent.absent;
import static org.dmfs.rfc3986.validation.CharSets.*;


/**
 * An {@link Authority} that's parsed and validated lazily.
 */
public final class EncodedAuthority implements Authority, Parsed
{
    private final UriEncoded mAuthority;
    private OptionalLazyUserInfo mUserInfo;
    private UriEncoded mHost;
    private Optional<Integer> mPort;
    private int mEnd;


    public EncodedAuthority(UriEncoded authority)
    {
        mAuthority = authority;
        mUserInfo = new OptionalLazyUserInfo(mAuthority);
    }


    @Override
    public OptionalLazyUserInfo userInfo()
    {
        return mUserInfo;
    }


    @Override
    public UriEncoded host()
    {
        if (mHost == null)
        {
            mHost = parsedHost();
        }
        return mHost;
    }


    private UriEncoded parsedHost()
    {
        final UriEncoded authority = mAuthority;
        final int count = authority.length();
        final int start = userInfo().isPresent() ? userInfo().value().length() + 1 : 0;

        int i = start;
        if (i == count)
        {
            return authority.subSequence(start, count);
        }

        if (authority.charAt(i) == '[')
        {
            return parsedIpvFuture(authority, i, count);
        }

        while (i < count && REG_NAME_CHAR.contains(authority.charAt(i)))
        {
            ++i;
        }

        if (i < count && !HOST_TERMINATOR_CHARS.contains(authority.charAt(i)))
        {
            // the authority is followed by an unexpected character
            throw new IllegalArgumentException(
                String.format("Authority %s contains illegal char %c at position %d", authority.toString(), authority.charAt(i), i));
        }

        return authority.subSequence(start, i);
    }


    private UriEncoded parsedIpvFuture(UriEncoded authority, int start, int length)
    {
        int i = start + 1;
        while (i < length && authority.charAt(i) != ']')
        {
            // TODO validate format of IPv6 or Future IP address
            ++i;
        }
        if (i == length)
        {
            throw new IllegalArgumentException(String.format("missing ']' in %s", authority.toString()));
        }
        return authority.subSequence(start, i);
    }


    @Override
    public Optional<Integer> port()
    {
        if (mPort == null)
        {
            mPort = parsedPort();
        }
        return mPort;
    }


    private Optional<Integer> parsedPort()
    {
        final UriEncoded authority = mAuthority;
        final int count = authority.length();
        final int portPos = host().length() + userInfo().parsedLength();
        if (portPos + 1 >= count || authority.charAt(portPos) != ':')
        {
            mEnd = portPos;
            // no port
            return absent();
        }
        int port = 0;
        int i = portPos + 1;
        while (i < count && DIGIT.contains(authority.charAt(i)))
        {
            port = port * 10 + authority.charAt(i) - '0';
            ++i;
        }
        mEnd = i;
        if (i == count)
        {
            // end of string
            return new Present<>(port);
        }
        char next = authority.charAt(i);
        if (next != '/' && next != '?' && next != '#')
        {
            // port not followed by a valid character
            throw new IllegalArgumentException(
                String.format("Authority %s contains illegal char %c at position %d", mAuthority.toString(), next, i));
        }
        return new Present<>(port);

    }


    @Override
    public int parsedLength()
    {
        port();
        return mEnd;
    }
}
