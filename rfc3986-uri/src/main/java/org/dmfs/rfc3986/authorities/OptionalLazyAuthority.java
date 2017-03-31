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
import org.dmfs.rfc3986.Authority;
import org.dmfs.rfc3986.UriEncoded;
import org.dmfs.rfc3986.utils.Parsed;

import java.util.NoSuchElementException;

import static org.dmfs.optional.Absent.absent;


/**
 * The {@link Optional} {@link Authority} of a {@link UriEncoded} {@link CharSequence}.
 *
 * @author Marten Gajda
 */
public final class OptionalLazyAuthority implements Optional<Authority>, Parsed
{
    private final UriEncoded mUriEncoded;
    private Optional<Authority> mOptionalAuthority;


    public OptionalLazyAuthority(UriEncoded uriEncoded)
    {
        mUriEncoded = uriEncoded;
    }


    @Override
    public boolean isPresent()
    {
        return authority().isPresent();
    }


    @Override
    public Authority value(Authority defaultValue)
    {
        return authority().value(defaultValue);
    }


    @Override
    public Authority value() throws NoSuchElementException
    {
        return authority().value();
    }


    private Optional<Authority> authority()
    {
        if (mOptionalAuthority == null)
        {
            mOptionalAuthority = parsedAuthority();
        }
        return mOptionalAuthority;
    }


    private Optional<Authority> parsedAuthority()
    {
        if (mUriEncoded.length() < 2 || mUriEncoded.charAt(0) != '/' || mUriEncoded.charAt(1) != '/')
        {
            // too short or not starting with "//", not an authority
            return absent();
        }
        // this looks like an authority
        return new Present<Authority>(new EncodedAuthority(mUriEncoded.subSequence(2, mUriEncoded.length())));
    }


    @Override
    public int parsedLength()
    {
        return authority().isPresent() ? ((EncodedAuthority) mOptionalAuthority.value()).parsedLength() + 2 : 0;
    }

}
