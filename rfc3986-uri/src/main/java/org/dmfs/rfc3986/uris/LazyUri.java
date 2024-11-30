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
import org.dmfs.rfc3986.Fragment;
import org.dmfs.rfc3986.Scheme;
import org.dmfs.rfc3986.Uri;
import org.dmfs.rfc3986.UriEncoded;
import org.dmfs.rfc3986.authorities.OptionalLazyAuthority;
import org.dmfs.rfc3986.fragments.OptionalLazyFragment;
import org.dmfs.rfc3986.paths.LazyPath;
import org.dmfs.rfc3986.queries.OptionalLazyQuery;
import org.dmfs.rfc3986.schemes.OptionalLazyScheme;


/**
 * A lazily parsed and validated {@link Uri}.
 * <p>
 * To validate the entire URI you need to call {@link #fragment()}, otherwise the URI is only parsed and validated as far as necessary.
 */
public final class LazyUri implements Uri
{
    private final UriEncoded mUriEncoded;
    private final OptionalLazyScheme mOptionalScheme;
    private OptionalLazyAuthority mAuthority;
    private LazyPath mPath;
    private OptionalLazyQuery mQuery;
    private Optional<Fragment> mFragment;


    /**
     * Create a lazy {@link Uri} from the given {@link UriEncoded} {@link CharSequence}.
     *
     * @param uri The properly {@link UriEncoded} uri.
     */
    public LazyUri(UriEncoded uri)
    {
        mUriEncoded = uri;
        mOptionalScheme = new OptionalLazyScheme(uri);
    }


    @Override
    public Optional<? extends Scheme> scheme()
    {
        return mOptionalScheme;
    }


    @Override
    public OptionalLazyAuthority authority()
    {
        if (mAuthority == null)
        {
            mAuthority = new OptionalLazyAuthority(mUriEncoded.subSequence(mOptionalScheme.parsedLength(), mUriEncoded.length()));
        }
        return mAuthority;
    }


    @Override
    public LazyPath path()
    {
        if (mPath == null)
        {
            mPath = new LazyPath(mUriEncoded.subSequence(mOptionalScheme.parsedLength() + authority().parsedLength(), mUriEncoded.length()));
        }
        return mPath;
    }


    @Override
    public OptionalLazyQuery query()
    {
        if (mQuery == null)
        {
            mQuery = new OptionalLazyQuery(
                mUriEncoded.subSequence(mOptionalScheme.parsedLength() + authority().parsedLength() + path().parsedLength(), mUriEncoded.length()));
        }
        return mQuery;
    }


    @Override
    public Optional<? extends Fragment> fragment()
    {
        if (mFragment == null)
        {
            mFragment = new OptionalLazyFragment(
                mUriEncoded.subSequence(mOptionalScheme.parsedLength() + authority().parsedLength() + path().parsedLength() + query().parsedLength(),
                    mUriEncoded.length()));
        }
        return mFragment;
    }


    @Override
    public boolean isHierarchical()
    {
        return !mOptionalScheme.isPresent() || authority().isPresent() || path().isAbsolute();
    }


    @Override
    public boolean isAbsolute()
    {
        return mOptionalScheme.isPresent();
    }
}
