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

package org.dmfs.rfc3986.uris;

import org.dmfs.jems2.Optional;
import org.dmfs.jems2.optional.Absent;
import org.dmfs.jems2.optional.Present;
import org.dmfs.rfc3986.*;
import org.dmfs.rfc3986.paths.EmptyPath;

import static org.dmfs.jems2.optional.Absent.absent;


/**
 * An absolute {@link Uri} without authority.
 */
public final class OpaqueUri implements Uri
{
    private final Optional<Scheme> mScheme;
    private final Path mPath;
    private final Optional<Query> mQuery;
    private final Optional<Fragment> mFragment;


    public OpaqueUri(Scheme scheme)
    {
        this(new Present<>(scheme), EmptyPath.INSTANCE, Absent.<Query>absent(), Absent.<Fragment>absent());
    }


    public OpaqueUri(Scheme scheme, Path path)
    {
        this(new Present<>(scheme), path, Absent.<Query>absent(), Absent.<Fragment>absent());
    }


    public OpaqueUri(Scheme scheme, Path path, Query query)
    {
        this(new Present<>(scheme), path, new Present<>(query), Absent.<Fragment>absent());
    }


    public OpaqueUri(Scheme scheme, Path path, Query query, Fragment fragment)
    {
        this(new Present<>(scheme), path, new Present<>(query), new Present<>(fragment));
    }


    private OpaqueUri(Optional<Scheme> scheme, Path path, Optional<Query> query, Optional<Fragment> fragment)
    {
        mScheme = scheme;
        mPath = path;
        mQuery = query;
        mFragment = fragment;
    }


    @Override
    public Optional<? extends Scheme> scheme()
    {
        return mScheme;
    }


    @Override
    public Optional<? extends Authority> authority()
    {
        return absent();
    }


    @Override
    public Path path()
    {
        return mPath;
    }


    @Override
    public Optional<? extends Query> query()
    {
        return mQuery;
    }


    @Override
    public Optional<? extends Fragment> fragment()
    {
        return mFragment;
    }


    @Override
    public boolean isHierarchical()
    {
        return false;
    }


    @Override
    public boolean isAbsolute()
    {
        return true;
    }
}
