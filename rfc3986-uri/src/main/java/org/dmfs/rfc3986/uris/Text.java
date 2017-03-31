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
import org.dmfs.rfc3986.Query;
import org.dmfs.rfc3986.Uri;


/**
 * Adapter to adapt a {@link Uri} to a {@link CharSequence}.
 *
 * @author Marten Gajda
 */
public final class Text implements CharSequence
{
    private final Uri mUri;
    private String mString;


    public Text(Uri uri)
    {
        mUri = uri;
    }


    @Override
    public int length()
    {
        return toString().length();
    }


    @Override
    public char charAt(int i)
    {
        return toString().charAt(i);
    }


    @Override
    public CharSequence subSequence(int i, int i1)
    {
        return toString().subSequence(i, i1);
    }


    @Override
    public String toString()
    {
        if (mString == null)
        {
            StringBuilder stringBuilder = new StringBuilder(128);
            if (mUri.scheme().isPresent())
            {
                stringBuilder.append(mUri.scheme().value().toString());
                stringBuilder.append(':');
            }
            Optional<? extends Authority> optAuthority = mUri.authority();
            if (optAuthority.isPresent())
            {
                stringBuilder.append('/');
                stringBuilder.append('/');
                stringBuilder.append(new org.dmfs.rfc3986.authorities.Text(optAuthority.value()));
            }

            // Path is the only component that's always present, although it might be empty
            stringBuilder.append(new org.dmfs.rfc3986.paths.Text(mUri.path()));

            Optional<? extends Query> optQuery = mUri.query();
            if (optQuery.isPresent())
            {
                stringBuilder.append('?');
                stringBuilder.append(optQuery.value());
            }
            Optional<? extends Fragment> optFragment = mUri.fragment();
            if (optFragment.isPresent())
            {
                stringBuilder.append('#');
                stringBuilder.append(optFragment.value());
            }
            mString = stringBuilder.toString();
        }
        return mString;
    }
}
