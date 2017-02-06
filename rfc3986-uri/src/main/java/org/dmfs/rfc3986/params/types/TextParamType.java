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

package org.dmfs.rfc3986.params.types;

import org.dmfs.rfc3986.UriEncoded;
import org.dmfs.rfc3986.params.ParamType;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;


/**
 * @author Marten Gajda
 */
public final class TextParamType implements ParamType<CharSequence>
{
    private final UriEncoded mName;
    private final String mCharSet;


    public TextParamType(UriEncoded name)
    {
        this(name, "UTF-8");
    }


    public TextParamType(UriEncoded name, String charSet)
    {
        mName = name;
        mCharSet = charSet;
        // verify the existence of the Charset early
        Charset.forName(charSet);
    }


    @Override
    public UriEncoded name()
    {
        return mName.normalized();
    }


    @Override
    public CharSequence decodedValue(UriEncoded encoded)
    {
        try
        {
            return encoded.decoded(mCharSet);
        }
        catch (UnsupportedEncodingException e)
        {
            throw new RuntimeException("UnsupportedCharSetException despite check in constructor");
        }
    }
}
