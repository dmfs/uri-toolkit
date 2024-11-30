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
 * An {@link UriEncoded} {@link CharSequence} that is derived from an already encoded {@link CharSequence}.
 */
public final class Precoded implements UriEncoded
{
    private final CharSequence mEncoded;
    private final int mStart;
    private final int mEnd;
    private CharSequence mUtf8Plain;
    private UriEncoded mNormalized;


    public Precoded(CharSequence encoded)
    {
        this(encoded, 0, encoded.length());
    }


    private Precoded(CharSequence encoded, int start, int end)
    {
        mEncoded = encoded;
        mStart = start;
        mEnd = end;
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
        return decoded(mEncoded, mStart, mEnd, charset);
    }


    @Override
    public CharSequence decoded()
    {
        if (mUtf8Plain == null)
        {
            try
            {
                mUtf8Plain = decoded(mEncoded, mStart, mEnd, "UTF-8");
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
        return mEnd - mStart;
    }


    @Override
    public char charAt(int i)
    {
        if (i < 0)
        {
            throw new ArrayIndexOutOfBoundsException(String.format("Index %d is negative.", i));
        }
        if (mStart + i >= mEnd)
        {
            throw new ArrayIndexOutOfBoundsException(String.format("Index %d exceeds length %d", i, length()));
        }
        return mEncoded.charAt(i + mStart);
    }


    @Override
    public UriEncoded subSequence(int startIndex, int endIndex)
    {
        if (startIndex == 0 && endIndex == length())
        {
            return this;
        }
        if (endIndex < startIndex)
        {
            throw new ArrayIndexOutOfBoundsException(String.format("Start index %d < end index %d.", startIndex, endIndex));
        }
        if (startIndex < 0)
        {
            throw new ArrayIndexOutOfBoundsException(String.format("Start index %d is negative.", startIndex));
        }
        if (mStart + endIndex > mEnd)
        {
            throw new ArrayIndexOutOfBoundsException(String.format("End index %d exceeds length %d", endIndex, length()));
        }
        return new Precoded(mEncoded, startIndex + mStart, endIndex + mStart);
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
        return mEncoded.subSequence(mStart, mEnd).toString();
    }


    private CharSequence decoded(CharSequence encoded, int start, int end, String charSet) throws UnsupportedEncodingException
    {
        if (start == end)
        {
            return "";
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream(encoded.length());
        int i = start;
        while (i < end)
        {
            char c = encoded.charAt(i);
            if (c == '%')
            {
                if (i + 2 >= end)
                {
                    throw new IllegalArgumentException("Illegal percent encoding.");
                }

                out.write((decodeDigit(encoded.charAt(i + 1)) << 4) + decodeDigit(encoded.charAt(i + 2)));
                i += 3;
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
