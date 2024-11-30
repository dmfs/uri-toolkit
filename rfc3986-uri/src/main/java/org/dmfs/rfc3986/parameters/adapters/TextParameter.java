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

package org.dmfs.rfc3986.parameters.adapters;

import org.dmfs.rfc3986.parameters.ParameterList;
import org.dmfs.rfc3986.parameters.ParameterType;

import java.util.NoSuchElementException;


/**
 * A mandatory text parameter. If the given {@link ParameterList} doesn't contain any parameter of this type all methods will throw {@link
 * NoSuchElementException}.
 */
public final class TextParameter implements CharSequence
{
    private final OptionalParameter<CharSequence> mParameter;


    public TextParameter(ParameterType<CharSequence> type, ParameterList parameterList)
    {
        mParameter = new OptionalParameter<>(type, parameterList);
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
