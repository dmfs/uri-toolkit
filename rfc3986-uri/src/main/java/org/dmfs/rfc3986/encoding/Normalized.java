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
import org.dmfs.rfc3986.validation.CharSets;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;


/**
 * A normalized {@link UriEncoded} {@link CharSequence}.
 *
 * @author Marten Gajda
 */
public final class Normalized implements UriEncoded
{
    private final UriEncoded mDelegate;
    private CharSequence mNormalized;


    public Normalized(UriEncoded delegate)
    {
        mDelegate = delegate;
    }


    @Override
    public UriEncoded normalized()
    {
        return this;
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
    public int hashCode()
    {
        return toString().hashCode();
    }


    @Override
    public boolean equals(Object obj)
    {
        if (!(obj instanceof UriEncoded))
        {
            return false;
        }
        return toString().equals(((UriEncoded) obj).normalized().toString());
    }


    @Override
    public String toString()
    {
        if (mNormalized == null)
        {
            mNormalized = normalized(mDelegate);
        }
        return mNormalized.toString();
    }


    private CharSequence normalized(CharSequence encoded)
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
                    throw new IllegalArgumentException("Illegal encoding");
                }

                int value = (decodeDigit(encoded.charAt(i + 1)) << 4) + decodeDigit(encoded.charAt(i + 2));
                if (CharSets.UNRESERVED.contains((char) value))
                {
                    // this doesn't have to be encoded
                    out.write(value);
                    i += 3;
                }
                else
                {
                    // preserve encoding
                    out.write('%');
                    char high = encoded.charAt(i + 1);
                    if (high >= 'a')
                    {
                        out.write(high & 0xffdf);
                    }
                    else
                    {
                        out.write(high);
                    }
                    char low = encoded.charAt(i + 2);
                    if (low >= 'a')
                    {
                        out.write(low & 0xffdf);
                    }
                    else
                    {
                        out.write(low);
                    }
                    i += 3;
                }
            }
            else
            {
                out.write(c);
                i += 1;
            }
        }
        try
        {
            // TODO: can we assume that all non-ASCII characters are encoded?
            return out.toString("UTF-8");
        }
        catch (UnsupportedEncodingException e)
        {
            throw new RuntimeException("Runtime doesn't support UTF-8");
        }
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
