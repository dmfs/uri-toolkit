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

package org.dmfs.rfc3986.params.utils;

import org.dmfs.rfc3986.UriEncoded;
import org.dmfs.rfc3986.encoding.FormPrecoded;
import org.dmfs.rfc3986.params.Pair;
import org.dmfs.rfc3986.utils.Split;

import java.util.Iterator;


/**
 * @author Marten Gajda
 */
public final class UriFormEncodedPair implements Pair<UriEncoded, UriEncoded>
{
    private final Iterator<CharSequence> mEncodedParts;
    private UriEncoded mKey;
    private UriEncoded mValue;


    public UriFormEncodedPair(UriEncoded encodedPair)
    {
        // TODO: it's probably more efficient if we find the '=' without the Splitting iterator.
        mEncodedParts = new Split(encodedPair, '=');
    }


    @Override
    public UriEncoded key()
    {
        parse();
        return mKey;
    }


    @Override
    public UriEncoded value()
    {
        parse();
        return mValue;
    }


    private void parse()
    {
        if (mKey == null)
        {
            mKey = new FormPrecoded(mEncodedParts.next());
            if (mEncodedParts.hasNext())
            {
                mValue = new FormPrecoded(mEncodedParts.next());
            }
        }
    }
}
