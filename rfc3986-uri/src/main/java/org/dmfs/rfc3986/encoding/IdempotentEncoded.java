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

package org.dmfs.rfc3986.encoding;

import org.dmfs.rfc3986.UriEncoded;

import java.io.UnsupportedEncodingException;


/**
 * Adapter for special {@link CharSequence}s that remain the same before and after encoding.
 * <p>
 * To be used with care!
 *
 * @author Marten Gajda
 */
public final class IdempotentEncoded implements UriEncoded
{
    public final static UriEncoded EMPTY = new IdempotentEncoded("");
    public final static UriEncoded CURRENT = new IdempotentEncoded(".");
    public final static UriEncoded PARENT = new IdempotentEncoded("..");

    private final CharSequence mText;


    public IdempotentEncoded(CharSequence text)
    {
        mText = text;
    }


    @Override
    public UriEncoded normalized()
    {
        return this;
    }


    @Override
    public CharSequence decoded(String charset) throws UnsupportedEncodingException
    {
        return mText;
    }


    @Override
    public CharSequence decoded()
    {
        return mText;
    }


    @Override
    public int length()
    {
        return mText.length();
    }


    @Override
    public char charAt(int i)
    {
        return mText.charAt(i);
    }


    @Override
    public UriEncoded subSequence(int startIndex, int endIndex)
    {
        if (startIndex == 0 && endIndex == mText.length())
        {
            return this;
        }
        return new IdempotentEncoded(mText.subSequence(startIndex, endIndex));
    }


    @Override
    public int hashCode()
    {
        return mText.hashCode();
    }


    @Override
    public boolean equals(Object obj)
    {
        return obj instanceof UriEncoded && mText.equals(((UriEncoded) obj).normalized().toString());
    }


    @Override
    public String toString()
    {
        return mText.toString();
    }
}
