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

package org.dmfs.rfc3986.encoding.utils;

import org.dmfs.rfc3986.validation.CharSet;

import java.io.IOException;
import java.io.OutputStream;


/**
 * An {@link OutputStream} that percent-encodes characters transparently. Calling {@link #toString()} returns the result.
 *
 * @author Marten Gajda
 */
public final class FormPercentEncodingOutputStream extends OutputStream
{
    private final static char[] HEXDIGITS = new char[] { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };

    private final StringBuilder mStringBuilder;
    private final CharSet mNoEncodeMap;


    public FormPercentEncodingOutputStream(int initialCapacity, CharSet noEncodeMap)
    {
        mStringBuilder = new StringBuilder(initialCapacity);
        mNoEncodeMap = noEncodeMap;
    }


    @Override
    public void write(int i) throws IOException
    {
        if (mNoEncodeMap.contains((char) i))
        {
            mStringBuilder.append((char) i);
        }
        else if (i == ' ')
        {
            mStringBuilder.append('+');
        }
        else if (i == 0x0a)
        {
            // normalize new line
            mStringBuilder.append("%0D%0A");
        }
        else if (i != 0x0d) // don't encode CR chars CRLF sequences will be encoded when the LF is written
        {
            mStringBuilder.append('%');
            mStringBuilder.append(HEXDIGITS[(i >>> 4) & 0x0f]);
            mStringBuilder.append(HEXDIGITS[i & 0x0f]);
        }
    }


    @Override
    public String toString()
    {
        return mStringBuilder.toString();
    }
}
