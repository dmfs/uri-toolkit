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

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;


/**
 * An {@link UriEncoded} {@link CharSequence} that is derived from an already encoded {@link CharSequence}. In contrast to {@link Precoded} this decodes {@code
 * +} into a space character. This is meant to decode values in {@code x-www-form-urlencoded} data (like most query strings).
 * <p>
 * TODO: validate the provided string.
 *
 * @author Marten Gajda
 */
public final class FormPrecoded implements UriEncoded
{
    private final CharSequence mEncoded;
    private CharSequence mUtf8Plain;
    private UriEncoded mNormalized;


    public FormPrecoded(CharSequence encoded)
    {
        mEncoded = encoded;
    }


    @Override
    public UriEncoded normalized()
    {
        if (mNormalized == null)
        {
            mNormalized = new Normalized(this);
        }
        return mNormalized;
    }


    @Override
    public CharSequence decoded(String charset) throws UnsupportedEncodingException
    {
        if ("UTF-8".equalsIgnoreCase(charset))
        {
            return decoded();
        }
        return decoded(mEncoded, charset);
    }


    @Override
    public CharSequence decoded()
    {
        if (mUtf8Plain == null)
        {
            try
            {
                mUtf8Plain = decoded(mEncoded, "UTF-8");
            }
            catch (UnsupportedEncodingException e)
            {
                throw new RuntimeException("Runtime doesn't support UTF-8");
            }
        }
        return mUtf8Plain;
    }


    @Override
    public int length()
    {
        return mEncoded.length();
    }


    @Override
    public char charAt(int i)
    {
        return mEncoded.charAt(i);
    }


    @Override
    public UriEncoded subSequence(int startIndex, int endIndex)
    {
        return new FormPrecoded(mEncoded.subSequence(startIndex, endIndex));
    }


    @Override
    public int hashCode()
    {
        return normalized().hashCode();
    }


    @Override
    public boolean equals(Object obj)
    {
        return obj instanceof UriEncoded && normalized().equals(obj);
    }


    @Override
    public String toString()
    {
        return mEncoded.toString();
    }


    private CharSequence decoded(CharSequence encoded, String charSet) throws UnsupportedEncodingException
    {
        if (encoded.length() == 0)
        {
            return encoded;
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream(encoded.length());
        final int count = encoded.length();
        int i = 0;
        while (i < count)
        {
            char c = encoded.charAt(i);
            if (c == '%')
            {
                if (i + 2 >= count)
                {
                    throw new IllegalArgumentException("Illegal percent encoding.");
                }

                out.write((decodeDigit(encoded.charAt(i + 1)) << 4) + decodeDigit(encoded.charAt(i + 2)));
                i += 3;
            }
            else if (c == '+')
            {
                out.write(' ');
                i += 1;
            }
            else
            {
                out.write(c);
                i += 1;
            }
        }
        return out.toString(charSet);
    }


    private int decodeDigit(char c)
    {
        if ('0' <= c && c <= '9')
        {
            return c - '0';
        }
        int r = (c - 'A') & 0xffffffDF;
        if (r < 0 || r > 5)
        {
            throw new IllegalArgumentException(String.format("%c is not a valid hex digit"));
        }
        return r + 10;
    }
}
