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

import org.dmfs.optional.Absent;
import org.dmfs.optional.Optional;
import org.dmfs.optional.Present;
import org.dmfs.rfc3986.Authority;
import org.dmfs.rfc3986.Fragment;
import org.dmfs.rfc3986.Path;
import org.dmfs.rfc3986.Query;
import org.dmfs.rfc3986.Scheme;
import org.dmfs.rfc3986.Uri;
import org.dmfs.rfc3986.paths.EmptyPath;

import static org.dmfs.optional.Absent.absent;


/**
 * A simplified relative reference {@link Uri} as per <a href="https://tools.ietf.org/html/rfc3986#section-4.2">RFC 3986, Section 4.2</a>. A relative {@link
 * Uri} has no {@link Scheme}. Since scheme relative references are less common, this class supports only relative references without {@link Scheme} and {@link
 * Authority}. To create a scheme relative reference use {@link StructuredUri#StructuredUri(Optional, Optional, Path, Optional, Optional)} and pass {@code
 * None.none()} as the {@link Scheme}.
 *
 * @author Marten Gajda
 */
public final class RelativeUri implements Uri
{
    private final Path mPath;
    private final Optional<Query> mQuery;
    private final Optional<Fragment> mFragment;


    public RelativeUri(Path path)
    {
        this(path, Absent.<Query>absent(), Absent.<Fragment>absent());
    }


    public RelativeUri(Query query)
    {
        this(EmptyPath.INSTANCE, new Present<>(query), Absent.<Fragment>absent());
    }


    public RelativeUri(Fragment fragment)
    {
        this(EmptyPath.INSTANCE, Absent.<Query>absent(), new Present<>(fragment));
    }


    public RelativeUri(Path path, Query query)
    {
        this(path, new Present<>(query), Absent.<Fragment>absent());
    }


    public RelativeUri(Query query, Fragment fragment)
    {
        this(EmptyPath.INSTANCE, new Present<>(query), new Present<>(fragment));
    }


    public RelativeUri(Path path, Query query, Fragment fragment)
    {
        this(path, new Present<>(query), new Present<>(fragment));
    }


    public RelativeUri(Path path, Optional<Query> query, Optional<Fragment> fragment)
    {
        mPath = path;
        mQuery = query;
        mFragment = fragment;
    }


    @Override
    public Optional<? extends Scheme> scheme()
    {
        return absent();
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
        return true;
    }


    @Override
    public boolean isAbsolute()
    {
        return false;
    }

}
