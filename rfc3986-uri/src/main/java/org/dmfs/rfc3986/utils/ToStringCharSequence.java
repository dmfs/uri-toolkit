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

package org.dmfs.rfc3986.utils;

/**
 * A {@link CharSequence} that delegates to the given {@link Object}'s toString() value lazily,
 * and it also caches it.
 *
 * @author Gabor Keszthelyi
 */
// TODO Create and use it from java tools dmfs library
public final class ToStringCharSequence implements CharSequence
{
    private final Object mObject;

    private String mCachedToString;


    public ToStringCharSequence(Object object)
    {
        mObject = object;
    }


    @Override
    public int length()
    {
        return cachedToString().length();
    }


    @Override
    public char charAt(int index)
    {
        return cachedToString().charAt(index);
    }


    @Override
    public CharSequence subSequence(int start, int end)
    {
        return cachedToString().subSequence(start, end);
    }


    private String cachedToString()
    {
        if (mCachedToString == null)
        {
            mCachedToString = mObject.toString();
        }
        return mCachedToString;
    }
}
