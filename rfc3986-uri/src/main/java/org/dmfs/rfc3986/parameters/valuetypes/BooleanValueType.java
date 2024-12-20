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
 * A {@link ValueType} for {@link Boolean} parameters.
 */
public final class BooleanValueType implements ValueType<Boolean>
{
    public final static BooleanValueType INSTANCE = new BooleanValueType();


    @Override
    public Boolean parsedValue(CharSequence valueText)
    {
        return Boolean.parseBoolean(valueText.toString()) || "1".equals(valueText.toString());
    }


    @Override
    public CharSequence serializedValue(Boolean value)
    {
        return value.toString();
    }
}
