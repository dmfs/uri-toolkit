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

package org.dmfs.rfc3986.parameters.parameters;

import org.dmfs.rfc3986.UriEncoded;
import org.dmfs.rfc3986.encoding.FormPrecoded;
import org.dmfs.rfc3986.encoding.IdempotentEncoded;
import org.dmfs.rfc3986.parameters.Parameter;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;


/**
 * A {@link Parameter} derived from a url encoded key-value-pair.
 */
public final class UrlEncodedParameter implements Parameter
{
    private final CharSequence mEncodedParameter;
    private final String mCharSet;
    private UriEncoded mName;
    private UriEncoded mValue;


    public UrlEncodedParameter(UriEncoded encodedPair)
    {
        this(encodedPair, "UTF-8");
    }


    public UrlEncodedParameter(UriEncoded encodedParameter, String charSet) throws UnsupportedCharsetException
    {
        mEncodedParameter = encodedParameter;
        mCharSet = charSet;
        // check early if the charset exists
        Charset.forName(charSet);
    }


    @Override
    public CharSequence name()
    {
        parse();
        try
        {
            return mName.decoded(mCharSet);
        }
        catch (UnsupportedEncodingException e)
        {
            // this should not happen since we've already tried to load the charset in the constructor
            throw new RuntimeException(String.format("CharSet %s not supported by Runtime", mCharSet));
        }
    }


    @Override
    public CharSequence textValue()
    {
        parse();
        try
        {
            return mValue.decoded(mCharSet);
        }
        catch (UnsupportedEncodingException e)
        {
            // this should not happen since we've already tried to load the charset in the constructor
            throw new RuntimeException(String.format("CharSet %s not supported by Runtime", mCharSet));
        }
    }


    private void parse()
    {
        if (mName == null)
        {
            int equalsPos = equalsPos(mEncodedParameter);
            mName = new FormPrecoded(mEncodedParameter.subSequence(0, equalsPos));
            if (equalsPos < mEncodedParameter.length())
            {
                mValue = new FormPrecoded(mEncodedParameter.subSequence(equalsPos + 1, mEncodedParameter.length()));
            }
            else
            {
                mValue = IdempotentEncoded.EMPTY;
            }
        }
    }


    private int equalsPos(CharSequence charSequence)
    {
        final int len = charSequence.length();
        int i = 0;
        while (i < len && charSequence.charAt(i) != '=')
        {
            ++i;
        }
        return i;
    }
}
