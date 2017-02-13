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
import org.dmfs.rfc3986.parameters.Parameter;
import org.dmfs.rfc3986.parameters.ParameterList;

import java.io.UnsupportedEncodingException;


/**
 * An adapter to adapt a {@link ParameterList} to {@code x-www-form-urlencoded} a {@link UriEncoded}.
 * <p>
 * Note, this can't be decoded as a whole. Calling {@link #decoded()} or {@link #decoded(String)} with result in an Exception.
 *
 * @author Marten Gajda
 */
public final class XWwwFormUrlEncoded implements UriEncoded
{
    private final ParameterList mParams;
    private final String mCharSet;
    private String mText;


    public XWwwFormUrlEncoded(ParameterList params)
    {
        this(params, "UTF-8");
    }


    public XWwwFormUrlEncoded(ParameterList params, String charset)
    {
        mParams = params;
        mCharSet = charset;
    }


    @Override
    public UriEncoded normalized()
    {
        // this will be rendered in normalized form
        return this;
    }


    @Override
    public CharSequence decoded(String charset) throws UnsupportedEncodingException
    {
        throw new UnsupportedOperationException("x-www-form-urlencoded can't be decoded as a whole");
    }


    @Override
    public CharSequence decoded()
    {
        throw new UnsupportedOperationException("x-www-form-urlencoded can't be decoded as a whole");
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
    public UriEncoded subSequence(int startIndex, int endIndex)
    {
        if (startIndex == 0 && endIndex == toString().length())
        {
            return this;
        }
        return new Precoded(toString().subSequence(startIndex, endIndex));
    }


    @Override
    public String toString()
    {
        if (mText == null)
        {
            StringBuilder sb = new StringBuilder(256);
            boolean first = true;
            for (Parameter parameter : mParams)
            {
                if (first)
                {
                    first = false;
                }
                else
                {
                    sb.append('&');
                }
                sb.append(new FormEncoded(parameter.name(), mCharSet));
                sb.append('=');
                sb.append(new FormEncoded(parameter.textValue(), mCharSet));
            }
            mText = sb.toString();
        }
        return mText;
    }
}
