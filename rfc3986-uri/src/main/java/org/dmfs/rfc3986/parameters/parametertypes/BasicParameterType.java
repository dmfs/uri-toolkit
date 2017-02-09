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

package org.dmfs.rfc3986.parameters.parametertypes;

import org.dmfs.rfc3986.parameters.Parameter;
import org.dmfs.rfc3986.parameters.ParameterType;
import org.dmfs.rfc3986.parameters.ValueType;


/**
 * A basic {@link ParameterType} implementation.
 *
 * @author Marten Gajda
 */
public final class BasicParameterType<T> implements ParameterType<T>
{
    private final CharSequence mName;
    private final ValueType<T> mValueType;


    /**
     * Creates a {@link ParameterType} with the given name and {@link ValueType}.
     *
     * @param name
     * @param valueType
     */
    public BasicParameterType(CharSequence name, ValueType<T> valueType)
    {
        mName = name;
        mValueType = valueType;
    }


    @Override
    public CharSequence name()
    {
        return mName;
    }


    @Override
    public T value(Parameter parameter)
    {
        if (!name().toString().equals(parameter.name().toString()))
        {
            throw new IllegalArgumentException(
                    String.format("Given parameter has wrong type \"%s\". Expected type \"%s\"", parameter.name().toString(), mName.toString()));
        }
        return mValueType.parsedValue(parameter.textValue());
    }


    @Override
    public Parameter parameter(final T value)
    {
        return new Parameter()
        {
            @Override
            public CharSequence name()
            {
                return mName;
            }


            @Override
            public CharSequence textValue()
            {
                return mValueType.serializedValue(value);
            }
        };
    }
}
