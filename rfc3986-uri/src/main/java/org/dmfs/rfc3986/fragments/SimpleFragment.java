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

package org.dmfs.rfc3986.fragments;

import org.dmfs.rfc3986.Fragment;
import org.dmfs.rfc3986.UriEncoded;
import org.dmfs.rfc3986.encoding.XWwwFormUrlEncoded;
import org.dmfs.rfc3986.parameters.ParameterList;

import java.io.UnsupportedEncodingException;


/**
 * A {@link Fragment} derived from a {@link UriEncoded} {@link CharSequence}.
 *
 * @author Marten Gajda
 */
public final class SimpleFragment implements Fragment
{
    private final UriEncoded mDelegate;


    public SimpleFragment(ParameterList parameters)
    {
        this(new XWwwFormUrlEncoded(parameters));
    }


    public SimpleFragment(UriEncoded fragment)
    {
        mDelegate = fragment;
    }


    @Override
    public UriEncoded normalized()
    {
        return mDelegate.normalized();
    }


    @Override
    public CharSequence decoded(String charset) throws UnsupportedEncodingException
    {
        return mDelegate.decoded(charset);
    }


    @Override
    public CharSequence decoded()
    {
        return mDelegate.decoded();
    }


    @Override
    public int length()
    {
        return mDelegate.length();
    }


    @Override
    public char charAt(int i)
    {
        return mDelegate.charAt(i);
    }


    @Override
    public UriEncoded subSequence(int startIndex, int endIndex)
    {
        return mDelegate.subSequence(startIndex, endIndex);
    }


    @Override
    public String toString()
    {
        return mDelegate.toString();
    }
}
