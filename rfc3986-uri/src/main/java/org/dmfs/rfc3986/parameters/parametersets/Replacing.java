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

package org.dmfs.rfc3986.parameters.parametersets;

import org.dmfs.jems2.iterator.Concat;
import org.dmfs.jems2.iterator.Seq;
import org.dmfs.jems2.iterator.Sieved;
import org.dmfs.rfc3986.parameters.Parameter;
import org.dmfs.rfc3986.parameters.ParameterList;

import java.util.Iterator;


/**
 * {@link ParameterList} that replaces values of the decorated {@link ParameterList} with the given values.
 */
public final class Replacing implements ParameterList
{
    private final ParameterList mDelegate;
    private final Parameter[] mNewParameters;


    public Replacing(ParameterList delegate, Parameter... newParameters)
    {
        mDelegate = delegate;
        mNewParameters = newParameters;
    }


    @Override
    public Iterator<Parameter> iterator()
    {
        return new Concat<>(

            new Sieved<>(
                element -> {
                    // don't iterate keys that we have in mNewParameters
                    for (Parameter parameter : mNewParameters)
                    {
                        // TODO: get rid of the toString conversion and use something like an `Equalable`
                        // TODO: maybe move this check to ParameterType
                        if (parameter.name().toString().equals(element.name().toString()))
                        {
                            return false;
                        }
                    }
                    return true;
                },
                mDelegate.iterator()), new Seq<>(mNewParameters));
    }
}
