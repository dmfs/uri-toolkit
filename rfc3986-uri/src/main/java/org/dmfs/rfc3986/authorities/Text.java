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

package org.dmfs.rfc3986.authorities;

import org.dmfs.jems2.Optional;
import org.dmfs.rfc3986.Authority;
import org.dmfs.rfc3986.UriEncoded;
import org.dmfs.rfc3986.encoding.Precoded;

import java.io.UnsupportedEncodingException;


/**
 * The (normalized) {@link UriEncoded} representation of an {@link Authority}.
 */
public final class Text implements UriEncoded
{
    private final Authority mDelegate;
    private String mText;


    public Text(Authority delegate)
    {
        mDelegate = delegate;
    }


    @Override
    public int length()
    {
        if (mText == null)
        {
            // result not cached yet, calculate the length on the fly
            int len = mDelegate.host().length();
            if (mDelegate.userInfo().isPresent())
            {
                len += mDelegate.userInfo().value().length() + 1;
            }
            Optional<Integer> optionalPort = mDelegate.port();
            if (optionalPort.isPresent())
            {
                int port = optionalPort.value();
                // we use this if-else chain, because it's supposed to be faster than to string conversion and easier than calculations via log
                if (port < 10)
                {
                    len += 2;
                }
                else if (port < 100)
                {
                    len += 3;
                }
                else if (port < 1000)
                {
                    len += 4;
                }
                else if (port < 10000)
                {
                    len += 5;
                }
                else if (port < 100000)
                {
                    len += 6;
                }
                else
                {
                    throw new IllegalArgumentException(String.format("Port number %d out of range (<100000)", port));
                }
            }
            return len;
        }
        return mText.length();
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
            StringBuilder builder = new StringBuilder(64);
            Optional<? extends UriEncoded> mUserInfo = mDelegate.userInfo();
            if (mUserInfo.isPresent())
            {
                builder.append(mUserInfo.value().normalized());
                builder.append('@');
            }
            builder.append(mDelegate.host().normalized());
            Optional<Integer> port = mDelegate.port();
            if (port.isPresent())
            {
                builder.append(':');
                builder.append((int) port.value());
            }
            mText = builder.toString();
        }
        return mText;
    }


    @Override
    public UriEncoded normalized()
    {
        return this;
    }


    @Override
    public CharSequence decoded(String charset) throws UnsupportedEncodingException
    {
        throw new UnsupportedOperationException("an Authority can't be decoded as a whole");
    }


    @Override
    public CharSequence decoded()
    {
        throw new UnsupportedOperationException("an Authority can't be decoded as a whole");
    }
}
