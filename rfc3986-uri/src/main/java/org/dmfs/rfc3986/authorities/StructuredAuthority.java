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

import org.dmfs.optional.Absent;
import org.dmfs.optional.Optional;
import org.dmfs.optional.Present;
import org.dmfs.rfc3986.Authority;
import org.dmfs.rfc3986.UriEncoded;


/**
 * An {@link Authority} built from its components.
 *
 * @author Marten Gajda
 */
public final class StructuredAuthority implements Authority
{
    private final Optional<UriEncoded> mUserInfo;
    private final UriEncoded mHost;
    private final Optional<Integer> mPort;


    public StructuredAuthority(UriEncoded host)
    {
        this(Absent.<UriEncoded>absent(), host, Absent.<Integer>absent());
    }


    public StructuredAuthority(UriEncoded host, int port)
    {
        this(Absent.<UriEncoded>absent(), host, new Present<>(port));
    }


    public StructuredAuthority(UriEncoded userInfo, UriEncoded host)
    {
        this(new Present<>(userInfo), host, Absent.<Integer>absent());
    }


    public StructuredAuthority(UriEncoded userInfo, UriEncoded host, int port)
    {
        this(new Present<>(userInfo), host, new Present<>(port));
    }


    public StructuredAuthority(Optional<UriEncoded> userInfo, UriEncoded host, Optional<Integer> port)
    {
        mUserInfo = userInfo;
        mHost = host;
        mPort = port;
    }


    @Override
    public Optional<? extends UriEncoded> userInfo()
    {
        return mUserInfo;
    }


    @Override
    public UriEncoded host()
    {
        return mHost;
    }


    @Override
    public Optional<Integer> port()
    {
        return mPort;
    }
}
