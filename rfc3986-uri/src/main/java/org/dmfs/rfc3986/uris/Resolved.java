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
import org.dmfs.rfc3986.*;


/**
 * A {@link Uri} decorator that resolves a reference {@link Uri} against the decorated base {@link Uri}.
 */
public final class Resolved implements Uri
{
    private final Uri mBase;
    private final Uri mReference;


    public Resolved(Uri base, Uri reference)
    {
        mBase = base;
        mReference = reference;
    }


    @Override
    public Optional<? extends Scheme> scheme()
    {
        return mReference.isAbsolute() ? mReference.scheme() : mBase.scheme();
    }


    @Override
    public Optional<? extends Authority> authority()
    {
        return mReference.isAbsolute() || mReference.authority().isPresent() ? mReference.authority() : mBase.authority();
    }


    @Override
    public Path path()
    {
        return mReference.isAbsolute() || mReference.authority().isPresent() ?
            new org.dmfs.rfc3986.paths.Normalized(mReference.path()) :
            new org.dmfs.rfc3986.paths.Resolved(mBase.path(), mReference.path());
    }


    @Override
    public Optional<? extends Query> query()
    {
        return mReference.isAbsolute() ||
            mReference.authority().isPresent() ||
            !mReference.path().isEmpty() ||
            mReference.query().isPresent() ? mReference.query() : mBase.query();
    }


    @Override
    public Optional<? extends Fragment> fragment()
    {
        return mReference.fragment();
    }


    @Override
    public boolean isHierarchical()
    {
        return mBase.isHierarchical() || mReference.isHierarchical();
    }


    @Override
    public boolean isAbsolute()
    {
        return mBase.isAbsolute() || mReference.isAbsolute();
    }
}
