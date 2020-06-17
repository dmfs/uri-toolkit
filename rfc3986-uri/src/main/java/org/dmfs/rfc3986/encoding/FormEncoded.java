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
import org.dmfs.rfc3986.encoding.utils.FormPercentEncodingOutputStream;
import org.dmfs.rfc3986.validation.CharSets;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;


/**
 * A lazily encoded {@link CharSequence}. This encodes <strong>all</strong> reserved characters and delimiters. The only characters not encoded are "a"-"z",
 * "A"-"Z", "0"-"9", "-", "_", "." and "~".
 * <p>
 * Note, in contrast to {@link Encoded} this encodes spaces as {@code +} instead of {@code %20} and normalizes new line squences to {@code %0D%0A}.
 *
 * @author Marten Gajda
 * @see <a href="https://tools.ietf.org/html/rfc3986#section-2.3">RFC 3986 Section 2.3</a>
 */
public final class FormEncoded implements UriEncoded
{
    private final CharSequence mPlain;
    private final String mCharSet;
    private CharSequence mEncoded;


    public FormEncoded(CharSequence plain)
    {
        this(plain, "UTF-8");
    }


    public FormEncoded(CharSequence plain, String charSet)
    {
        mPlain = plain;
        mCharSet = charSet;
    }


    @Override
    public UriEncoded normalized()
    {
        // this will be encoded in a normalized form
        return this;
    }


    @Override
    public CharSequence decoded(String charset)
    {
        return mPlain;
    }


    @Override
    public CharSequence decoded()
    {
        return mPlain;
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
        return obj instanceof UriEncoded && toString().equals(((UriEncoded) obj).normalized().toString());
    }


    @Override
    public String toString()
    {
        if (mEncoded == null)
        {
            try
            {
                mEncoded = encoded(mPlain, mCharSet);
            }
            catch (UnsupportedEncodingException e)
            {
                throw new IllegalArgumentException(String.format("Charset %s not supported by Runtime", mCharSet));
            }
        }
        return mEncoded.toString();
    }


    private CharSequence encoded(CharSequence charSequence, String charSet) throws UnsupportedEncodingException
    {
        final int len = charSequence != null ? charSequence.length() : 0;
        if (len == 0)
        {
            return IdempotentEncoded.EMPTY;
        }
        try
        {
            // TODO: improve the performance. This appears to have quite some overhead compared to URLEncoder.encode().
            OutputStream out = new FormPercentEncodingOutputStream(len, CharSets.UNRESERVED);
            Writer w = new OutputStreamWriter(out, charSet);
            w.append(charSequence);
            w.close();
            return out.toString();
        }
        catch (UnsupportedEncodingException e)
        {
            throw e;
        }
        catch (IOException e)
        {
            throw new RuntimeException("IOException while operating on CharSequences");
        }
    }
}
