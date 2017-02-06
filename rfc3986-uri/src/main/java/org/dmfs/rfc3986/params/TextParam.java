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

package org.dmfs.rfc3986.params;

import org.dmfs.rfc3986.UriEncoded;
import org.dmfs.rfc3986.encoding.Encoded;
import org.dmfs.rfc3986.params.types.TextParamType;


/**
 * A mandatory text parameter. If the given {@link Parametrized} doesn't contain any parameter of this type all methods will throw.
 *
 * @author Marten Gajda
 */
public final class TextParam implements CharSequence
{
    private final OptionalParameter<CharSequence> mParameter;


    public TextParam(String name, Parametrized<UriEncoded, UriEncoded> parametrized)
    {
        this(new TextParamType(new Encoded(name)), parametrized);
    }


    public TextParam(UriEncoded name, Parametrized<UriEncoded, UriEncoded> parametrized)
    {
        this(new TextParamType(name), parametrized);
    }


    public TextParam(ParamType<CharSequence> type, Parametrized<UriEncoded, UriEncoded> parametrized)
    {
        mParameter = new OptionalParameter<>(type, parametrized);
    }


    @Override
    public int length()
    {
        return mParameter.value().length();
    }


    @Override
    public char charAt(int i)
    {
        return mParameter.value().charAt(i);
    }


    @Override
    public CharSequence subSequence(int i, int i1)
    {
        return mParameter.value().subSequence(i, i1);
    }


    @Override
    public String toString()
    {
        return mParameter.value().toString();
    }
}
