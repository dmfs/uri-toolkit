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

package org.dmfs.rfc3986.paths;

import org.dmfs.rfc3986.Path;
import org.dmfs.rfc3986.UriEncoded;
import org.dmfs.rfc3986.utils.Parsed;

import java.util.Iterator;

import static org.dmfs.rfc3986.validation.CharSets.PCHAR;


/**
 * A lazy {@link Path}. It's not parsed nor validated until it's actually needed.
 *
 * @author Marten Gajda
 */
public final class LazyPath implements Path, Parsed
{
    private final UriEncoded mUriEncoded;
    private Path mDelegate;
    private int mEnd;


    public LazyPath(UriEncoded uri)
    {
        mUriEncoded = uri;
    }


    @Override
    public boolean isEmpty()
    {
        return path().isEmpty();
    }


    @Override
    public boolean isAbsolute()
    {
        return path().isAbsolute();
    }


    @Override
    public Iterator<UriEncoded> iterator()
    {
        return path().iterator();
    }


    private Path path()
    {
        if (mDelegate == null)
        {
            mDelegate = parsedPath();
        }
        return mDelegate;
    }


    private Path parsedPath()
    {
        final UriEncoded uriEncoded = mUriEncoded;
        final int count = uriEncoded.length();
        int i = 0;
        if (i == count)
        {
            return EmptyPath.INSTANCE;
        }
        while (i < count && (PCHAR.contains(uriEncoded.charAt(i)) || uriEncoded.charAt(i) == '/'))
        {
            ++i;
        }
        mEnd = i;
        return new EncodedPath(uriEncoded.subSequence(0, i));
    }


    @Override
    public int parsedLength()
    {
        path();
        return mEnd;
    }
}
