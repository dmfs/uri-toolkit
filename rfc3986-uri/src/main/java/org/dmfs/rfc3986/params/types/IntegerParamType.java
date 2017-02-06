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


/**
 * @author Marten Gajda
 */
public final class IntegerParamType implements ParamType<Integer>
{
    private final UriEncoded mName;


    public IntegerParamType(UriEncoded name)
    {
        mName = name;
    }


    @Override
    public UriEncoded name()
    {
        return mName.normalized();
    }


    @Override
    public Integer decodedValue(UriEncoded encoded)
    {
        return Integer.parseInt(encoded.decoded().toString());
    }
}
