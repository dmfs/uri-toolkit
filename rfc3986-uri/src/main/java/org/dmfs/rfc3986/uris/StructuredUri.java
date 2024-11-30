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


/**
 * A {@link Uri} that's composed of its individual components.
 */
public final class StructuredUri implements Uri
{
    private final Optional<Scheme> mScheme;
    private final Optional<Authority> mAuthority;
    private final Path mPath;
    private final Optional<Query> mQuery;
    private final Optional<Fragment> mFragment;


    public StructuredUri(Scheme scheme, Authority authority)
    {
        this(new Present<>(scheme), new Present<>(authority), EmptyPath.INSTANCE, Absent.<Query>absent(), Absent.<Fragment>absent());
    }


    public StructuredUri(Scheme scheme, Authority authority, Path path)
    {
        this(new Present<>(scheme), new Present<>(authority), path, Absent.<Query>absent(), Absent.<Fragment>absent());
    }


    public StructuredUri(Scheme scheme, Authority authority, Path path, Query query)
    {
        this(new Present<>(scheme), new Present<>(authority), path, new Present<>(query), Absent.<Fragment>absent());
    }


    public StructuredUri(Scheme scheme, Authority authority, Path path, Query query, Fragment fragment)
    {
        this(new Present<>(scheme), new Present<>(authority), path, new Present<>(query), new Present<>(fragment));
    }


    private StructuredUri(Optional<Scheme> scheme, Optional<Authority> authority, Path path, Optional<Query> query, Optional<Fragment> fragment)
    {
        mScheme = scheme;
        mAuthority = authority;
        mPath = path;
        mQuery = query;
        mFragment = fragment;
        if (authority.isPresent() && !path.isAbsolute() && !path.isEmpty())
        {
            throw new IllegalArgumentException("URIs with an authority must have an absolute or empty path.");
        }
        if (!scheme.isPresent() && !authority.isPresent() && !mPath.isAbsolute() && !path.isEmpty() && path.iterator().next().toString().contains(":"))
        {
            throw new IllegalArgumentException(
                "URIs without scheme and authority must have not have a \":\" in the first path segment unless the path is absolute.");
        }
        // TODO: do we have more constraints?
    }


    @Override
    public Optional<? extends Scheme> scheme()
    {
        return mScheme;
    }


    @Override
    public Optional<? extends Authority> authority()
    {
        return mAuthority;
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
        return !mScheme.isPresent() || mAuthority.isPresent() || mPath.isAbsolute();
    }


    @Override
    public boolean isAbsolute()
    {
        return mScheme.isPresent();
    }
}
