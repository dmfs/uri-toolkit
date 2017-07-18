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

import org.dmfs.optional.Optional;
import org.dmfs.rfc3986.Authority;
import org.dmfs.rfc3986.Fragment;
import org.dmfs.rfc3986.Path;
import org.dmfs.rfc3986.Query;
import org.dmfs.rfc3986.Scheme;
import org.dmfs.rfc3986.Uri;
import org.dmfs.rfc3986.encoding.Precoded;
import org.dmfs.rfc3986.utils.ToStringCharSequence;

import java.net.URI;


/**
 * {@link URI} to {@link Uri} adapter.
 *
 * @author Gabor Keszthelyi
 */
public final class JavaUri implements Uri
{

    private final Uri mDelegate;


    public JavaUri(URI javaUri)
    {
        mDelegate = new LazyUri(new Precoded(new ToStringCharSequence(javaUri)));
    }


    @Override
    public Optional<? extends Scheme> scheme()
    {
        return mDelegate.scheme();
    }


    @Override
    public Optional<? extends Authority> authority()
    {
        return mDelegate.authority();
    }


    @Override
    public Path path()
    {
        return mDelegate.path();
    }


    @Override
    public Optional<? extends Query> query()
    {
        return mDelegate.query();
    }


    @Override
    public Optional<? extends Fragment> fragment()
    {
        return mDelegate.fragment();
    }


    @Override
    public boolean isHierarchical()
    {
        return mDelegate.isHierarchical();
    }


    @Override
    public boolean isAbsolute()
    {
        return mDelegate.isAbsolute();
    }
}
