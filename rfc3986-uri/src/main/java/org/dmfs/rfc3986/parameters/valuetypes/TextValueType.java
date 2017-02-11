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

package org.dmfs.rfc3986.parameters.valuetypes;

import org.dmfs.rfc3986.parameters.ValueType;


/**
 * A {@link ValueType} for plain text parameters.
 *
 * @author Marten Gajda
 */
public final class TextValueType implements ValueType<CharSequence>
{
    public final static TextValueType INSTANCE = new TextValueType();


    @Override
    public CharSequence parsedValue(CharSequence valueText)
    {
        return valueText;
    }


    @Override
    public CharSequence serializedValue(CharSequence value)
    {
        return value;
    }
}
