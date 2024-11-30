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


/**
 * An adapter that adapts a {@link Path} to a {@link CharSequence}.
 */
public final class Text implements CharSequence
{
    private final Path mDelegate;
    private String mPath;


    public Text(Path delegate)
    {
        mDelegate = delegate;
    }


    @Override
    public int length()
    {
        if (mPath == null)
        {
            // we don't have a cached copy of the path, so just sum the length of all segments
            int len = 0;
            for (UriEncoded segment : mDelegate)
            {
                len += segment.length() + 1;
            }
            return len == 0 ? 0 : len - 1;
        }
        return mPath.length();
    }


    @Override
    public char charAt(int index)
    {
        return toString().charAt(index);
    }


    @Override
    public CharSequence subSequence(int beginIndex, int endIndex)
    {
        return toString().subSequence(beginIndex, endIndex);
    }


    @Override
    public String toString()
    {
        if (mPath == null)
        {
            StringBuilder stringBuilder = new StringBuilder(256);
            boolean first = true;
            for (UriEncoded segment : mDelegate)
            {
                if (first)
                {
                    first = false;
                }
                else
                {
                    stringBuilder.append('/');
                }
                stringBuilder.append(segment);
            }
            mPath = stringBuilder.toString();
        }
        return mPath;
    }
}
