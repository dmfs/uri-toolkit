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

import org.dmfs.jems2.iterator.Mapped;
import org.dmfs.rfc3986.Path;
import org.dmfs.rfc3986.UriEncoded;
import org.dmfs.rfc3986.encoding.Precoded;
import org.dmfs.rfc3986.utils.Split;

import java.util.Iterator;


/**
 * A path that's derived from a properly encoded String.
 */
public final class EncodedPath implements Path
{
    private final CharSequence mPath;


    public EncodedPath(UriEncoded path)
    {
        mPath = path;
    }


    @Override
    public boolean isEmpty()
    {
        return mPath.length() == 0;
    }


    @Override
    public boolean isAbsolute()
    {
        return mPath.length() > 0 && mPath.charAt(0) == '/';
    }


    @Override
    public Iterator<UriEncoded> iterator()
    {
        if (isEmpty())
        {
            return org.dmfs.jems2.iterator.EmptyIterator.emptyIterator();
        }
        return new Mapped<>(Precoded::new, new Split(mPath, '/'));
    }
}
